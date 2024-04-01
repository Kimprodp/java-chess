package chess.dao;

import chess.entity.PieceEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PieceDao {

    private static final PieceDao INSTANCE = new PieceDao();

    private PieceDao() {
    }

    public static PieceDao getInstance() {
        return INSTANCE;
    }

    public void add(PieceEntity pieceEntity) {
        String query = "INSERT INTO piece VALUES(0, ?, ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, pieceEntity.getType());
            preparedStatement.setString(2, pieceEntity.getCamp());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasData() {
        String query = "SELECT EXISTS (SELECT 1 FROM piece)";
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getBoolean(1);
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PieceEntity find(PieceEntity pieceEntity) {
        String query = "SELECT * FROM piece WHERE type = ? AND camp = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, pieceEntity.getType());
            preparedStatement.setString(2, pieceEntity.getCamp());

            ResultSet resultSet = preparedStatement.executeQuery();
            validatePieceExist(pieceEntity, resultSet);
            return new PieceEntity(
                    resultSet.getString("type"),
                    resultSet.getString("camp")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PieceEntity findById(int pieceId) {
        String query = "SELECT * FROM piece WHERE piece_id = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pieceId);

            ResultSet resultSet = preparedStatement.executeQuery();
            validatePieceExist(pieceId, resultSet);
            return new PieceEntity(
                    resultSet.getString("type"),
                    resultSet.getString("camp")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findId(PieceEntity pieceEntity) {
        String query = "SELECT * FROM piece WHERE type = ? AND camp = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, pieceEntity.getType());
            preparedStatement.setString(2, pieceEntity.getCamp());

            ResultSet resultSet = preparedStatement.executeQuery();
            validatePieceExist(pieceEntity, resultSet);
            return resultSet.getInt("piece_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void truncateTable() {
        String query = "TRUNCATE TABLE piece";
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void validatePieceExist(PieceEntity pieceEntity, ResultSet resultSet)
            throws SQLException {
        if (!resultSet.next()) {
            throw new IllegalArgumentException("[ERROR] 저장된 piece 정보가 없습니다. : " + pieceEntity);
        }
    }

    private static void validatePieceExist(int pieceId, ResultSet resultSet)
            throws SQLException {
        if (!resultSet.next()) {
            throw new IllegalArgumentException("[ERROR] 저장된 piece 정보가 없습니다. : " + pieceId);
        }
    }
}
