package chess.domain.piece.movestrategy;

import chess.domain.PiecePosition;
import chess.domain.position.Position;
import java.util.Set;

public final class KingMoveStrategy extends MovablePositionGenerator implements MoveStrategy {

    private static final KingMoveStrategy INSTANCE = new KingMoveStrategy();

    private KingMoveStrategy() {
    }

    public static KingMoveStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public Set<Position> move(Position standardPosition, PiecePosition PiecePosition) {
        return generateMovablePosition(standardPosition, PiecePosition);
    }

    private Set<Position> generateMovablePosition(Position standardPosition, PiecePosition piecePosition) {
        initializeMovablePosition(standardPosition);
        addUpDirection(piecePosition);
        addDownDirection(piecePosition);
        addLeftDirection(piecePosition);
        addRightDirection(piecePosition);
        addUpLeftDirection(piecePosition);
        addUpRightDirection(piecePosition);
        addDownLeftDirection(piecePosition);
        addDownRightDirection(piecePosition);

        return getMovablePosition();
    }
}
