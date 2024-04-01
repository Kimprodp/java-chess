package chess.dao;

import chess.entity.PositionEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PositionDao {

    private static final PositionDao INSTANCE = new PositionDao();

    private PositionDao() {
    }

    public static PositionDao getInstance() {
        return INSTANCE;
    }

    public void add(PositionEntity positionEntity) {
        String query = "INSERT INTO position VALUES(0, ?, ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, positionEntity.getLettering());
            preparedStatement.setString(2, positionEntity.getNumbering());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasData() {
        String query = "SELECT EXISTS (SELECT 1 FROM position)";
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

    public PositionEntity find(PositionEntity positionEntity) {
        String query = "SELECT * FROM position WHERE lettering = ? AND numbering = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, positionEntity.getLettering());
            preparedStatement.setString(2, positionEntity.getNumbering());

            ResultSet resultSet = preparedStatement.executeQuery();
            validatePositionExist(positionEntity, resultSet);
            return new PositionEntity(
                    resultSet.getString("lettering"),
                    resultSet.getString("numbering")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PositionEntity findById(int positionId) {
        String query = "SELECT * FROM position WHERE position_id = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, positionId);

            ResultSet resultSet = preparedStatement.executeQuery();
            validatePositionExist(positionId, resultSet);
            return new PositionEntity(
                    resultSet.getString("lettering"),
                    resultSet.getString("numbering")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findId(PositionEntity positionEntity) {
        String query = "SELECT * FROM position WHERE lettering = ? AND numbering = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, positionEntity.getLettering());
            preparedStatement.setString(2, positionEntity.getNumbering());

            ResultSet resultSet = preparedStatement.executeQuery();
            validatePositionExist(positionEntity, resultSet);
            return resultSet.getInt("position_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void truncateTable() {
        String query = "TRUNCATE TABLE position";
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void validatePositionExist(PositionEntity positionEntity, ResultSet resultSet)
            throws SQLException {
        if (!resultSet.next()) {
            throw new IllegalArgumentException("[ERROR] 저장된 position 정보가 없습니다. : " + positionEntity);
        }
    }

    private static void validatePositionExist(int positionId, ResultSet resultSet)
            throws SQLException {
        if (!resultSet.next()) {
            throw new IllegalArgumentException("[ERROR] 저장된 position 정보가 없습니다. : " + positionId);
        }
    }
}
