package chess.dto;

import chess.domain.piece.Camp;
import java.util.Map;

public record ChessStatusDto(
        Camp winner,
        Map<Camp, Double> score) {
}
