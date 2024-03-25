package chess.domain;

import chess.domain.position.Position;
import chess.domain.piece.Piece;
import java.util.Map;

public class PiecePosition {

    private final Map<Position, Piece> piecePosition;

    public PiecePosition(Map<Position, Piece> piecePosition) {
        this.piecePosition = piecePosition;
    }

    public Piece findChessPieceOnPosition(Position findPosition) {
        if (!hasPieceAt(findPosition)) {
            throw new IllegalArgumentException("[ERROR] 해당 위치에 체스말이 없습니다. :" + findPosition);
        }

        return piecePosition.get(findPosition);
    }

    public boolean hasPieceAt(Position position) {
        return piecePosition.containsKey(position);
    }
}
