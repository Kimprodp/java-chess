package chess.dao;

import chess.entity.PieceEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class PieceDao extends DaoTemplate {

    private static final PieceDao INSTANCE = new PieceDao();

    private PieceDao() {
    }

    public static PieceDao getInstance() {
        return INSTANCE;
    }

    public void add(PieceEntity entity) {
        String query = "INSERT INTO piece VALUES(0, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = preparedStatement(connection, query, entity.getType(),
                     entity.getCamp())) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PieceEntity findById(int pieceId) {
        String query = "SELECT * FROM piece WHERE piece_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = preparedStatement(connection, query, pieceId);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return mappingResult(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findId(PieceEntity entity) {
        String query = "SELECT * FROM piece WHERE type = ? AND camp = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = preparedStatement(
                     connection, query, entity.getType(), entity.getCamp());
             ResultSet resultSet = preparedStatement.executeQuery()) {
            validateResultSetExist(resultSet);
            return resultSet.getInt("piece_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasData() {
        String query = "SELECT EXISTS (SELECT 1 FROM piece)";
        return hasData(query);
    }

    private PieceEntity mappingResult(ResultSet resultSet) throws SQLException {
        validateResultSetExist(resultSet);
        return new PieceEntity(
                resultSet.getString("type"),
                resultSet.getString("camp")
        );
    }
}
