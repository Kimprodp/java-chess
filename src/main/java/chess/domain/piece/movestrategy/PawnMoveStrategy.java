package chess.domain.piece.movestrategy;

import chess.domain.PiecePosition;
import chess.domain.piece.Camp;
import chess.domain.piece.Piece;
import chess.domain.position.BoardPosition;
import chess.domain.position.Numbering;
import chess.domain.position.Position;
import java.util.Set;

public final class PawnMoveStrategy extends MovablePositionGenerator implements MoveStrategy {

    private static final PawnMoveStrategy INSTANCE = new PawnMoveStrategy();
    private static final int TWO_STEP = 2;


    private PawnMoveStrategy() {
    }

    public static PawnMoveStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public Set<Position> move(Position standardPosition, PiecePosition piecePosition) {
        return generateMovablePosition(standardPosition, piecePosition);
    }

    private Set<Position> generateMovablePosition(Position standardPosition, PiecePosition piecePosition) {
        initializeMovablePosition(standardPosition);

        Piece pawn = piecePosition.findChessPieceOnPosition(standardPosition);
        if (pawn.getCamp() == Camp.WHITE) {
            addDirectionByWhiteCamp(standardPosition, piecePosition);
        }
        if (pawn.getCamp() == Camp.BLACK) {
            addDirectionByBlackCamp(standardPosition, piecePosition);
        }
        return getMovablePosition();
    }

    private void addDirectionByWhiteCamp(Position standardPosition, PiecePosition piecePosition) {
        addForwardByWhiteCamp(standardPosition, piecePosition);
        addUpLeftDirection(standardPosition, piecePosition);
        addUpRightDirection(standardPosition, piecePosition);
    }

    private void addDirectionByBlackCamp(Position standardPosition, PiecePosition piecePosition) {
        addForwardByBlackCamp(standardPosition, piecePosition);
        addDownLeftDirection(standardPosition, piecePosition);
        addDownRightDirection(standardPosition, piecePosition);
    }

    @Override
    protected void addUpDirection(Position standardPosition, PiecePosition piecePosition) {
        if (!BoardPosition.canFindUpPosition(standardPosition)) {
            return;
        }

        Position upPosition = BoardPosition.findUpPosition(standardPosition);
        if (!piecePosition.hasPieceAt(upPosition)) {
            addPosition(upPosition, piecePosition);
        }
    }

    @Override
    protected void addDownDirection(Position standardPosition, PiecePosition piecePosition) {
        if (!BoardPosition.canFindDownPosition(standardPosition)) {
            return;
        }

        Position downPosition = BoardPosition.findDownPosition(standardPosition);
        if (!piecePosition.hasPieceAt(downPosition)) {
            addPosition(downPosition, piecePosition);
        }
    }

    @Override
    protected void addUpLeftDirection(Position standardPosition, PiecePosition piecePosition) {
        if (!BoardPosition.canFindUpPosition(standardPosition) ||
                !BoardPosition.canFindLeftPosition(standardPosition)) {
            return;
        }

        Position upPosition = BoardPosition.findUpPosition(standardPosition);
        Position upLeftPosition = BoardPosition.findLeftPosition(upPosition);
        if (piecePosition.hasPieceAt(upLeftPosition)) {
            addPosition(upLeftPosition, piecePosition);
        }
    }

    @Override
    protected void addUpRightDirection(Position standardPosition, PiecePosition piecePosition) {
        if (!BoardPosition.canFindUpPosition(standardPosition) ||
                !BoardPosition.canFindRightPosition(standardPosition)) {
            return;
        }

        Position upPosition = BoardPosition.findUpPosition(standardPosition);
        Position upRightPosition = BoardPosition.findRightPosition(upPosition);
        if (piecePosition.hasPieceAt(upRightPosition)) {
            addPosition(upRightPosition, piecePosition);
        }
    }

    @Override
    protected void addDownLeftDirection(Position standardPosition, PiecePosition piecePosition) {
        if (!BoardPosition.canFindDownPosition(standardPosition) ||
                !BoardPosition.canFindLeftPosition(standardPosition)) {
            return;
        }

        Position downPosition = BoardPosition.findDownPosition(standardPosition);
        Position downLeftPosition = BoardPosition.findLeftPosition(downPosition);
        if (piecePosition.hasPieceAt(downLeftPosition)) {
            addPosition(downLeftPosition, piecePosition);
        }
    }

    @Override
    protected void addDownRightDirection(Position standardPosition, PiecePosition piecePosition) {
        if (!BoardPosition.canFindDownPosition(standardPosition) ||
                !BoardPosition.canFindRightPosition(standardPosition)) {
            return;
        }

        Position downPosition = BoardPosition.findDownPosition(standardPosition);
        Position downRightPosition = BoardPosition.findRightPosition(downPosition);
        if (piecePosition.hasPieceAt(downRightPosition)) {
            addPosition(downRightPosition, piecePosition);
        }
    }

    private void addForwardByWhiteCamp(Position standardPosition, PiecePosition piecePosition) {
        addUpDirection(standardPosition, piecePosition);
        Position upPosition = BoardPosition.findUpPosition(standardPosition);
        if (isAddTwoStepByWhite(standardPosition, piecePosition, upPosition)) {
            Position twoStepPosition = BoardPosition.findUpPosition(standardPosition);
            addUpDirection(twoStepPosition, piecePosition);
        }
    }

    private void addForwardByBlackCamp(Position standardPosition, PiecePosition piecePosition) {
        addDownDirection(standardPosition, piecePosition);
        Position downPosition = BoardPosition.findDownPosition(standardPosition);
        if (isAddTwoStepByBlack(standardPosition, piecePosition, downPosition)) {
            Position twoStepPosition = BoardPosition.findDownPosition(standardPosition);
            addDownDirection(twoStepPosition, piecePosition);
        }
    }

    private static boolean isAddTwoStepByWhite(Position standardPosition, PiecePosition piecePosition,
                                               Position upPosition) {
        return standardPosition.getNumbering() == Numbering.TWO &&
                BoardPosition.canFindUpPosition(standardPosition, TWO_STEP) &&
                !piecePosition.hasPieceAt(upPosition);
    }

    private static boolean isAddTwoStepByBlack(Position standardPosition, PiecePosition piecePosition,
                                               Position downPosition) {
        return standardPosition.getNumbering() == Numbering.SEVEN &&
                BoardPosition.canFindDownPosition(standardPosition, TWO_STEP) &&
                !piecePosition.hasPieceAt(downPosition);
    }
}
