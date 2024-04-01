package chess.domain.game;

import chess.domain.piece.Camp;
import chess.domain.piece.InitialPieceGenerator;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.position.BoardPosition;
import chess.domain.position.Lettering;
import chess.domain.position.Numbering;
import chess.domain.position.Position;
import chess.entity.PieceEntity;
import chess.entity.PositionEntity;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PiecePositionGenerator {

    private static final List<Numbering> PIECE_STARTING_NUMBERING = List.of(
            Numbering.ONE,
            Numbering.TWO,
            Numbering.SEVEN,
            Numbering.EIGHT);
    private static final PiecePositionGenerator INSTANCE = new PiecePositionGenerator();

    private final InitialPieceGenerator initialPieceGenerator = InitialPieceGenerator.getInstance();
    private final List<Position> startingPosition = fetchStartingPosition();

    private PiecePositionGenerator() {
    }

    public static PiecePositionGenerator getInstance() {
        return INSTANCE;
    }

    public Map<Position, Piece> generatePiecePosition() {
        Map<Position, Piece> piecePosition = new HashMap<>();

        for (Position position : startingPosition) {
            Piece piece = initialPieceGenerator.generate(position);
            piecePosition.put(position, piece);
        }

        return piecePosition;
    }

    public Map<Position, Piece> generatePiecePosition(Map<PositionEntity, PieceEntity> piecePositionEntry) {
        Map<Position, Piece> piecePosition = new HashMap<>();
        for (PositionEntity positionEntity : piecePositionEntry.keySet()) {
            Position position = BoardPosition.findPosition(
                    Lettering.valueOf(positionEntity.getLettering()),
                    Numbering.valueOf(positionEntity.getNumbering())
            );

            PieceEntity pieceEntity = piecePositionEntry.get(positionEntity);
            Piece piece = new Piece(
                    PieceType.valueOf(pieceEntity.getType()),
                    Camp.valueOf(pieceEntity.getCamp())
            );

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
