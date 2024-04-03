package chess.dao;

import chess.entity.PieceEntity;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class PieceDao extends DaoTemplate {

    private static final PieceDao INSTANCE = new PieceDao();

    private static final String COLUMN_KEY = "piece_id";
    private static final String QUERY_ADD = "INSERT INTO piece VALUES(0, ?, ?)";
    private static final String QUERY_FIND = "SELECT * FROM piece WHERE type = ? AND camp = ?";
    private static final String QUERY_FIND_BY_ID = "SELECT * FROM piece WHERE piece_id = ?";
    private static final String QUERY_HAS_DATA = "SELECT EXISTS (SELECT 1 FROM piece)";

    private PieceDao() {
    }

    public static PieceDao getInstance() {
        return INSTANCE;
    }

    public void add(PieceEntity entity) {
        add(QUERY_ADD, preparedStatement -> {
            try {
                preparedStatement.setString(1, entity.getType());
                preparedStatement.setString(2, entity.getCamp());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public PieceEntity findById(int pieceId) {
        try (ResultSet resultSet = findById(QUERY_FIND_BY_ID, pieceId)) {
            validateResultSetExist(resultSet);
            return new PieceEntity(
                    resultSet.getString("type"),
                    resultSet.getString("camp")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findId(PieceEntity entity) {
        return findId(QUERY_FIND, COLUMN_KEY, preparedStatement -> {
            try {
                preparedStatement.setString(1, entity.getType());
                preparedStatement.setString(2, entity.getCamp());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public boolean hasData() {
        return hasData(QUERY_HAS_DATA);
    }
}
