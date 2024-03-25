package chess.domain.piece;

import chess.domain.position.Lettering;
import chess.domain.position.Numbering;
import chess.domain.position.Position;
import java.util.List;

public class PieceTypeDeterminer {

    private static final PieceTypeDeterminer INSTANCE = new PieceTypeDeterminer();
    private static final List<Numbering> PAWN_LINE_NUMBERING = List.of(Numbering.TWO, Numbering.SEVEN);
    private static final List<Numbering> OTHER_PIECE_LINE_NUMBERING = List.of(Numbering.ONE, Numbering.EIGHT);


    private ChessPieceType determinedType;

    private PieceTypeDeterminer() {
    }

    public static PieceTypeDeterminer getInstance() {
        return INSTANCE;
    }

    public ChessPieceType determine(Position position) {
        determineType(position);
        return determinedType;
    }

    private void determineType(Position chessPiecePosition) {
        validatePlaceablePosition(chessPiecePosition);
        Numbering numbering = chessPiecePosition.getNumbering();
        Lettering lettering = chessPiecePosition.getLettering();
        updateKing(numbering, lettering);
        updateQueen(numbering, lettering);
        updateBishop(numbering, lettering);
        updateKnight(numbering, lettering);
        updateRook(numbering, lettering);
        updatePawn(numbering, lettering);
    }

    private void updateKing(Numbering numbering, Lettering lettering) {
        if (OTHER_PIECE_LINE_NUMBERING.contains(numbering) && lettering == Lettering.E) {
            determinedType = ChessPieceType.KING;
        }
    }

    private void updateQueen(Numbering numbering, Lettering lettering) {
        if (OTHER_PIECE_LINE_NUMBERING.contains(numbering) && lettering == Lettering.D) {
            determinedType = ChessPieceType.QUEEN;
        }
    }

    private void updateBishop(Numbering numbering, Lettering lettering) {
        List<Lettering> bishopLettering = List.of(Lettering.C, Lettering.F);
        if (OTHER_PIECE_LINE_NUMBERING.contains(numbering) && bishopLettering.contains(lettering)) {
            determinedType = ChessPieceType.BISHOP;
        }
    }

    private void updateKnight(Numbering numbering, Lettering lettering) {
        List<Lettering> knightLettering = List.of(Lettering.B, Lettering.G);
        if (OTHER_PIECE_LINE_NUMBERING.contains(numbering) && knightLettering.contains(lettering)) {
            determinedType = ChessPieceType.KNIGHT;
        }
    }

    private void updateRook(Numbering numbering, Lettering lettering) {
        List<Lettering> rookLettering = List.of(Lettering.A, Lettering.H);
        if (OTHER_PIECE_LINE_NUMBERING.contains(numbering) && rookLettering.contains(lettering)) {
            determinedType = ChessPieceType.ROOK;
        }
    }

    private void updatePawn(Numbering numbering, Lettering lettering) {
        List<Lettering> pawnLettering = List.of(Lettering.values());
        if (PAWN_LINE_NUMBERING.contains(numbering) && pawnLettering.contains(lettering)) {
            determinedType = ChessPieceType.PAWN;
        }
    }

    private void validatePlaceablePosition(Position chessPiecePosition) {
        Numbering numbering = chessPiecePosition.getNumbering();
        if (!PAWN_LINE_NUMBERING.contains(numbering) && !OTHER_PIECE_LINE_NUMBERING.contains(numbering)) {
            throw new IllegalArgumentException("[ERROR] 체스 말이 놓일 수 있는 위치가 아닙니다. : " + chessPiecePosition);
        }
    }
}
