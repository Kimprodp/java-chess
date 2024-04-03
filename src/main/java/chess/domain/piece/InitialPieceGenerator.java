package chess.domain.piece;

import chess.domain.position.Position;

public class InitialPieceGenerator {

    private static final InitialPieceGenerator INSTANCE = new InitialPieceGenerator();

    private final PieceCampDeterminer pieceCampDeterminer = PieceCampDeterminer.getInstance();
    private final PieceTypeDeterminer pieceTypeDeterminer = PieceTypeDeterminer.getInstance();

    private InitialPieceGenerator() {
    }

    public static InitialPieceGenerator getInstance() {
        return INSTANCE;
    }

    public Piece generate(Position position) {
        PieceType pieceType = pieceTypeDeterminer.determine(position);
        Camp camp = pieceCampDeterminer.determineCamp(position.getNumbering());
        return new Piece(pieceType, camp);
    }
}
