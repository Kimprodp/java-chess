package chess.domain.piece;

import chess.domain.game.PiecePosition;
import chess.domain.position.Position;
import java.util.Set;

public class Piece {

    private final PieceType pieceType;
    private final Camp camp;

    public Piece(PieceType pieceType, Camp camp) {
        this.pieceType = pieceType;
        this.camp = camp;
    }

    public void move(Position startPosition, Position targetPosition, PiecePosition piecePosition) {
        validatePieceOnStartPosition(startPosition, piecePosition);
        Set<Position> movablePosition = pieceType.executeMoveStrategy(startPosition, piecePosition);
        if (movablePosition.contains(targetPosition)) {
            piecePosition.movePiece(this, targetPosition);
        }
    }

    private void validatePieceOnStartPosition(Position startPosition, PiecePosition piecePosition) {
        if (piecePosition.findChessPieceOnPosition(startPosition) != this) {
            throw new IllegalArgumentException("[ERROR] 현재 말이 시작 위치에 있지 않습니다. : " + this + startPosition);
        }
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Camp getCamp() {
        return camp;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "chessPieceType=" + pieceType +
                ", camp=" + camp +
                '}';
    }
}
