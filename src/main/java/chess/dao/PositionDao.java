package chess.dao;

import chess.entity.PositionEntity;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class PositionDao extends DaoTemplate {

    private static final PositionDao INSTANCE = new PositionDao();

    private static final String COLUMN_KEY = "position_id";
    private static final String QUERY_ADD = "INSERT INTO position VALUES(0, ?, ?)";
    private static final String QUERY_FIND = "SELECT * FROM position WHERE lettering = ? AND numbering = ?";
    private static final String QUERY_FIND_BY_ID = "SELECT * FROM position WHERE position_id = ?";
    private static final String QUERY_HAS_DATA = "SELECT EXISTS (SELECT 1 FROM position)";

    private PositionDao() {
    }

    public static PositionDao getInstance() {
        return INSTANCE;
    }

    public void add(PositionEntity entity) {
        add(QUERY_ADD, preparedStatement -> {
            try {
                preparedStatement.setString(1, entity.getLettering());
                preparedStatement.setString(2, entity.getNumbering());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public PositionEntity findById(int positionId) {
        try (ResultSet resultSet = findById(QUERY_FIND_BY_ID, positionId)) {
            validateResultSetExist(resultSet);
            return new PositionEntity(
                    resultSet.getString("lettering"),
                    resultSet.getString("numbering")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int findId(PositionEntity entity) {
        return findId(QUERY_FIND, COLUMN_KEY, preparedStatement -> {
            try {
                preparedStatement.setString(1, entity.getLettering());
                preparedStatement.setString(2, entity.getNumbering());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public boolean hasData() {
        return hasData(QUERY_HAS_DATA);
    }
}
