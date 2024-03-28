package chess.domain.game;

import chess.domain.position.BoardPosition;
import chess.domain.position.Lettering;
import chess.domain.position.Numbering;
import chess.domain.position.Position;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceGenerator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PiecePositionInitializer {

    private static final List<Numbering> PIECE_STARTING_NUMBERING = List.of(
            Numbering.ONE,
            Numbering.TWO,
            Numbering.SEVEN,
            Numbering.EIGHT)
            ;
    private static final PiecePositionInitializer INSTANCE = new PiecePositionInitializer();

    private final List<Position> startingPosition = fetchStartingPosition();

    private PiecePositionInitializer() {
    }

    public static PiecePositionInitializer getInstance() {
        return INSTANCE;
    }

    public Map<Position, Piece> generateInitializedPiecePosition() {
        Map<Position, Piece> piecePosition = new HashMap<>();

        for (Position position : startingPosition) {
            PieceGenerator pieceGenerator = PieceGenerator.getInstance();
            Piece piece = pieceGenerator.generate(position);
            piecePosition.put(position, piece);
        }

        return piecePosition;
    }

    private List<Position> fetchStartingPosition() {
        return Arrays.stream(Lettering.values())
                .flatMap(lettering -> Arrays.stream(Numbering.values())
                        .filter(PIECE_STARTING_NUMBERING::contains)
                        .map(numbering -> BoardPosition.findPosition(lettering, numbering)))
                .toList();
    }
}
