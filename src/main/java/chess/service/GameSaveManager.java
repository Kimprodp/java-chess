package chess.service;

import chess.dao.ChessGameDao;
import chess.dao.PieceDao;
import chess.dao.PiecePositionDao;
import chess.dao.PiecePositionEntryDao;
import chess.dao.PositionDao;
import chess.domain.game.ChessGame;
import chess.domain.game.ChessStatus;
import chess.domain.game.PiecePosition;
import chess.domain.game.PiecePositionGenerator;
import chess.domain.game.TurnExecutor;
import chess.domain.piece.Camp;
import chess.domain.position.Position;
import chess.dto.PieceDto;
import chess.dto.PiecePositionDto;
import chess.entity.ChessGameEntity;
import chess.entity.PieceEntity;
import chess.entity.PiecePositionEntryEntity;
import chess.entity.PiecePositionEntryEntityToDelete;
import chess.entity.PositionEntity;
import java.util.Map;

public class GameSaveManager {

    private static final ChessGameDao CHESS_GAME_DAO = ChessGameDao.getInstance();
    private static final PiecePositionDao PIECE_POSITION_DAO = PiecePositionDao.getInstance();
    private static final PiecePositionEntryDao PIECE_POSITION_ENTRY_DAO = PiecePositionEntryDao.getINSTANCE();
    private static final PositionDao POSITION_DAO = PositionDao.getInstance();
    private static final PieceDao PIECE_DAO = PieceDao.getInstance();

    private static final int AUTO_INCREMENT_DEFAULT_ID = 0;

    private int inProgressGameID;

    public boolean isPreviousGameInProgress() {
        if (CHESS_GAME_DAO.hasData()) {
            int lastChessGameId = CHESS_GAME_DAO.findLastID();
            ChessGameEntity lastGame = CHESS_GAME_DAO.findById(lastChessGameId);
            return lastGame.getStatus_value() != Camp.NONE.ordinal();
        }
        return false;
    }

    public ChessGame newGame() {
        PiecePosition piecePosition = createPiecePositionInstance();
        ChessGame chessGame = createChessGameInstance(piecePosition, Camp.WHITE);
        saveNewGame(chessGame);
        return chessGame;
    }

    public ChessGame loadLastGame() {
        ChessGameEntity loadedGameEntity = loadProgressGame();
        PiecePosition piecePosition = createPiecePositionInstance(loadProgressGamePosition(loadedGameEntity));
        Camp turnToMove = Camp.getByOrdinal(loadedGameEntity.getStatus_value());
        return createChessGameInstance(piecePosition, turnToMove);
    }

    public void saveGame(ChessGame chessGame, Position move, Position target) {
        ChessGameEntity chessGameEntity = CHESS_GAME_DAO.findById(inProgressGameID);
        int piecePositionId = chessGameEntity.getPiecePositionId();

        deletePreviousPosition(move, piecePositionId);
        updateMoveResult(target, piecePositionId, chessGame);
        updateGameStatus(chessGame, inProgressGameID, piecePositionId);
    }

    public void clearGame() {
        CHESS_GAME_DAO.truncateTable();
        PIECE_POSITION_ENTRY_DAO.truncateTable();
        PIECE_POSITION_DAO.truncateTable();
    }

    private void saveNewGame(ChessGame chessGame) {
        int savedPiecePositionId = PIECE_POSITION_DAO.add();
        PIECE_POSITION_ENTRY_DAO.add(savedPiecePositionId, chessGame.requestPiecePosition());
        int savedChessGameId = CHESS_GAME_DAO.add(
                new ChessGameEntity(AUTO_INCREMENT_DEFAULT_ID, savedPiecePositionId, Camp.WHITE.ordinal()));
        inProgressGameID = savedChessGameId;
    }

    private ChessGameEntity loadProgressGame() {
        validateLoadGameExist();
        int lastChessGameID = CHESS_GAME_DAO.findLastID();
        ChessGameEntity lastGame = CHESS_GAME_DAO.findById(lastChessGameID);
        inProgressGameID = lastGame.getChessGameId();
        return lastGame;
    }

    private Map<PositionEntity, PieceEntity> loadProgressGamePosition(ChessGameEntity loadedGameEntity) {
        int piecePositionId = loadedGameEntity.getPiecePositionId();
        return PIECE_POSITION_ENTRY_DAO.findByPiecePositionId(piecePositionId);
    }

    private PiecePosition createPiecePositionInstance() {
        PiecePositionGenerator piecePositionGenerator = PiecePositionGenerator.getInstance();
        return new PiecePosition(piecePositionGenerator.generatePiecePosition());
    }

    private PiecePosition createPiecePositionInstance(Map<PositionEntity, PieceEntity> piecePositionEntry) {
        PiecePositionGenerator piecePositionGenerator = PiecePositionGenerator.getInstance();
        return new PiecePosition(piecePositionGenerator.generatePiecePosition(piecePositionEntry));
    }

    private ChessGame createChessGameInstance(PiecePosition piecePosition, Camp turnToMove) {
        TurnExecutor turnExecutor = new TurnExecutor(piecePosition, turnToMove);
        ChessStatus chessStatus = new ChessStatus(piecePosition);
        return new ChessGame(turnExecutor, chessStatus);
    }

    private static void deletePreviousPosition(Position move, int piecePositionId) {
        int positionIdToDelete = POSITION_DAO.findId(new PositionEntity(move.getLettering(), move.getNumbering()));
        PIECE_POSITION_ENTRY_DAO.deleteRow(new PiecePositionEntryEntityToDelete(piecePositionId, positionIdToDelete));
    }

    private static void updateMoveResult(Position target, int piecePositionId, ChessGame chessGame) {
        int positionIdToUpdate = POSITION_DAO.findId(new PositionEntity(target.getLettering(), target.getNumbering()));

        PiecePositionDto piecePositionDto = chessGame.requestPiecePosition();
        PieceDto pieceDto = piecePositionDto.piecePosition().get(target);
        int pieceIdToUpdate = PIECE_DAO.findId(new PieceEntity(pieceDto.pieceType(), pieceDto.camp()));

        if (PIECE_POSITION_ENTRY_DAO.hasDataByPositionId(positionIdToUpdate)) {
            PIECE_POSITION_ENTRY_DAO.updateRow(
                    new PiecePositionEntryEntity(piecePositionId, positionIdToUpdate, pieceIdToUpdate));
            return;
        }
        PIECE_POSITION_ENTRY_DAO.addRow(
                new PiecePositionEntryEntity(piecePositionId, positionIdToUpdate, pieceIdToUpdate));
    }

    private void updateGameStatus(ChessGame chessGame, int chessGameId, int piecePositionId) {
        Camp turnToMove = extractStatusValue(chessGame);
        CHESS_GAME_DAO.updateStatus(new ChessGameEntity(chessGameId, piecePositionId, turnToMove));
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
