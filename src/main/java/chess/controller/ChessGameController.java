package chess.controller;

import chess.domain.game.ChessGame;
import chess.domain.game.ChessStatus;
import chess.domain.game.InProgressGameInfo;
import chess.domain.game.PiecePosition;
import chess.domain.game.PiecePositionGenerator;
import chess.domain.game.TurnExecutor;
import chess.domain.game.TurnResult;
import chess.domain.piece.Camp;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.position.BoardPosition;
import chess.domain.position.Position;
import chess.dto.ChessStatusDto;
import chess.dto.MoveCommandDto;
import chess.dto.PieceDto;
import chess.dto.PiecePositionDto;
import chess.dto.PositionDto;
import chess.entity.ChessGameEntity;
import chess.entity.PieceEntity;
import chess.entity.PositionEntity;
import chess.service.GameSaveManager;
import chess.view.Command;
import chess.view.CommandType;
import chess.view.InputView;
import chess.view.OutputView;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class ChessGameController {

    private final GameSaveManager gameSaveManager = new GameSaveManager();

    public void run() {
        InProgressGameInfo inProgressGameInfo = startGame();
        ChessGame chessGame = inProgressGameInfo.chessGame();
        showChess(chessGame);

        boolean isGameInProgress = true;
        while (isGameInProgress) {
            Command command = readPlayCommand();
            applyMove(command, inProgressGameInfo);
            applyStatus(command, chessGame);
            isGameInProgress = chessGame.isGameInProgress() && command.getCommandType() != CommandType.END;
        }
        showGameEnd(chessGame);
    }

    private InProgressGameInfo startGame() {
        showGameStart();
        Command command = readStartCommand();
        return createChessGameByCommand(command);
    }

    private void showGameStart() {
        OutputView.printGameStart();
        if (gameSaveManager.isPreviousGameInProgress()) {
            OutputView.printLoadGameStart();
        }
    }

    private InProgressGameInfo createChessGameByCommand(Command command) {
        if (command.getCommandType() == CommandType.LOAD_GAME && gameSaveManager.isPreviousGameInProgress()) {
            return createLoadGame();
        }

        return createNewGame();
    }

    private InProgressGameInfo createLoadGame() {
        ChessGameEntity chessGameData = gameSaveManager.loadLastGame();
        Camp turnToMove = Camp.getByOrdinal(chessGameData.getStatusValue());
        Map<PositionEntity, PieceEntity> piecePositionData = gameSaveManager.loadPiecePositionByGame(chessGameData);
        PiecePosition piecePosition = createPiecePosition(piecePositionData);
        ChessGame chessGame = createChessGame(piecePosition, turnToMove);
        return new InProgressGameInfo(chessGameData, chessGame);
    }

    private InProgressGameInfo createNewGame() {
        PiecePosition piecePosition = createPiecePosition();
        ChessGame chessGame = createChessGame(piecePosition);
        ChessGameEntity chessGameData = gameSaveManager.saveNewGame(chessGame);
        return new InProgressGameInfo(chessGameData, chessGame);
    }

    private PiecePosition createPiecePosition() {
        PiecePositionGenerator piecePositionGenerator = PiecePositionGenerator.getInstance();
        return new PiecePosition(piecePositionGenerator.generatePiecePosition());
    }

    private PiecePosition createPiecePosition(Map<PositionEntity, PieceEntity> piecePositionEntry) {
        PiecePositionGenerator piecePositionGenerator = PiecePositionGenerator.getInstance();
        return new PiecePosition(piecePositionGenerator.generatePiecePosition(piecePositionEntry));
    }

    private ChessGame createChessGame(PiecePosition piecePosition, Camp turnToMove) {
        TurnExecutor turnExecutor = new TurnExecutor(piecePosition, turnToMove);
        ChessStatus chessStatus = new ChessStatus(piecePosition);
        return new ChessGame(turnExecutor, chessStatus);
    }

    private ChessGame createChessGame(PiecePosition piecePosition) {
        TurnExecutor turnExecutor = new TurnExecutor(piecePosition);
        ChessStatus chessStatus = new ChessStatus(piecePosition);
        return new ChessGame(turnExecutor, chessStatus);
    }

    private void applyMove(Command command, InProgressGameInfo inProgressGameInfo) {
        ChessGame chessGame = inProgressGameInfo.chessGame();
        if (command.getCommandType() == CommandType.MOVE && chessGame.isGameInProgress()) {
            MoveCommandDto moveCommandDto = extractMoveCommand(command);
            PositionDto moveSourceDto = moveCommandDto.moveSource();
            PositionDto targetDto = moveCommandDto.target();

            Position moveSource = BoardPosition.findPosition(moveSourceDto.lettering(), moveSourceDto.numbering());
            Position target = BoardPosition.findPosition(targetDto.lettering(), targetDto.numbering());
            TurnResult turnResult = chessGame.executeTurn(moveSource, target);
            gameSaveManager.saveProgressGame(inProgressGameInfo, turnResult);
            showChess(chessGame);
        }
    }

    private void applyStatus(Command command, ChessGame chessGame) {
        if (command.getCommandType() == CommandType.STATUS) {
            ChessStatusDto chessStatusDto = chessGame.requestStatus();
            OutputView.printStatus(chessStatusDto);
        }
    }

    private Command readStartCommand() {
        Command command = InputView.readCommand();
        validateStartCommand(command);
        return command;
    }

    private Command readPlayCommand() {
        Command command = InputView.readCommand();
        validateProgressCommand(command);
        return command;
    }

    private void showChess(ChessGame chessGame) {
        Map<Position, Piece> piecePosition = chessGame.requestPiecePosition();
        OutputView.printChess(createPiecePositionDto(piecePosition));
    }

    private static void showGameEnd(ChessGame chessGame) {
        OutputView.printGameEnd();
        OutputView.printStatus(chessGame.requestStatus());
    }

    private MoveCommandDto extractMoveCommand(Command command) {
        return command
                .getMoveCommandDto()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 이동 정보가 없습니다. : " + command));
    }

    private PiecePositionDto createPiecePositionDto(Map<Position, Piece> piecePosition) {
        Map<Position, PieceDto> collect = piecePosition.entrySet().stream()
                .collect(Collectors.toMap(
                        Entry::getKey,
                        entry -> {
                            PieceType pieceType = entry.getValue().getPieceType();
                            Camp camp = entry.getValue().getCamp();
                            return new PieceDto(pieceType, camp);
                        }
                ));

        return new PiecePositionDto(collect);
    }

    private void validateStartCommand(Command command) {
        if (command.getCommandType() != CommandType.START && command.getCommandType() != CommandType.LOAD_GAME) {
            throw new IllegalArgumentException("[ERROR] 먼저 게임 시작이 필요합니다.");
        }
        if (command.getCommandType() == CommandType.LOAD_GAME && !gameSaveManager.isPreviousGameInProgress()) {
            throw new IllegalArgumentException("[ERROR] 불러올 진행중인 게임이 없습니다.");
        }
    }

    private void validateProgressCommand(Command command) {
        if (command.getCommandType() == CommandType.START || command.getCommandType() == CommandType.LOAD_GAME) {
            throw new IllegalArgumentException("[ERROR] 이미 게임이 시작되었습니다.");
        }
    }
}
