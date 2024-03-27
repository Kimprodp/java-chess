package chess.domain;

import chess.domain.piece.Camp;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.position.Position;
import chess.dto.PieceDto;
import chess.dto.PiecePositionDto;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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

    public Position findPositionByPiece(Piece piece) {
        return piecePosition.keySet().stream()
                .filter(position -> piecePosition.get(position) == piece)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 체스말이 체스판 위에 있지 않습니다. : " + piece));
    }

    public List<Piece> findPieceByTypeAndCamp(PieceType pieceType, Camp camp) {
        return piecePosition.keySet().stream()
                .filter(position -> {
                    Piece piece = piecePosition.get(position);
                    return piece.getPieceType() == pieceType && piece.getCamp() == camp;
                })
                .map(piecePosition::get)
                .toList();
    }

    public void movePiece(Piece piece, Position targetPosition) {
        Position positionByPiece = findPositionByPiece(piece);
        piecePosition.remove(positionByPiece);
        piecePosition.put(targetPosition, piece);
    }

    public boolean hasPieceAt(Position position) {
        return piecePosition.containsKey(position);
    }

    public PiecePositionDto createDto() {
        Map<Position, PieceDto> collect = piecePosition.entrySet().stream()
                .collect(Collectors.toMap(
                        Entry::getKey,
                        entry -> entry.getValue().createDto()
                ));

        return new PiecePositionDto(collect);
    }
}
