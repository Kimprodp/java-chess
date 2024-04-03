package chess.domain.game;

import chess.domain.piece.Camp;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import chess.dto.ChessStatusDto;
import java.util.Map;

public class ChessGame {

    private final TurnExecutor turnExecutor;
    private final ChessStatus chessStatus;

    public ChessGame(TurnExecutor turnExecutor, ChessStatus chessStatus) {
        this.turnExecutor = turnExecutor;
        this.chessStatus = chessStatus;
    }

    public TurnResult executeTurn(Position moveSource, Position target) {
        validateGameInProgress();
        return turnExecutor.execute(moveSource, target, chessStatus);
    }

    public Map<Position, Piece> requestPiecePosition() {
        return turnExecutor.requestPiecePosition();
    }

    public Camp requestCurrentTurn() {
        return turnExecutor.getTurnToMove();
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
