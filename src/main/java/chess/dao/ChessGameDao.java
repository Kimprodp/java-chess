package chess.dao;

import chess.entity.ChessGameEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ChessGameDao extends DaoTemplate {

    private static final ChessGameDao INSTANCE = new ChessGameDao();

    private ChessGameDao() {
    }

    public static ChessGameDao getInstance() {
        return INSTANCE;
    }

    public int add(ChessGameEntity entity) {
        String query = "INSERT INTO chess_game VALUES(0, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = preparedStatement(connection, query, entity.getPiecePositionId(),
                     entity.getStatusValue())) {
            preparedStatement.executeUpdate();
            return getGeneratedKeys(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ChessGameEntity findById(int chessGameId) {
        String query = "SELECT * FROM chess_game WHERE chess_game_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = preparedStatement(connection, query, chessGameId);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return mappingResult(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findLastID() {
        String query = "SELECT COALESCE(MAX(chess_game_id), 1) AS max_id FROM chess_game";
        try (Connection connection = getConnection();
             ResultSet resultSet = executeQueryByStatement(connection, query)) {
            validateResultSetExist(resultSet);
            return resultSet.getInt("max_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasData() {
        String query = "SELECT EXISTS (SELECT 1 FROM chess_game)";
        return executeQuery(query);
    }

    public void updateStatus(ChessGameEntity entity) {
        String query = "UPDATE chess_game SET status_value = ? WHERE chess_game_id = ? AND piece_position_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = preparedStatement(connection, query, entity.getStatusValue(),
                     entity.getChessGameId(), entity.getPiecePositionId())) {
            int numberOfProcessedRows = preparedStatement.executeUpdate();
            validateProcessedRows(numberOfProcessedRows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void truncateTable() {
        String query = "TRUNCATE TABLE chess_game";
        executeUpdate(query);
    }

    public void deleteForeignKeyConstraint() {
        String query = "ALTER TABLE chess_game DROP FOREIGN KEY chess_game_ibfk_1";
        executeUpdate(query);
    }

    public void addForeignKeyConstraint() {
        String query = "ALTER TABLE chess_game\n"
                + "ADD CONSTRAINT chess_game_ibfk_1 FOREIGN KEY (piece_position_id) REFERENCES piece_position(piece_position_id)";
        executeUpdate(query);
    }

    private ChessGameEntity mappingResult(ResultSet resultSet) throws SQLException {
        validateResultSetExist(resultSet);
        return new ChessGameEntity(
                resultSet.getInt("chess_game_id"),
                resultSet.getInt("piece_position_id"),
                resultSet.getInt("status_value")
        );
    }
}
