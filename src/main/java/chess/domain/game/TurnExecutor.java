package chess.domain.game;

import chess.domain.piece.Camp;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import java.util.Map;

public class TurnExecutor {

    private final PiecePosition piecePosition;
    private Camp turnToMove;

    public TurnExecutor(PiecePosition piecePosition, Camp turnToMove) {
        this.piecePosition = piecePosition;
        this.turnToMove = turnToMove;
    }

    public TurnResult execute(Position moveSource, Position target, ChessStatus chessStatus) {
        validatePieceExistsOnSquare(moveSource);
        Piece pieceToMove = piecePosition.findChessPieceOnPosition(moveSource);
        validateTurnSide(pieceToMove);
        pieceToMove.move(moveSource, target, piecePosition);
        chessStatus.updateStatus(piecePosition);
        changeTurn();
        return new TurnResult(target, pieceToMove);
    }

    public Map<Position, Piece> requestPiecePosition() {
        return piecePosition.getPiecePosition();
    }

    private void changeTurn() {
        if (turnToMove == Camp.WHITE) {
            turnToMove = Camp.BLACK;
            return;
        }
        turnToMove = Camp.WHITE;
    }

    private void validatePieceExistsOnSquare(Position moveSource) {
        if (!piecePosition.hasPieceAt(moveSource)) {
            throw new IllegalArgumentException("[ERROR] 이동할 체스말이 없습니다. : " + moveSource);
        }
    }

    private void validateTurnSide(Piece pieceToMove) {
        if (pieceToMove.getCamp() != turnToMove) {
            throw new IllegalArgumentException("[ERROR] 현재 실행 가능한 턴의 체스말이 아닙니다. : " + pieceToMove);
        }
    }

    public Camp getTurnToMove() {
        return turnToMove;
    }
}
