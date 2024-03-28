package chess.controller;

import chess.domain.game.ChessGame;
import chess.domain.game.ChessStatus;
import chess.domain.game.PiecePosition;
import chess.domain.game.PiecePositionInitializer;
import chess.domain.game.TurnExecutor;
import chess.domain.position.BoardPosition;
import chess.domain.position.Position;
import chess.dto.MoveCommandDto;
import chess.dto.PiecePositionDto;
import chess.dto.PositionDto;
import chess.view.Command;
import chess.view.CommandType;
import chess.view.InputView;
import chess.view.OutputView;

public class ChessGameController {

    private boolean isGameInProgress;

    public void run() {
        ChessGame chessGame = startGame();

        while (isGameInProgress) {
            Command command = InputView.readCommand();

            validateProgressCommand(command);
            end(command);
            move(command, chessGame);
            status(command);
        }
    }

    private ChessGame startGame() {
        OutputView.printGameStart();
        Command command = InputView.readCommand();
        validateStartCommand(command);
        isGameInProgress = true;

        PiecePosition piecePosition = initialPiecePosition(command);
        OutputView.printChess(piecePosition.createDto());

        return createChessGame(piecePosition);
    }

    private PiecePosition initialPiecePosition(Command command) {
        PiecePositionInitializer piecePositionInitializer = PiecePositionInitializer.getInstance();
        return new PiecePosition(piecePositionInitializer.generateInitializedPiecePosition());
    }

    private ChessGame createChessGame(PiecePosition piecePosition) {
        TurnExecutor turnExecutor = new TurnExecutor(piecePosition);
        ChessStatus chessStatus = new ChessStatus();
        return new ChessGame(turnExecutor, chessStatus);
    }

    private void move(Command command, ChessGame chessGame) {
        if (command.getCommandType() == CommandType.MOVE) {
            MoveCommandDto moveCommandDto = extractMoveCommand(command);
            PositionDto moveSourceDto = moveCommandDto.moveSource();
            PositionDto targetDto = moveCommandDto.target();

            Position moveSource = BoardPosition.findPosition(moveSourceDto.lettering(), moveSourceDto.numbering());
            Position target = BoardPosition.findPosition(targetDto.lettering(), targetDto.numbering());
            PiecePositionDto piecePositionDto = chessGame.executeTurn(moveSource, target);
            OutputView.printChess(piecePositionDto);
        }
    }

    private void end(Command command) {
        if (command.getCommandType() == CommandType.END) {
            isGameInProgress = false;
        }
    }

    //TODO
    private void status(Command command) {
        if (command.getCommandType() == CommandType.STATUS) {
            isGameInProgress = false;
        }
    }

    private MoveCommandDto extractMoveCommand(Command command) {
        return command
                .getMoveCommandDto()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 이동 정보가 없습니다. : " + command));
    }

    private void validateStartCommand(Command command) {
        if (command.getCommandType() != CommandType.START) {
            throw new IllegalArgumentException("[ERROR] 먼저 게임 시작이 필요합니다.");
        }
    }

    private void validateProgressCommand(Command command) {
        if (command.getCommandType() == CommandType.START) {
            throw new IllegalArgumentException("[ERROR] 이미 게임이 시작되었습니다.");
        }
    }
}
