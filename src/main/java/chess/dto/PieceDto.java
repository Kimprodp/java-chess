package chess.dto;

import chess.domain.piece.Camp;
import chess.domain.piece.PieceType;

public record PieceDto(
        PieceType pieceType,
        Camp camp) {
}
