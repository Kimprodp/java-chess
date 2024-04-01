package chess.dto;

import chess.domain.position.Position;
import java.util.Map;

public record PiecePositionDto(
        Map<Position, PieceDto> piecePosition
) {
}
