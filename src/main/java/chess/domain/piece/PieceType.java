package chess.domain.piece;

import chess.domain.PiecePosition;
import chess.domain.piece.movestrategy.BishopMoveStrategy;
import chess.domain.piece.movestrategy.KingMoveStrategy;
import chess.domain.piece.movestrategy.KnightMoveStrategy;
import chess.domain.piece.movestrategy.MoveStrategy;
import chess.domain.piece.movestrategy.PawnMoveStrategy;
import chess.domain.piece.movestrategy.QueenMoveStrategy;
import chess.domain.piece.movestrategy.RookMoveStrategy;
import chess.domain.position.Position;
import java.util.Set;
import java.util.function.Supplier;

public enum PieceType {

    KING(KingMoveStrategy::getInstance),
    QUEEN(QueenMoveStrategy::getInstance),
    BISHOP(BishopMoveStrategy::getInstance),
    KNIGHT(KnightMoveStrategy::getInstance),
    ROOK(RookMoveStrategy::getInstance),
    PAWN(PawnMoveStrategy::getInstance);

    private final Supplier<MoveStrategy> moveStrategySupplier;

    PieceType(Supplier<MoveStrategy> moveStrategySupplier) {
        this.moveStrategySupplier = moveStrategySupplier;
    }

    public Set<Position> executeMoveStrategy(Position standardPosition, PiecePosition piecePosition) {
        MoveStrategy moveStrategy = moveStrategySupplier.get();
        return moveStrategy.move(standardPosition, piecePosition);
    }
}
