package chess.dao;

import chess.entity.ChessGameEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChessGameDao {

    private static final ChessGameDao INSTANCE = new ChessGameDao();

    private ChessGameDao() {
    }

    public static ChessGameDao getInstance() {
        return INSTANCE;
    }

    public int add(ChessGameEntity chessGameEntity) {
        String query = "INSERT INTO chess_game VALUES(0, ?, ?)";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, chessGameEntity.getPiecePositionId());
            preparedStatement.setInt(2, chessGameEntity.getStatus_value());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            validateChessGameExist(generatedKeys);
            return generatedKeys.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasData() {
        String query = "SELECT EXISTS (SELECT 1 FROM chess_game)";
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

    public ChessGameEntity findById(int chessGameId) {
        String query = "SELECT * FROM chess_game WHERE chess_game_id = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, chessGameId);

            ResultSet resultSet = preparedStatement.executeQuery();
            validateChessGameExist(resultSet);
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
        String query = "SELECT COALESCE(MAX(chess_game_id), 0) AS max_id FROM chess_game";
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            validateChessGameExist(resultSet);
            return resultSet.getInt("max_id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStatus(ChessGameEntity chessGameEntity) {
        String query = "UPDATE chess_game SET status_value = ? WHERE chess_game_id = ? AND piece_position_id = ?";
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, chessGameEntity.getStatus_value());
            preparedStatement.setInt(2, chessGameEntity.getChessGameId());
            preparedStatement.setInt(3, chessGameEntity.getPiecePositionId());
            int numberOfUpdateRows = preparedStatement.executeUpdate();
            validateUpdateSuccess(numberOfUpdateRows);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void truncateTable() {
        String query = "TRUNCATE TABLE chess_game";
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteForeignKeyConstraint() {
        String query = "ALTER TABLE chess_game DROP FOREIGN KEY chess_game_ibfk_1";
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addForeignKeyConstraint() {
        String query = "ALTER TABLE chess_game\n"
                + "ADD CONSTRAINT chess_game_ibfk_1 FOREIGN KEY (piece_position_id) REFERENCES piece_position(piece_position_id)";
        try (Connection connection = DBConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateChessGameExist(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new IllegalArgumentException("[ERROR] 불러올 게임이 없습니다.");
        }
    }

    private void validateUpdateSuccess(int numberOfUpdateRows) {
        if (numberOfUpdateRows != 1) {
            throw new IllegalArgumentException("[ERROR] 업데이트가 정상적으로 처리되지 않았습니다.");
        }
    }
}
