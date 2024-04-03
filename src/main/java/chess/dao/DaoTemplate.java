package chess.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;

public abstract sealed class DaoTemplate
        permits ChessGameDao, PiecePositionDao, PieceDao, PositionDao {

    private final DBConnector dbConnector = DBConnector.getInstance();

    protected DaoTemplate() {
    }

    protected int add(String query, Consumer<PreparedStatement> statementSetter) {
        try (PreparedStatement preparedStatement = getPreparedStatementContainGeneratedKey(query)) {
            statementSetter.accept(preparedStatement);
            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                validateResultSetExist(resultSet);
                return resultSet.getInt(1);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected int add(String query) {
        try (PreparedStatement preparedStatement = getPreparedStatementContainGeneratedKey(query);
             ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
            validateResultSetExist(resultSet);
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected ResultSet findById(String query, int id) {
        try (PreparedStatement preparedStatement = getPreparedStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                validateResultSetExist(resultSet);
                return resultSet;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected int findId(String query, String columnName, Consumer<PreparedStatement> statementSetter) {
        try (PreparedStatement preparedStatement = getPreparedStatement(query)) {
            statementSetter.accept(preparedStatement);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                validateResultSetExist(resultSet);
                return resultSet.getInt(columnName);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean hasData(String query) {
        try (ResultSet resultSet = createResultSetByStatement(query)) {
            return hasResultExist(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected boolean hasDataById(String query, int id) {
        try (PreparedStatement preparedStatement = getPreparedStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return hasResultExist(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void executeUpdate(String query, Consumer<PreparedStatement> statementSetter) {
        try (PreparedStatement preparedStatement = getPreparedStatement(query)) {
            statementSetter.accept(preparedStatement);
            int numberOfProcessedRows = preparedStatement.executeUpdate();
            validateProcessedRows(numberOfProcessedRows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected void executeStatement(String query) {
        try (Statement statement = getStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected Connection getConnection() {
        return dbConnector.getConnection();
    }

    protected PreparedStatement getPreparedStatement(String query) throws SQLException {
        try (Connection connection = getConnection()) {
            return connection.prepareStatement(query);
        }
    }

    protected PreparedStatement getPreparedStatementContainGeneratedKey(String query) throws SQLException {
        try (Connection connection = getConnection()) {
            return connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        }
    }

    protected Statement getStatement() throws SQLException {
        try (Connection connection = getConnection()) {
            return connection.createStatement();
        }
    }

    protected ResultSet createResultSetByStatement(String query) throws SQLException {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            return statement.executeQuery(query);
        }
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
