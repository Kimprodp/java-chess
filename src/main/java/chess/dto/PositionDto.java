package chess.dto;

import chess.domain.position.Lettering;
import chess.domain.position.Numbering;

public record PositionDto(
        Lettering lettering,
        Numbering numbering
) {
}
