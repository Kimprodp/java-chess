package chess.domain.piece;

import chess.domain.position.Position;

public class PieceGenerator {

    private static final PieceGenerator INSTANCE = new PieceGenerator();

    private final PieceCampDeterminer pieceCampDeterminer = PieceCampDeterminer.getInstance();
    private final PieceTypeDeterminer pieceTypeDeterminer = PieceTypeDeterminer.getInstance();

    private PieceGenerator() {
    }

    public static PieceGenerator getInstance() {
        return INSTANCE;
    }

    public Piece generate(Position position) {
        PieceType pieceType = pieceTypeDeterminer.determine(position);
        Camp camp = pieceCampDeterminer.determineCamp(position.getNumbering());
        return new Piece(pieceType, camp);
    }
}
