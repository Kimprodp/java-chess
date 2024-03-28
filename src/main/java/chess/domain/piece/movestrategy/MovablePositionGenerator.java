package chess.domain.piece.movestrategy;

import chess.domain.game.PiecePosition;
import chess.domain.piece.Piece;
import chess.domain.position.BoardPosition;
import chess.domain.position.Position;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public sealed class MovablePositionGenerator
        permits KingMoveStrategy, QueenMoveStrategy, BishopMoveStrategy, KnightMoveStrategy, RookMoveStrategy,
        PawnMoveStrategy {

    private final List<Position> movablePosition = new ArrayList<>();
    private Position standardPosition;

    protected MovablePositionGenerator() {
    }

    public final void initializeMovablePosition(Position standardPosition) {
        movablePosition.clear();
        this.standardPosition = standardPosition;
    }

    public final void repeatAddDirections(PiecePosition piecePosition,
                                          BiConsumer<Position, PiecePosition> directionAdder) {
        Position lastAddedPosition = standardPosition;
        int sizeBeforeAddition;

        do {
            sizeBeforeAddition = movablePosition.size();
            directionAdder.accept(lastAddedPosition, piecePosition);
            lastAddedPosition = calculateLastAddedPosition();
        } while (canPositionAdd(sizeBeforeAddition, lastAddedPosition, piecePosition));
    }

    protected void addUpDirection(PiecePosition piecePosition) {
        if (BoardPosition.canFindUpPosition(standardPosition)) {
            Position upPosition = BoardPosition.findUpPosition(standardPosition);
            addPosition(upPosition, piecePosition);
        }
    }

    protected void addUpDirection(Position lastAddedPosition, PiecePosition piecePosition) {
        if (BoardPosition.canFindUpPosition(lastAddedPosition)) {
            Position upPosition = BoardPosition.findUpPosition(lastAddedPosition);
            addPosition(upPosition, piecePosition);
        }
    }

    protected void addDownDirection(PiecePosition piecePosition) {
        if (BoardPosition.canFindDownPosition(standardPosition)) {
            Position downPosition = BoardPosition.findDownPosition(standardPosition);
            addPosition(downPosition, piecePosition);
        }
    }

    protected void addDownDirection(Position lastAddedPosition, PiecePosition piecePosition) {
        if (BoardPosition.canFindDownPosition(lastAddedPosition)) {
            Position downPosition = BoardPosition.findDownPosition(lastAddedPosition);
            addPosition(downPosition, piecePosition);
        }
    }

    protected void addLeftDirection(PiecePosition piecePosition) {
        if (BoardPosition.canFindLeftPosition(standardPosition)) {
            Position leftPosition = BoardPosition.findLeftPosition(standardPosition);
            addPosition(leftPosition, piecePosition);
        }
    }

    protected void addLeftDirection(Position lastAddedPosition, PiecePosition piecePosition) {
        if (BoardPosition.canFindLeftPosition(lastAddedPosition)) {
            Position leftPosition = BoardPosition.findLeftPosition(lastAddedPosition);
            addPosition(leftPosition, piecePosition);
        }
    }

    protected void addRightDirection(PiecePosition piecePosition) {
        if (BoardPosition.canFindRightPosition(standardPosition)) {
            Position leftPosition = BoardPosition.findRightPosition(standardPosition);
            addPosition(leftPosition, piecePosition);
        }
    }

    protected void addRightDirection(Position lastAddedPosition, PiecePosition piecePosition) {
        if (BoardPosition.canFindRightPosition(lastAddedPosition)) {
            Position leftPosition = BoardPosition.findRightPosition(lastAddedPosition);
            addPosition(leftPosition, piecePosition);
        }
    }

    protected void addUpLeftDirection(PiecePosition piecePosition) {
        if (BoardPosition.canFindUpPosition(standardPosition) &&
                BoardPosition.canFindLeftPosition(standardPosition)) {
            Position upPosition = BoardPosition.findUpPosition(standardPosition);
            Position upLeftPosition = BoardPosition.findLeftPosition(upPosition);
            addPosition(upLeftPosition, piecePosition);
        }
    }

    protected void addUpLeftDirection(Position lastAddedPosition, PiecePosition piecePosition) {
        if (BoardPosition.canFindUpPosition(lastAddedPosition) &&
                BoardPosition.canFindLeftPosition(lastAddedPosition)) {
            Position upPosition = BoardPosition.findUpPosition(lastAddedPosition);
            Position upLeftPosition = BoardPosition.findLeftPosition(upPosition);
            addPosition(upLeftPosition, piecePosition);
        }
    }

    protected void addUpRightDirection(PiecePosition piecePosition) {
        if (BoardPosition.canFindUpPosition(standardPosition) &&
                BoardPosition.canFindRightPosition(standardPosition)) {
            Position upPosition = BoardPosition.findUpPosition(standardPosition);
            Position upRightPosition = BoardPosition.findRightPosition(upPosition);
            addPosition(upRightPosition, piecePosition);
        }
    }

    protected void addUpRightDirection(Position lastAddedPosition, PiecePosition piecePosition) {
        if (BoardPosition.canFindUpPosition(lastAddedPosition) &&
                BoardPosition.canFindRightPosition(lastAddedPosition)) {
            Position upPosition = BoardPosition.findUpPosition(lastAddedPosition);
            Position upRightPosition = BoardPosition.findRightPosition(upPosition);
            addPosition(upRightPosition, piecePosition);
        }
    }

    protected void addDownLeftDirection(PiecePosition piecePosition) {
        if (BoardPosition.canFindUpPosition(standardPosition) &&
                BoardPosition.canFindLeftPosition(standardPosition)) {
            Position downPosition = BoardPosition.findDownPosition(standardPosition);
            Position downLeftPosition = BoardPosition.findLeftPosition(downPosition);
            addPosition(downLeftPosition, piecePosition);
        }
    }

    protected void addDownLeftDirection(Position lastAddedPosition, PiecePosition piecePosition) {
        if (BoardPosition.canFindDownPosition(lastAddedPosition) &&
                BoardPosition.canFindLeftPosition(lastAddedPosition)) {
            Position downPosition = BoardPosition.findDownPosition(lastAddedPosition);
            Position downLeftPosition = BoardPosition.findLeftPosition(downPosition);
            addPosition(downLeftPosition, piecePosition);
        }
    }

    protected void addDownRightDirection(PiecePosition piecePosition) {
        if (BoardPosition.canFindDownPosition(standardPosition) &&
                BoardPosition.canFindRightPosition(standardPosition)) {
            Position downPosition = BoardPosition.findDownPosition(standardPosition);
            Position downRightPosition = BoardPosition.findRightPosition(downPosition);
            addPosition(downRightPosition, piecePosition);
        }
    }

    protected void addDownRightDirection(Position lastAddedPosition, PiecePosition piecePosition) {
        if (BoardPosition.canFindDownPosition(lastAddedPosition) &&
                BoardPosition.canFindRightPosition(lastAddedPosition)) {
            Position downPosition = BoardPosition.findDownPosition(lastAddedPosition);
            Position downRightPosition = BoardPosition.findRightPosition(downPosition);
            addPosition(downRightPosition, piecePosition);
        }
    }

    public final void addPosition(Position positionToAdd, PiecePosition piecePosition) {
        if (!piecePosition.hasPieceAt(positionToAdd)) {
            movablePosition.add(positionToAdd);
            return;
        }
        if (isDifferentCamp(positionToAdd, piecePosition)) {
            movablePosition.add(positionToAdd);
        }
    }

    private Position calculateLastAddedPosition() {
        if (movablePosition.isEmpty()) {
            return standardPosition;
        }
        return movablePosition.get(movablePosition.size() - 1);
    }

    private boolean isDifferentCamp(Position positionToAdd, PiecePosition piecePosition) {
        Piece pieceOnStartPosition = piecePosition.findChessPieceOnPosition(standardPosition);
        Piece pieceOnMovePosition = piecePosition.findChessPieceOnPosition(positionToAdd);

        return pieceOnStartPosition.getCamp() != pieceOnMovePosition.getCamp();
    }

    private boolean canPositionAdd(int sizeBeforeAddition, Position lastAddedPosition, PiecePosition piecePosition) {
        boolean isPositionAdded = movablePosition.size() == sizeBeforeAddition + 1;
        boolean hasPieceAtLastPosition = piecePosition.hasPieceAt(lastAddedPosition);
        return isPositionAdded && !hasPieceAtLastPosition;
    }

    protected final Set<Position> getMovablePosition() {
        return new HashSet<>(movablePosition);
    }
}
