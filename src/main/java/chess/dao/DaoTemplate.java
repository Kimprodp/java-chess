package chess.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract sealed class DaoTemplate
        permits ChessGameDao, PiecePositionDao, PieceDao, PositionDao {

    private final DBConnector dbConnector = DBConnector.getInstance();

    protected DaoTemplate() {
    }

    protected Connection getConnection() {
        return dbConnector.getConnection();
    }

    protected PreparedStatement preparedStatement(Connection connection, String query, Object... params)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        setParameters(preparedStatement, params);
        return preparedStatement;
    }

    protected PreparedStatement preparedStatement(Connection connection, String query) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        return preparedStatement;
    }

    protected void setParameters(PreparedStatement preparedStatement, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
    }

    protected int getGeneratedKeys(PreparedStatement preparedStatement) throws SQLException {
        ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        validateResultSetExist(generatedKeys);
        return generatedKeys.getInt(1);
    }

    protected boolean hasData(String query) {
        try (Connection connection = getConnection();
             ResultSet resultSet = executeQueryByStatement(connection, query)) {
            return hasResultExist(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void executeUpdate(String query) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected ResultSet executeQueryByStatement(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    protected boolean hasResultExist(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return resultSet.getBoolean(1);
        }
        return false;
    }

    protected void validateResultSetExist(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new IllegalArgumentException("[ERROR] 불러올 데이터가 없습니다.");
        }
    }

    protected void validateProcessedRows(int numberOfProcessedRows) {
        if (numberOfProcessedRows != 1) {
            throw new IllegalArgumentException("[ERROR] 데이터 변경이 정상적으로 처리되지 않았습니다.");
        }
    }
}
