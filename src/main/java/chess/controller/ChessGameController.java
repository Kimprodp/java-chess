package chess.controller;

import chess.domain.game.ChessGame;
import chess.domain.position.BoardPosition;
import chess.domain.position.Position;
import chess.dto.ChessStatusDto;
import chess.dto.MoveCommandDto;
import chess.dto.PiecePositionDto;
import chess.dto.PositionDto;
import chess.service.GameSaveManager;
import chess.view.Command;
import chess.view.CommandType;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessGameController {

    private final GameSaveManager gameSaveManager = new GameSaveManager();
    private boolean isGameInProgress;

    public void run() {
        ChessGame chessGame = startGame();

        while (isGameInProgress) {
            Command command = InputView.readCommand();
            validateProgressCommand(command);
            end(command);
            move(command, chessGame);
            status(command, chessGame);
        }

        showGameEnd(chessGame);
    }

    private ChessGame startGame() {
        OutputView.printGameStart();
        if (gameSaveManager.isPreviousGameInProgress()) {
            OutputView.printLoadGameStart();
        }
        Command command = InputView.readCommand();
        validateStartCommand(command);

        ChessGame gameToPlay = createChessGameByCommand(command);
        isGameInProgress = true;
        OutputView.printChess(gameToPlay.requestPiecePosition());

        return gameToPlay;
    }

    private ChessGame createChessGameByCommand(Command command) {
        if (command.getCommandType() == CommandType.LOAD_GAME) {
            return gameSaveManager.loadLastGame();
        }
        gameSaveManager.clearGame();
        return gameSaveManager.newGame();
    }

    private void move(Command command, ChessGame chessGame) {
        if (command.getCommandType() == CommandType.MOVE && chessGame.isGameInProgress()) {
            MoveCommandDto moveCommandDto = extractMoveCommand(command);
            PositionDto moveSourceDto = moveCommandDto.moveSource();
            PositionDto targetDto = moveCommandDto.target();

            Position moveSource = BoardPosition.findPosition(moveSourceDto.lettering(), moveSourceDto.numbering());
            Position target = BoardPosition.findPosition(targetDto.lettering(), targetDto.numbering());
            PiecePositionDto piecePositionDto = chessGame.executeTurn(moveSource, target);
            gameSaveManager.saveGame(chessGame, moveSource, target);
            isGameInProgress = chessGame.isGameInProgress();
            OutputView.printChess(piecePositionDto);
        }
    }

    private void end(Command command) {
        if (command.getCommandType() == CommandType.END) {
            isGameInProgress = false;
        }
    }

    private void status(Command command, ChessGame chessGame) {
        if (command.getCommandType() == CommandType.STATUS) {
            ChessStatusDto chessStatusDto = chessGame.requestStatus();
            OutputView.printStatus(chessStatusDto);
        }
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
