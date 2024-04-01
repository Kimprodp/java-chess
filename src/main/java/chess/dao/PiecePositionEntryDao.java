package chess.dao;

import chess.domain.position.Position;
import chess.dto.PieceDto;
import chess.dto.PiecePositionDto;
import chess.entity.PieceEntity;
import chess.entity.PiecePositionEntryEntity;
import chess.entity.PiecePositionEntryEntityToDelete;
import chess.entity.PositionEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class PiecePositionEntryDao {

    private static final PiecePositionEntryDao INSTANCE = new PiecePositionEntryDao();

    private final PositionDao positionDao = PositionDao.getInstance();
    private final PieceDao pieceDao = PieceDao.getInstance();

    private PiecePositionEntryDao() {
    }

    public static PiecePositionEntryDao getINSTANCE() {
        return INSTANCE;
    }

    public int add(int piecePositionId, PiecePositionDto piecePositionDto) {
        Map<Position, PieceDto> positionPiece = piecePositionDto.piecePosition();
        for (Position position : positionPiece.keySet()) {
            PieceDto pieceDto = positionPiece.get(position);

            int positionId = positionDao.findId(new PositionEntity(position.getLettering(), position.getNumbering()));
            int pieceId = pieceDao.findId(new PieceEntity(pieceDto.pieceType(), pieceDto.camp()));
            addRow(new PiecePositionEntryEntity(piecePositionId, positionId, pieceId));
        }

        return piecePositionId;
    }

    public void addRow(PiecePositionEntryEntity piecePositionEntryEntity) {
        String query = "INSERT INTO piece_position_entry VALUES(?, ?, ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, piecePositionEntryEntity.piecePositionId());
            preparedStatement.setInt(2, piecePositionEntryEntity.positionId());
            preparedStatement.setInt(3, piecePositionEntryEntity.pieceId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<PositionEntity, PieceEntity> findByPiecePositionId(int piecePositionId) {
        String query = "SELECT * FROM piece_position_entry WHERE piece_position_id = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, piecePositionId);

            ResultSet resultSet = preparedStatement.executeQuery();
            validatePiecePositionIdExist(piecePositionId, resultSet);
            return createEntry(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasDataByPositionId(int positionId) {
        String query = "SELECT EXISTS (SELECT 1 FROM piece_position_entry WHERE position_id = ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setInt(1, positionId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateRow(PiecePositionEntryEntity piecePositionEntryEntity) {
        String query = "UPDATE piece_position_entry SET piece_id = ? WHERE piece_position_id = ? AND position_id = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, piecePositionEntryEntity.pieceId());
            preparedStatement.setInt(2, piecePositionEntryEntity.piecePositionId());
            preparedStatement.setInt(3, piecePositionEntryEntity.positionId());
            int numberOfUpdateRows = preparedStatement.executeUpdate();
            validateUpdateSuccess(numberOfUpdateRows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteRow(PiecePositionEntryEntityToDelete piecePositionEntryEntityToDelete) {
        String query = "DELETE FROM piece_position_entry WHERE piece_position_id = ? AND position_id = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, piecePositionEntryEntityToDelete.piecePositionId());
            preparedStatement.setInt(2, piecePositionEntryEntityToDelete.positionId());
            int numberOfDeletedRows = preparedStatement.executeUpdate();
            validateDeleteSuccess(numberOfDeletedRows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void truncateTable() {
        String query = "TRUNCATE TABLE piece_position_entry";
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteForeignKeyConstraint() {
        String query = "ALTER TABLE piece_position_entry DROP FOREIGN KEY piece_position_entry_ibfk_1";
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addForeignKeyConstraint() {
        String query = "ALTER TABLE piece_position_entry\n"
                + "ADD CONSTRAINT piece_position_entry_ibfk_1 FOREIGN KEY (position_id) REFERENCES `position`(position_id)";
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<PositionEntity, PieceEntity> createEntry(ResultSet resultSet) throws SQLException {
        Map<PositionEntity, PieceEntity> piecePosition = new HashMap<>();
        while (resultSet.next()) {
            int positionId = resultSet.getInt("position_id");
            int pieceId = resultSet.getInt("piece_id");
            PositionEntity positionEntity = positionDao.findById(positionId);
            PieceEntity pieceEntity = pieceDao.findById(pieceId);
            piecePosition.put(positionEntity, pieceEntity);
        }
        return piecePosition;
    }

    private void validatePiecePositionIdExist(int findPiecePositionId, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new IllegalArgumentException("[ERROR] piecePositionId로 저장된 정보가 없습니다. : " + findPiecePositionId);
        }
    }

    private void validateDeleteSuccess(int numberOfDeletedRows) {
        if (numberOfDeletedRows != 1) {
            throw new IllegalArgumentException("[ERROR] 삭제가 정상적으로 처리되지 않았습니다.");
        }
    }

    private void validateUpdateSuccess(int numberOfUpdateRows) {
        if (numberOfUpdateRows != 1) {
            throw new IllegalArgumentException("[ERROR] 업데이트가 정상적으로 처리되지 않았습니다.");
        }
    }
}
