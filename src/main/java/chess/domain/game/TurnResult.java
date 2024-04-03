package chess.domain.game;

import chess.domain.piece.Piece;
import chess.domain.position.Position;

public record TurnResult(
        Position target,
        Piece movedPiece
) {
}
