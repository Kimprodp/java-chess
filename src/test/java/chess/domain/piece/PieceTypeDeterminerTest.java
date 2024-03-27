package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.position.Lettering;
import chess.domain.position.Numbering;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PieceTypeDeterminerTest {

    PieceTypeDeterminer pieceTypeDeterminer = PieceTypeDeterminer.getInstance();

    @Test
    void 레터링이_E이고_넘버링이_1_또는_8인경우_타입을_킹으로_결정() {
        //given
        Position position1 = new Position(Lettering.E, Numbering.ONE);
        Position position2 = new Position(Lettering.E, Numbering.EIGHT);

        //when
        PieceType pieceType1 = pieceTypeDeterminer.determine(position1);
        PieceType pieceType2 = pieceTypeDeterminer.determine(position2);

        //then
        assertAll(
                () -> assertThat(pieceType1).isEqualTo(PieceType.KING),
                () -> assertThat(pieceType2).isEqualTo(PieceType.KING)
        );
    }

    @Test
    void 레터링이_D이고_넘버링이_1_또는_8인경우_타입을_퀸으로_결정() {
        //given
        Position position1 = new Position(Lettering.D, Numbering.ONE);
        Position position2 = new Position(Lettering.D, Numbering.EIGHT);

        //when
        PieceType pieceType1 = pieceTypeDeterminer.determine(position1);
        PieceType pieceType2 = pieceTypeDeterminer.determine(position2);

        //then
        assertAll(
                () -> assertThat(pieceType1).isEqualTo(PieceType.QUEEN),
                () -> assertThat(pieceType2).isEqualTo(PieceType.QUEEN)
        );
    }

    @Test
    void 레터링이_C_또는_F이고_넘버링이_1_또는_8인경우_타입을_비숍으로_결정() {
        //given
        Position position1 = new Position(Lettering.C, Numbering.ONE);
        Position position2 = new Position(Lettering.C, Numbering.EIGHT);
        Position position3 = new Position(Lettering.F, Numbering.ONE);
        Position position4 = new Position(Lettering.F, Numbering.EIGHT);

        //when
        PieceType pieceType1 = pieceTypeDeterminer.determine(position1);
        PieceType pieceType2 = pieceTypeDeterminer.determine(position2);
        PieceType pieceType3 = pieceTypeDeterminer.determine(position3);
        PieceType pieceType4 = pieceTypeDeterminer.determine(position4);

        //then
        assertAll(
                () -> assertThat(pieceType1).isEqualTo(PieceType.BISHOP),
                () -> assertThat(pieceType2).isEqualTo(PieceType.BISHOP),
                () -> assertThat(pieceType3).isEqualTo(PieceType.BISHOP),
                () -> assertThat(pieceType4).isEqualTo(PieceType.BISHOP)
        );
    }

    @Test
    void 레터링이_B_또는_G이고_넘버링이_1_또는_8인경우_타입을_나이트로_결정() {
        //given
        Position position1 = new Position(Lettering.B, Numbering.ONE);
        Position position2 = new Position(Lettering.B, Numbering.EIGHT);
        Position position3 = new Position(Lettering.G, Numbering.ONE);
        Position position4 = new Position(Lettering.G, Numbering.EIGHT);

        //when
        PieceType pieceType1 = pieceTypeDeterminer.determine(position1);
        PieceType pieceType2 = pieceTypeDeterminer.determine(position2);
        PieceType pieceType3 = pieceTypeDeterminer.determine(position3);
        PieceType pieceType4 = pieceTypeDeterminer.determine(position4);

        //then
        assertAll(
                () -> assertThat(pieceType1).isEqualTo(PieceType.KNIGHT),
                () -> assertThat(pieceType2).isEqualTo(PieceType.KNIGHT),
                () -> assertThat(pieceType3).isEqualTo(PieceType.KNIGHT),
                () -> assertThat(pieceType4).isEqualTo(PieceType.KNIGHT)
        );
    }

    @Test
    void 레터링이_A_또는_H이고_넘버링이_1_또는_8인경우_타입을_룩으로_결정() {
        //given
        Position position1 = new Position(Lettering.A, Numbering.ONE);
        Position position2 = new Position(Lettering.A, Numbering.EIGHT);
        Position position3 = new Position(Lettering.H, Numbering.ONE);
        Position position4 = new Position(Lettering.H, Numbering.EIGHT);

        //when
        PieceType pieceType1 = pieceTypeDeterminer.determine(position1);
        PieceType pieceType2 = pieceTypeDeterminer.determine(position2);
        PieceType pieceType3 = pieceTypeDeterminer.determine(position3);
        PieceType pieceType4 = pieceTypeDeterminer.determine(position4);

        //then
        assertAll(
                () -> assertThat(pieceType1).isEqualTo(PieceType.ROOK),
                () -> assertThat(pieceType2).isEqualTo(PieceType.ROOK),
                () -> assertThat(pieceType3).isEqualTo(PieceType.ROOK),
                () -> assertThat(pieceType4).isEqualTo(PieceType.ROOK)
        );
    }

    @Test
    void 레터링에_상관없이_넘버링이_2_또는_7인_경우_타입을_폰으로_결정() {
        //given
        Position position1 = new Position(Lettering.A, Numbering.TWO);
        Position position2 = new Position(Lettering.H, Numbering.SEVEN);

        //when
        PieceType pieceType1 = pieceTypeDeterminer.determine(position1);
        PieceType pieceType2 = pieceTypeDeterminer.determine(position2);

        //then
        assertAll(
                () -> assertThat(pieceType1).isEqualTo(PieceType.PAWN),
                () -> assertThat(pieceType2).isEqualTo(PieceType.PAWN)
        );
    }

    @Test
    void 타입을_결정할_수_없는_경우_예외발생() {
        //given
        Position position1 = new Position(Lettering.A, Numbering.THREE);
        Position position2 = new Position(Lettering.A, Numbering.FOUR);
        Position position3 = new Position(Lettering.A, Numbering.FIVE);
        Position position4 = new Position(Lettering.A, Numbering.SIX);

        //when, then
        assertAll(
                () -> assertThatThrownBy(() -> pieceTypeDeterminer.determine(position1))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> pieceTypeDeterminer.determine(position2))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> pieceTypeDeterminer.determine(position3))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> pieceTypeDeterminer.determine(position4))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }
}
