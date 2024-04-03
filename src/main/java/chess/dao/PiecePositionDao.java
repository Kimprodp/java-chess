package chess.dao;

import chess.entity.PieceEntity;
import chess.entity.PiecePositionEntryEntity;
import chess.entity.PositionEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class PiecePositionDao extends DaoTemplate<Map<PositionEntity, PieceEntity>> {

    private static final PiecePositionDao INSTANCE = new PiecePositionDao();

    private final ChessGameDao chessGameDao = ChessGameDao.getInstance();
    private final PositionDao positionDao = PositionDao.getInstance();
    private final PieceDao pieceDao = PieceDao.getInstance();

    private PiecePositionDao() {
    }

    public static PiecePositionDao getInstance() {
        return INSTANCE;
    }


    public int add() {
        String query = "INSERT INTO piece_position VALUES(0)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = preparedStatement(connection, query)) {
            preparedStatement.executeUpdate();
            return getGeneratedKeys(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addEntry(PiecePositionEntryEntity entity) {
        String query = "INSERT INTO piece_position_entry VALUES(?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = preparedStatement(connection, query, entity.piecePositionId(),
                     entity.positionId(), entity.pieceId())) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<PositionEntity, PieceEntity> findEntryByPiecePositionId(int piecePositionId) {
        String query = "SELECT * FROM piece_position_entry WHERE piece_position_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = preparedStatement(connection, query, piecePositionId);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return createEntry(resultSet, piecePositionId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasEntryDataByPositionId(int positionId) {
        String query = "SELECT EXISTS (SELECT 1 FROM piece_position_entry WHERE position_id = ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = preparedStatement(connection, query, positionId);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return hasResultExist(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateEntry(PiecePositionEntryEntity entity) {
        String query = "UPDATE piece_position_entry SET piece_id = ? WHERE piece_position_id = ? AND position_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = preparedStatement(connection, query, entity.pieceId(),
                     entity.piecePositionId(), entity.positionId())) {
            int numberOfProcessedRows = preparedStatement.executeUpdate();
            validateProcessedRows(numberOfProcessedRows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEntryByPiece(PiecePositionEntryEntity entity) {
        String query = "DELETE FROM piece_position_entry WHERE piece_position_id = ? AND position_id = ? AND piece_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = preparedStatement(connection, query, entity.piecePositionId(), entity.positionId(), entity.pieceId())) {
            int numberOfProcessedRows = preparedStatement.executeUpdate();
            validateProcessedRows(numberOfProcessedRows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void truncateTable() {
        String query = "TRUNCATE TABLE piece_position";
        executeUpdate(query);
    }

    public void truncateTableEntry() {
        String query = "TRUNCATE TABLE piece_position_entry";
        executeUpdate(query);
    }

    public void deleteForeignKeyConstraint() {
        String query = "ALTER TABLE piece_position_entry DROP FOREIGN KEY piece_position_entry_ibfk_1";
        executeUpdate(query);
    }

    public void addForeignKeyConstraint() {
        String query = "ALTER TABLE piece_position_entry\n"
                + "ADD CONSTRAINT piece_position_entry_ibfk_1 FOREIGN KEY (position_id) REFERENCES `position`(position_id)";
        executeUpdate(query);
    }

    private Map<PositionEntity, PieceEntity> createEntry(ResultSet resultSet, int piecePositionId) throws SQLException {
        Map<PositionEntity, PieceEntity> piecePosition = new HashMap<>();
        while (resultSet.next()) {
            int positionId = resultSet.getInt("position_id");
            int pieceId = resultSet.getInt("piece_id");
            PositionEntity positionEntity = positionDao.findById(positionId);
            PieceEntity pieceEntity = pieceDao.findById(pieceId);
            piecePosition.put(positionEntity, pieceEntity);
        }
        validatePiecePositionIdExist(piecePosition, piecePositionId);
        return piecePosition;
    }

    private void validatePiecePositionIdExist(Map<PositionEntity, PieceEntity> piecePosition, int piecePositionId) {
        if (piecePosition.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] piecePositionId로 저장된 정보가 없습니다. : " + piecePositionId);
        }
    }
}
