package chess.dao;

import chess.entity.ChessGameEntity;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class ChessGameDao extends DaoTemplate {

    private static final ChessGameDao INSTANCE = new ChessGameDao();

    private static final String QUERY_ADD = "INSERT INTO chess_game VALUES(0, ?, ?)";
    private static final String QUERY_FIND_BY_ID = "SELECT * FROM chess_game WHERE chess_game_id = ?";
    private static final String QUERY_FIND_LAST_ID = "SELECT COALESCE(MAX(chess_game_id), 1) AS max_id FROM chess_game";
    private static final String QUERY_HAS_DATA = "SELECT EXISTS (SELECT 1 FROM chess_game)";
    private static final String QUERY_UPDATE_STATUS = "UPDATE chess_game SET status_value = ? WHERE chess_game_id = ? AND piece_position_id = ?";
    private static final String QUERY_TRUNCATE_TABLE = "TRUNCATE TABLE chess_game";
    private static final String QUERY_DELETE_FOREIGN_KEY_CONSTRAINT = "ALTER TABLE chess_game DROP FOREIGN KEY chess_game_ibfk_1";
    private static final String QUERY_ADD_FOREIGN_KEY_CONSTRAINT = "ALTER TABLE chess_game\n"
            + "ADD CONSTRAINT chess_game_ibfk_1 FOREIGN KEY (piece_position_id) REFERENCES piece_position(piece_position_id)";

    private ChessGameDao() {
    }

    public static ChessGameDao getInstance() {
        return INSTANCE;
    }

    public int add(ChessGameEntity entity) {
        return add(QUERY_ADD, preparedStatement -> {
            try {
                preparedStatement.setInt(1, entity.getPiecePositionId());
                preparedStatement.setInt(2, entity.getStatusValue());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public ChessGameEntity findById(int chessGameId) {
        try (ResultSet resultSet = findById(QUERY_FIND_BY_ID, chessGameId)) {
            validateResultSetExist(resultSet);
            return new ChessGameEntity(
                    resultSet.getInt("chess_game_id"),
                    resultSet.getInt("piece_position_id"),
                    resultSet.getInt("status_value")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findLastID() {
        try (ResultSet resultSet = createResultSetByStatement(QUERY_FIND_LAST_ID)) {
            validateResultSetExist(resultSet);
            return resultSet.getInt("max_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasData() {
        return hasData(QUERY_HAS_DATA);
    }

    public void updateStatus(ChessGameEntity entity) {
        executeUpdate(QUERY_UPDATE_STATUS, preparedStatement -> {
            try {
                preparedStatement.setInt(1, entity.getStatusValue());
                preparedStatement.setInt(2, entity.getChessGameId());
                preparedStatement.setInt(3, entity.getPiecePositionId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void truncateTable() {
        executeStatement(QUERY_TRUNCATE_TABLE);
    }

    public void deleteForeignKeyConstraint() {
        executeStatement(QUERY_DELETE_FOREIGN_KEY_CONSTRAINT);
    }

    public void addForeignKeyConstraint() {
        executeStatement(QUERY_ADD_FOREIGN_KEY_CONSTRAINT);
    }
}
