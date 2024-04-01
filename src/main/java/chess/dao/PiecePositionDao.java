package chess.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PiecePositionDao {

    private static final PiecePositionDao INSTANCE = new PiecePositionDao();

    private final ChessGameDao chessGameDao = ChessGameDao.getInstance();
    private final PiecePositionEntryDao piecePositionEntryDao = PiecePositionEntryDao.getINSTANCE();

    private PiecePositionDao() {
    }

    public static PiecePositionDao getInstance() {
        return INSTANCE;
    }

    public int add() {
        String query = "INSERT INTO piece_position VALUES(0)";
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.getGeneratedKeys();
            validatePiecePositionExist(generatedKeys);
            return generatedKeys.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void truncateTable() {
        chessGameDao.deleteForeignKeyConstraint();
        piecePositionEntryDao.deleteForeignKeyConstraint();

        String query = "TRUNCATE TABLE piece_position";
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        chessGameDao.addForeignKeyConstraint();
        piecePositionEntryDao.addForeignKeyConstraint();
    }

    private void validatePiecePositionExist(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new IllegalArgumentException("[ERROR] 저장된 piecePosition 정보가 없습니다.");
        }
    }
}
