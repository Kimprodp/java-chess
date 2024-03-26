package chess.domain.piece.movestrategy;

import chess.domain.PiecePosition;
import chess.domain.position.Position;
import java.util.Set;

public final class BishopMoveStrategy extends MovablePositionGenerator implements MoveStrategy {

    private static final BishopMoveStrategy INSTANCE = new BishopMoveStrategy();

    private BishopMoveStrategy() {
    }

    public static BishopMoveStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public Set<Position> move(Position standardPosition, PiecePosition piecePosition) {
        return generateMovablePosition(standardPosition, piecePosition);
    }

    private Set<Position> generateMovablePosition(Position standardPosition, PiecePosition piecePosition) {
        initializeMovablePosition(standardPosition);
        repeatAddDirections(piecePosition, this::addUpLeftDirection);
        repeatAddDirections(piecePosition, this::addUpRightDirection);
        repeatAddDirections(piecePosition, this::addDownLeftDirection);
        repeatAddDirections(piecePosition, this::addDownRightDirection);

        return getMovablePosition();
    }
}
