package chess.view;

import chess.domain.piece.Camp;
import chess.domain.piece.PieceType;
import chess.dto.PieceDto;
import java.util.Arrays;

public enum ChessPiecePrintFormat {

    KING(PieceType.KING, "K"),
    QUEEN(PieceType.QUEEN, "Q"),
    BISHOP(PieceType.BISHOP, "B"),
    KNIGHT(PieceType.KNIGHT, "N"),
    ROOK(PieceType.ROOK, "R"),
    PAWN(PieceType.PAWN, "P");

    private final PieceType type;
    private final String notation;

    ChessPiecePrintFormat(PieceType type, String notation) {
        this.notation = notation;
        this.type = type;
    }

    public static String findChessPieceNotation(PieceDto pieceDto) {
        PieceType pieceType = pieceDto.pieceType();
        Camp camp = pieceDto.camp();

        return Arrays.stream(ChessPiecePrintFormat.values())
                .filter(chessPieceFormat -> chessPieceFormat.type == pieceType)
                .map(chessPieceFormat -> determineNotation(chessPieceFormat, camp))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 일치하는 체스말이 없습니다."));
    }

    private static String determineNotation(ChessPiecePrintFormat chessPieceFormat, Camp camp) {
        if (camp == Camp.BLACK) {
            return chessPieceFormat.notation;
        }
        return chessPieceFormat.notation.toLowerCase();
    }
}
