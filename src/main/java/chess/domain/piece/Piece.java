package chess.domain.piece;

import chess.domain.PiecePosition;
import chess.domain.position.Position;
import chess.dto.ChessPieceDto;
import java.util.Set;

public class Piece {

    private final ChessPieceType chessPieceType;
    private final Camp camp;

    public Piece(ChessPieceType chessPieceType, Camp camp) {
        this.chessPieceType = chessPieceType;
        this.camp = camp;
    }

    public void move(Position startPosition, Position targetPosition, PiecePosition piecePosition) {
        validatePieceOnStartPosition(startPosition, piecePosition);
        Set<Position> movablePosition = chessPieceType.executeMoveStrategy(startPosition, piecePosition);
        if (movablePosition.contains(targetPosition)) {
            piecePosition.movePiece(this, targetPosition);
        }
    }

    private void validatePieceOnStartPosition(Position startPosition, PiecePosition piecePosition) {
        if (piecePosition.findChessPieceOnPosition(startPosition) != this) {
            throw new IllegalArgumentException("[ERROR] 현재 말이 시작 위치에 있지 않습니다. : " + this + startPosition);
        }
    }

    public ChessPieceDto createDto() {
        return new ChessPieceDto(getChessPieceType(), camp);

    }

    public ChessPieceType getChessPieceType() {
        return chessPieceType;
    }

    public Camp getCamp() {
        return camp;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "chessPieceType=" + chessPieceType +
                ", camp=" + camp +
                '}';
    }
}
