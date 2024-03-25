package chess.domain.piece.movestrategy;

import chess.domain.PiecePosition;
import chess.domain.position.Position;
import java.util.Set;

public interface MoveStrategy {

    Set<Position> move(Position standardPosition, PiecePosition chessBoard);
}
