package chess.domain.piece;

import chess.service.ChessDataInitializer;
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
        ChessDataInitializer chessDataInitializer = ChessDataInitializer.getInstance();

        PieceType pieceType = pieceTypeDeterminer.determine(position);
        Camp camp = pieceCampDeterminer.determineCamp(position.getNumbering());
        return chessDataInitializer.findPiece(pieceType, camp);
    }
}
