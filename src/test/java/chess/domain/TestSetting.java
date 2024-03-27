package chess.domain;

import chess.domain.piece.Camp;
import chess.domain.piece.PieceType;
import chess.domain.piece.Piece;
import chess.domain.position.Lettering;
import chess.domain.position.Numbering;
import chess.domain.position.Position;

public class TestSetting {

    public static final Piece KING_WHITE = new Piece(PieceType.KING, Camp.WHITE);
    public static final Piece KING_BLACK = new Piece(PieceType.KING, Camp.BLACK);

    public static final Piece QUEEN_WHITE = new Piece(PieceType.QUEEN, Camp.WHITE);
    public static final Piece QUEEN_BLACK = new Piece(PieceType.QUEEN, Camp.BLACK);

    public static final Piece BISHOP_WHITE = new Piece(PieceType.BISHOP, Camp.WHITE);
    public static final Piece BISHOP_BLACK = new Piece(PieceType.BISHOP, Camp.BLACK);

    public static final Piece ROOK_WHITE = new Piece(PieceType.ROOK, Camp.WHITE);
    public static final Piece ROOK_BLACK = new Piece(PieceType.ROOK, Camp.BLACK);

    public static final Piece KNIGHT_WHITE = new Piece(PieceType.KNIGHT, Camp.WHITE);
    public static final Piece KNIGHT_BLACK = new Piece(PieceType.KNIGHT, Camp.BLACK);

    public static final Piece PAWN_WHITE = new Piece(PieceType.PAWN, Camp.WHITE);
    public static final Piece PAWN_BLACK = new Piece(PieceType.PAWN, Camp.BLACK);

    public static final Position A1 = new Position(Lettering.A, Numbering.ONE);
    public static final Position A2 = new Position(Lettering.A, Numbering.TWO);
    public static final Position A3 = new Position(Lettering.A, Numbering.THREE);
    public static final Position A4 = new Position(Lettering.A, Numbering.FOUR);
    public static final Position A5 = new Position(Lettering.A, Numbering.FIVE);
    public static final Position A6 = new Position(Lettering.A, Numbering.SIX);
    public static final Position A7 = new Position(Lettering.A, Numbering.SEVEN);
    public static final Position A8 = new Position(Lettering.A, Numbering.EIGHT);

    public static final Position B1 = new Position(Lettering.B, Numbering.ONE);
    public static final Position B2 = new Position(Lettering.B, Numbering.TWO);
    public static final Position B3 = new Position(Lettering.B, Numbering.THREE);
    public static final Position B4 = new Position(Lettering.B, Numbering.FOUR);
    public static final Position B5 = new Position(Lettering.B, Numbering.FIVE);
    public static final Position B6 = new Position(Lettering.B, Numbering.SIX);
    public static final Position B7 = new Position(Lettering.B, Numbering.SEVEN);
    public static final Position B8 = new Position(Lettering.B, Numbering.EIGHT);

    public static final Position C1 = new Position(Lettering.C, Numbering.ONE);
    public static final Position C2 = new Position(Lettering.C, Numbering.TWO);
    public static final Position C3 = new Position(Lettering.C, Numbering.THREE);
    public static final Position C4 = new Position(Lettering.C, Numbering.FOUR);
    public static final Position C5 = new Position(Lettering.C, Numbering.FIVE);
    public static final Position C6 = new Position(Lettering.C, Numbering.SIX);
    public static final Position C7 = new Position(Lettering.C, Numbering.SEVEN);
    public static final Position C8 = new Position(Lettering.C, Numbering.EIGHT);

    public static final Position D1 = new Position(Lettering.D, Numbering.ONE);
    public static final Position D2 = new Position(Lettering.D, Numbering.TWO);
    public static final Position D3 = new Position(Lettering.D, Numbering.THREE);
    public static final Position D4 = new Position(Lettering.D, Numbering.FOUR);
    public static final Position D5 = new Position(Lettering.D, Numbering.FIVE);
    public static final Position D6 = new Position(Lettering.D, Numbering.SIX);
    public static final Position D7 = new Position(Lettering.D, Numbering.SEVEN);
    public static final Position D8 = new Position(Lettering.D, Numbering.EIGHT);

    public static final Position E1 = new Position(Lettering.E, Numbering.ONE);
    public static final Position E2 = new Position(Lettering.E, Numbering.TWO);
    public static final Position E3 = new Position(Lettering.E, Numbering.THREE);
    public static final Position E4 = new Position(Lettering.E, Numbering.FOUR);
    public static final Position E5 = new Position(Lettering.E, Numbering.FIVE);
    public static final Position E6 = new Position(Lettering.E, Numbering.SIX);
    public static final Position E7 = new Position(Lettering.E, Numbering.SEVEN);
    public static final Position E8 = new Position(Lettering.E, Numbering.EIGHT);

    public static final Position F1 = new Position(Lettering.F, Numbering.ONE);
    public static final Position F2 = new Position(Lettering.F, Numbering.TWO);
    public static final Position F3 = new Position(Lettering.F, Numbering.THREE);
    public static final Position F4 = new Position(Lettering.F, Numbering.FOUR);
    public static final Position F5 = new Position(Lettering.F, Numbering.FIVE);
    public static final Position F6 = new Position(Lettering.F, Numbering.SIX);
    public static final Position F7 = new Position(Lettering.F, Numbering.SEVEN);
    public static final Position F8 = new Position(Lettering.F, Numbering.EIGHT);

    public static final Position G1 = new Position(Lettering.G, Numbering.ONE);
    public static final Position G2 = new Position(Lettering.G, Numbering.TWO);
    public static final Position G3 = new Position(Lettering.G, Numbering.THREE);
    public static final Position G4 = new Position(Lettering.G, Numbering.FOUR);
    public static final Position G5 = new Position(Lettering.G, Numbering.FIVE);
    public static final Position G6 = new Position(Lettering.G, Numbering.SIX);
    public static final Position G7 = new Position(Lettering.G, Numbering.SEVEN);
    public static final Position G8 = new Position(Lettering.G, Numbering.EIGHT);

    public static final Position H1 = new Position(Lettering.H, Numbering.ONE);
    public static final Position H2 = new Position(Lettering.H, Numbering.TWO);
    public static final Position H3 = new Position(Lettering.H, Numbering.THREE);
    public static final Position H4 = new Position(Lettering.H, Numbering.FOUR);
    public static final Position H5 = new Position(Lettering.H, Numbering.FIVE);
    public static final Position H6 = new Position(Lettering.H, Numbering.SIX);
    public static final Position H7 = new Position(Lettering.H, Numbering.SEVEN);
    public static final Position H8 = new Position(Lettering.H, Numbering.EIGHT);

    private TestSetting() {
    }
}
