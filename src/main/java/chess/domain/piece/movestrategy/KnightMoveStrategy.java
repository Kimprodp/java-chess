package chess.domain.piece.movestrategy;

import chess.domain.game.PiecePosition;
import chess.domain.position.BoardPosition;
import chess.domain.position.Position;
import java.util.Set;

public final class KnightMoveStrategy extends MovablePositionGenerator implements MoveStrategy {

    private static final KnightMoveStrategy INSTANCE = new KnightMoveStrategy();
    private static final int TWO_STEP = 2;

    private KnightMoveStrategy() {
    }

    public static KnightMoveStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public Set<Position> move(Position standardPosition, PiecePosition piecePosition) {
        return generateMovablePosition(standardPosition, piecePosition);
    }

    private Set<Position> generateMovablePosition(Position standardPosition, PiecePosition piecePosition) {
        initializeMovablePosition(standardPosition);
        addUPLeftLShape(standardPosition, piecePosition);
        addUPRightLShape(standardPosition, piecePosition);
        addDownLeftLShape(standardPosition, piecePosition);
        addDownRightLShape(standardPosition, piecePosition);
        addLeftUpLShape(standardPosition, piecePosition);
        addLeftDownLShape(standardPosition, piecePosition);
        addRightUpLShape(standardPosition, piecePosition);
        addRightDownLShape(standardPosition, piecePosition);

        return getMovablePosition();
    }

    private void addUPLeftLShape(Position standardPosition, PiecePosition piecePosition) {
        if (BoardPosition.canFindUpPosition(standardPosition, TWO_STEP) &&
                BoardPosition.canFindLeftPosition(standardPosition)) {
            Position twoStepUpPosition = BoardPosition.findUpPosition(standardPosition, TWO_STEP);
            Position upLeftLShapePosition = BoardPosition.findLeftPosition(twoStepUpPosition);
            addPosition(upLeftLShapePosition, piecePosition);
        }
    }

    private void addUPRightLShape(Position standardPosition, PiecePosition piecePosition) {
        if (BoardPosition.canFindUpPosition(standardPosition, TWO_STEP) &&
                BoardPosition.canFindRightPosition(standardPosition)) {
            Position twoStepUpPosition = BoardPosition.findUpPosition(standardPosition, TWO_STEP);
            Position upRightLShapePosition = BoardPosition.findRightPosition(twoStepUpPosition);
            addPosition(upRightLShapePosition, piecePosition);
        }
    }

    private void addDownLeftLShape(Position standardPosition, PiecePosition piecePosition) {
        if (BoardPosition.canFindDownPosition(standardPosition, TWO_STEP) &&
                BoardPosition.canFindLeftPosition(standardPosition)) {
            Position twoStepDownPosition = BoardPosition.findDownPosition(standardPosition, TWO_STEP);
            Position downLeftLShapePosition = BoardPosition.findLeftPosition(twoStepDownPosition);
            addPosition(downLeftLShapePosition, piecePosition);
        }
    }

    private void addDownRightLShape(Position standardPosition, PiecePosition piecePosition) {
        if (BoardPosition.canFindDownPosition(standardPosition, TWO_STEP) &&
                BoardPosition.canFindRightPosition(standardPosition)) {
            Position twoStepDownPosition = BoardPosition.findDownPosition(standardPosition, TWO_STEP);
            Position downRightLShapePosition = BoardPosition.findRightPosition(twoStepDownPosition);
            addPosition(downRightLShapePosition, piecePosition);
        }
    }

    private void addLeftUpLShape(Position standardPosition, PiecePosition piecePosition) {
        if (BoardPosition.canFindLeftPosition(standardPosition, TWO_STEP) &&
                BoardPosition.canFindUpPosition(standardPosition)) {
            Position twoStepLeftPosition = BoardPosition.findLeftPosition(standardPosition, TWO_STEP);
            Position leftUpLShapePosition = BoardPosition.findUpPosition(twoStepLeftPosition);
            addPosition(leftUpLShapePosition, piecePosition);
        }
    }

    private void addLeftDownLShape(Position standardPosition, PiecePosition piecePosition) {
        if (BoardPosition.canFindLeftPosition(standardPosition, TWO_STEP) &&
                BoardPosition.canFindDownPosition(standardPosition)) {
            Position twoStepLeftPosition = BoardPosition.findLeftPosition(standardPosition, TWO_STEP);
            Position leftDownLShapePosition = BoardPosition.findDownPosition(twoStepLeftPosition);
            addPosition(leftDownLShapePosition, piecePosition);
        }
    }

    private void addRightUpLShape(Position standardPosition, PiecePosition piecePosition) {
        if (BoardPosition.canFindRightPosition(standardPosition, TWO_STEP) &&
                BoardPosition.canFindUpPosition(standardPosition)) {
            Position twoStepRightPosition = BoardPosition.findRightPosition(standardPosition, TWO_STEP);
            Position rightUpLShapePosition = BoardPosition.findUpPosition(twoStepRightPosition);
            addPosition(rightUpLShapePosition, piecePosition);
        }
    }

    private void addRightDownLShape(Position standardPosition, PiecePosition piecePosition) {
        if (BoardPosition.canFindRightPosition(standardPosition, TWO_STEP) &&
                BoardPosition.canFindDownPosition(standardPosition)) {
            Position twoStepRightPosition = BoardPosition.findRightPosition(standardPosition, TWO_STEP);
            Position rightDownLShapePosition = BoardPosition.findDownPosition(twoStepRightPosition);
            addPosition(rightDownLShapePosition, piecePosition);
        }
    }
}
