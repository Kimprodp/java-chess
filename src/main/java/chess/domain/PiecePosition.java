package chess.domain;

import chess.domain.piece.Piece;
import chess.domain.position.Position;
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

    public void movePiece(Piece piece, Position targetPosition) {
        Position positionByPiece = findPositionByPiece(piece);
        piecePosition.remove(positionByPiece);
        piecePosition.put(targetPosition, piece);
    }

    private Position findPositionByPiece(Piece piece) {
        return piecePosition.keySet().stream()
                .filter(position -> piecePosition.get(position) == piece)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 체스말이 체스판 위에 있지 않습니다. : " + piece));
    }

    public boolean hasPieceAt(Position position) {
        return piecePosition.containsKey(position);
    }
}
