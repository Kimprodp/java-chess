package chess.service;

import chess.dao.ChessGameDao;
import chess.dao.PieceDao;
import chess.dao.PiecePositionDao;
import chess.dao.PositionDao;
import chess.domain.game.ChessGame;
import chess.domain.game.InProgressGameInfo;
import chess.domain.game.TurnResult;
import chess.domain.piece.Camp;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.entity.ChessGameEntity;
import chess.entity.PieceEntity;
import chess.entity.PiecePositionEntryEntity;
import chess.entity.PositionEntity;
import java.util.Map;

public class GameSaveManager {

    private static final ChessGameDao CHESS_GAME_DAO = ChessGameDao.getInstance();
    private static final PiecePositionDao PIECE_POSITION_DAO = PiecePositionDao.getInstance();
    private static final PositionDao POSITION_DAO = PositionDao.getInstance();
    private static final PieceDao PIECE_DAO = PieceDao.getInstance();

    private static final int AUTO_INCREMENT_DEFAULT_ID = 0;

    public boolean isPreviousGameInProgress() {
        if (CHESS_GAME_DAO.hasData()) {
            int lastChessGameId = CHESS_GAME_DAO.findLastID();
            ChessGameEntity lastGame = CHESS_GAME_DAO.findById(lastChessGameId);
            return lastGame.getStatusValue() != Camp.NONE.ordinal();
        }
        return false;
    }

    public ChessGameEntity saveNewGame(ChessGame chessGame) {
        clearGame();
        int savedPiecePositionId = PIECE_POSITION_DAO.add();
        int savedChessGameId = saveNewChessGame(chessGame, savedPiecePositionId);
        addPiecePositionEntry(chessGame, savedPiecePositionId);
        return CHESS_GAME_DAO.findById(savedChessGameId);
    }

    public ChessGameEntity loadLastGame() {
        validateLoadGameExist();
        int lastChessGameID = CHESS_GAME_DAO.findLastID();
        return CHESS_GAME_DAO.findById(lastChessGameID);
    }

    public void saveProgressGame(InProgressGameInfo inProgressGameInfo, TurnResult turnResult) {
        ChessGameEntity chessGameData = inProgressGameInfo.chessGameData();
        ChessGame chessGame = inProgressGameInfo.chessGame();

        int piecePositionId = chessGameData.getPiecePositionId();
        int movedPositionId = extractPositionId(turnResult);
        int movedPieceId = extractPieceId(turnResult);

        deletePreviousPosition(piecePositionId, movedPieceId);
        updateMoveResult(piecePositionId, movedPositionId, movedPieceId);
        updateGameStatus(chessGame, chessGameData.getChessGameId(), piecePositionId);
    }

    private void clearGame() {
        CHESS_GAME_DAO.truncateTable();
        PIECE_POSITION_DAO.truncateTableEntry();

        CHESS_GAME_DAO.deleteForeignKeyConstraint();
        PIECE_POSITION_DAO.deleteForeignKeyConstraint();
        PIECE_POSITION_DAO.truncateTable();

        CHESS_GAME_DAO.addForeignKeyConstraint();
        PIECE_POSITION_DAO.addForeignKeyConstraint();
    }

    private int saveNewChessGame(ChessGame chessGame, int savedPiecePositionId) {
        Camp camp = chessGame.requestCurrentTurn();
        return CHESS_GAME_DAO.add(new ChessGameEntity(AUTO_INCREMENT_DEFAULT_ID, savedPiecePositionId, camp));
    }

    private void addPiecePositionEntry(ChessGame chessGame, int savedPiecePositionId) {
        Map<Position, Piece> piecePosition = chessGame.requestPiecePosition();
        for (Position position : piecePosition.keySet()) {
            Piece piece = piecePosition.get(position);

            int positionId = POSITION_DAO.findId(new PositionEntity(position.getLettering(), position.getNumbering()));
            int pieceId = PIECE_DAO.findId(new PieceEntity(piece.getPieceType(), piece.getCamp()));
            PIECE_POSITION_DAO.addEntry(new PiecePositionEntryEntity(savedPiecePositionId, positionId, pieceId));
        }
    }

    public Map<PositionEntity, PieceEntity> loadPiecePositionByGame(ChessGameEntity chessGameEntity) {
        int piecePositionId = chessGameEntity.getPiecePositionId();
        return PIECE_POSITION_DAO.findEntryByPiecePositionId(piecePositionId);
    }

    private void deletePreviousPosition(int piecePositionId, int pieceIdToDelete) {
        PIECE_POSITION_DAO.deleteEntryByPiece(piecePositionId, pieceIdToDelete);
    }

    private void updateMoveResult(int piecePositionIdToUpdate, int positionIdToUpdate, int pieceIdToUpdate) {
        if (PIECE_POSITION_DAO.hasEntryDataByPositionId(positionIdToUpdate)) {
            PIECE_POSITION_DAO.updateEntry(new PiecePositionEntryEntity(
                    piecePositionIdToUpdate, positionIdToUpdate, pieceIdToUpdate));
            return;
        }
        PIECE_POSITION_DAO.addEntry(new PiecePositionEntryEntity(
                piecePositionIdToUpdate, positionIdToUpdate, pieceIdToUpdate));
    }

    private void updateGameStatus(ChessGame chessGame, int chessGameId, int piecePositionId) {
        Camp turnToMove = extractStatusValue(chessGame);
        CHESS_GAME_DAO.updateStatus(new ChessGameEntity(chessGameId, piecePositionId, turnToMove));
    }

    private int extractPositionId(TurnResult turnResult) {
        Position target = turnResult.target();
        return POSITION_DAO.findId(new PositionEntity(target.getLettering(), target.getNumbering()));
    }

    private int extractPieceId(TurnResult turnResult) {
        Piece movedPiece = turnResult.movedPiece();
        return PIECE_DAO.findId(new PieceEntity(movedPiece.getPieceType(), movedPiece.getCamp()));
    }

    private Camp extractStatusValue(ChessGame chessGame) {
        if (!chessGame.isGameInProgress()) {
            return Camp.NONE;
        }
        if (chessGame.requestCurrentTurn() == Camp.WHITE) {
            return Camp.WHITE;
        }
        return Camp.BLACK;
    }

    private void validateLoadGameExist() {
        if (!isPreviousGameInProgress()) {
            throw new IllegalArgumentException("[ERROR] 진행중인 불러올 게임이 없습니다.");
        }
    }
}
