package chess.dao;

import chess.entity.PieceEntity;
import chess.entity.PiecePositionEntryEntity;
import chess.entity.PositionEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class PiecePositionDao extends DaoTemplate {

    private static final PiecePositionDao INSTANCE = new PiecePositionDao();

    private static final String QUERY_ADD_PIECE_POSITION = "INSERT INTO piece_position VALUES(0)";
    private static final String QUERY_ADD_PIECE_POSITION_ENTRY = "INSERT INTO piece_position_entry VALUES(?, ?, ?)";
    private static final String QUERY_FIND_BY_ID = "SELECT * FROM piece_position_entry WHERE piece_position_id = ?";
    private static final String QUERY_HAS_DATA_BY_ID = "SELECT EXISTS (SELECT 1 FROM piece_position_entry WHERE position_id = ?)";
    private static final String QUERY_UPDATE_STATUS = "UPDATE piece_position_entry SET piece_id = ? WHERE piece_position_id = ? AND position_id = ?";
    private static final String QUERY_DELETE_BY_PIECE = "DELETE FROM piece_position_entry WHERE piece_position_id = ? AND piece_id = ?";
    private static final String QUERY_TRUNCATE_TABLE_PIECE_POSITION = "TRUNCATE TABLE piece_position_entry";
    private static final String QUERY_TRUNCATE_TABLE_PIECE_POSITION_ENTRY = "TRUNCATE TABLE piece_position_entry";
    private static final String QUERY_DELETE_FOREIGN_KEY_CONSTRAINT = "ALTER TABLE piece_position_entry DROP FOREIGN KEY piece_position_entry_ibfk_1";
    private static final String QUERY_ADD_FOREIGN_KEY_CONSTRAINT = "ALTER TABLE piece_position_entry\n"
            + "ADD CONSTRAINT piece_position_entry_ibfk_1 FOREIGN KEY (position_id) REFERENCES `position`(position_id)";

    private final PositionDao positionDao = PositionDao.getInstance();
    private final PieceDao pieceDao = PieceDao.getInstance();

    private PiecePositionDao() {
    }

    public static PiecePositionDao getInstance() {
        return INSTANCE;
    }

    public int add() {
        return add(QUERY_ADD_PIECE_POSITION);
    }

    public void addEntry(PiecePositionEntryEntity entity) {
        add(QUERY_ADD_PIECE_POSITION_ENTRY, preparedStatement -> {
            try {
                preparedStatement.setInt(1, entity.pieceId());
                preparedStatement.setInt(2, entity.piecePositionId());
                preparedStatement.setInt(3, entity.positionId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Map<PositionEntity, PieceEntity> findEntryByPiecePositionId(int piecePositionId) {
        try (ResultSet resultSet = findById(QUERY_FIND_BY_ID, piecePositionId)) {
            validateResultSetExist(resultSet);
            return createEntry(resultSet, piecePositionId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasEntryDataByPositionId(int positionId) {
        return hasDataById(QUERY_HAS_DATA_BY_ID, positionId);
    }

    public void updateEntry(PiecePositionEntryEntity entity) {
        executeUpdate(QUERY_UPDATE_STATUS, preparedStatement -> {
            try {
                preparedStatement.setInt(1, entity.pieceId());
                preparedStatement.setInt(2, entity.piecePositionId());
                preparedStatement.setInt(3, entity.positionId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteEntryByPiece(int piecePositionId, int pieceId) {
        executeUpdate(QUERY_DELETE_BY_PIECE, preparedStatement -> {
            try {
                preparedStatement.setInt(1, piecePositionId);
                preparedStatement.setInt(2, pieceId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void truncateTable() {
        executeStatement(QUERY_TRUNCATE_TABLE_PIECE_POSITION);
    }

    public void truncateTableEntry() {
        executeStatement(QUERY_TRUNCATE_TABLE_PIECE_POSITION_ENTRY);
    }

    public void deleteForeignKeyConstraint() {
        executeStatement(QUERY_DELETE_FOREIGN_KEY_CONSTRAINT);
    }

    public void addForeignKeyConstraint() {
        executeStatement(QUERY_ADD_FOREIGN_KEY_CONSTRAINT);
    }

    private Map<PositionEntity, PieceEntity> createEntry(ResultSet resultSet, int piecePositionId) throws SQLException {
        Map<PositionEntity, PieceEntity> piecePosition = new HashMap<>();
        while (resultSet.next()) {
            int positionId = resultSet.getInt("position_id");
            int pieceId = resultSet.getInt("piece_id");
            PositionEntity positionEntity = positionDao.findById(positionId);
            PieceEntity pieceEntity = pieceDao.findById(pieceId);
            piecePosition.put(positionEntity, pieceEntity);
        }
        validatePiecePositionIdExist(piecePosition, piecePositionId);
        return piecePosition;
    }

    private void validatePiecePositionIdExist(Map<PositionEntity, PieceEntity> piecePosition, int piecePositionId) {
        if (piecePosition.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] piecePositionId로 저장된 정보가 없습니다. : " + piecePositionId);
        }
    }
}
