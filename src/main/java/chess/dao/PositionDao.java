package chess.dao;

import chess.entity.PositionEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class PositionDao extends DaoTemplate {

    private static final PositionDao INSTANCE = new PositionDao();

    private PositionDao() {
    }

    public static PositionDao getInstance() {
        return INSTANCE;
    }

    public void add(PositionEntity entity) {
        String query = "INSERT INTO position VALUES(0, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = preparedStatement(connection, query, entity.getLettering(),
                     entity.getNumbering())) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PositionEntity findById(int positionId) {
        String query = "SELECT * FROM position WHERE position_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = preparedStatement(connection, query, positionId);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return mappingResult(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findId(PositionEntity entity) {
        String query = "SELECT * FROM position WHERE lettering = ? AND numbering = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = preparedStatement(
                     connection, query, entity.getLettering(), entity.getNumbering());
             ResultSet resultSet = preparedStatement.executeQuery()) {
            validateResultSetExist(resultSet);
            return resultSet.getInt("position_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasData() {
        String query = "SELECT EXISTS (SELECT 1 FROM position)";
        return hasData(query);
    }

    private PositionEntity mappingResult(ResultSet resultSet) throws SQLException {
        validateResultSetExist(resultSet);
        return new PositionEntity(
                resultSet.getString("lettering"),
                resultSet.getString("numbering")
        );
    }
}
