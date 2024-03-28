package chess.domain.game;

import chess.domain.position.Position;
import chess.dto.ChessStatusDto;
import chess.dto.PiecePositionDto;

public class ChessGame {

    private final TurnExecutor turnExecutor;
    private final ChessStatus chessStatus;

    public ChessGame(TurnExecutor turnExecutor, ChessStatus chessStatus) {
        this.turnExecutor = turnExecutor;
        this.chessStatus = chessStatus;
    }

    public PiecePositionDto executeTurn(Position moveSource, Position target) {
        validateGameInProgress();
        return turnExecutor.execute(moveSource, target, chessStatus);
    }

    public ChessStatusDto requestStatus() {
        return chessStatus.getStatus();
    }

    public boolean isGameInProgress() {
        return chessStatus.isGameInProgress();
    }

    private void validateGameInProgress() {
        if (!chessStatus.isGameInProgress()) {
            throw new IllegalArgumentException("[ERROR] 종료된 게임입니다.");
        }
    }
}
