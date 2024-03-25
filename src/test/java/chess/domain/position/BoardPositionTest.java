package chess.domain.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BoardPositionTest {

    @Test
    void 위쪽의_위치를_조회_가능한지_확인() {
        //given
        Position nonTopPosition = new Position(Lettering.A, Numbering.SIX);
        Position topPosition = new Position(Lettering.A, Numbering.EIGHT);

        //when
        boolean expectedTrue1 = BoardPosition.canFindUpPosition(nonTopPosition);
        boolean expectedTrue2 = BoardPosition.canFindUpPosition(nonTopPosition, 2);
        boolean expectedFalse = BoardPosition.canFindUpPosition(topPosition);

        //then
        assertAll(
                () -> assertThat(expectedTrue1).isTrue(),
                () -> assertThat(expectedTrue2).isTrue(),
                () -> assertThat(expectedFalse).isFalse()
        );
    }

    @Test
    void 아래쪽의_위치를_조회_가능한지_확인() {
        //given
        Position nonBottomPosition = new Position(Lettering.A, Numbering.THREE);
        Position bottomtopPosition = new Position(Lettering.A, Numbering.ONE);

        //when
        boolean expectedTrue1 = BoardPosition.canFindDownPosition(nonBottomPosition);
        boolean expectedTrue2 = BoardPosition.canFindDownPosition(nonBottomPosition, 2);
        boolean expectedFalse = BoardPosition.canFindDownPosition(bottomtopPosition);

        //then
        assertAll(
                () -> assertThat(expectedTrue1).isTrue(),
                () -> assertThat(expectedTrue2).isTrue(),
                () -> assertThat(expectedFalse).isFalse()
        );
    }

    @Test
    void 왼쪽의_위치를_조회_가능한지_확인() {
        //given
        Position nonLeftMostPosition = new Position(Lettering.C, Numbering.ONE);
        Position leftMostPosition = new Position(Lettering.A, Numbering.ONE);

        //when
        boolean expectedTrue1 = BoardPosition.canFindLeftPosition(nonLeftMostPosition);
        boolean expectedTrue2 = BoardPosition.canFindLeftPosition(nonLeftMostPosition, 2);
        boolean expectedFalse = BoardPosition.canFindLeftPosition(leftMostPosition);

        //then
        assertAll(
                () -> assertThat(expectedTrue1).isTrue(),
                () -> assertThat(expectedTrue2).isTrue(),
                () -> assertThat(expectedFalse).isFalse()
        );
    }

    @Test
    void 오른쪽의_위치를_조회_가능한지_확인() {
        //given
        Position nonRightMostPosition = new Position(Lettering.F, Numbering.ONE);
        Position rightMostPosition = new Position(Lettering.H, Numbering.ONE);

        //when
        boolean expectedTrue1 = BoardPosition.canFindRightPosition(nonRightMostPosition);
        boolean expectedTrue2 = BoardPosition.canFindRightPosition(nonRightMostPosition, 2);
        boolean expectedFalse = BoardPosition.canFindRightPosition(rightMostPosition);

        //then
        assertAll(
                () -> assertThat(expectedTrue1).isTrue(),
                () -> assertThat(expectedTrue2).isTrue(),
                () -> assertThat(expectedFalse).isFalse()
        );
    }

    @Test
    void 레터링과_넘버링에_해당하는_위치를_조회() {
        //given, when
        Position foundPosition = BoardPosition.findPosition(Lettering.A, Numbering.ONE);

        //then
        Lettering lettering = foundPosition.getLettering();
        Numbering numbering = foundPosition.getNumbering();

        assertAll(
                () -> assertThat(lettering).isEqualTo(Lettering.A),
                () -> assertThat(numbering).isEqualTo(Numbering.ONE)
        );
    }

    @Test
    void 전달된_위치의_위쪽_위치를_조회() {
        //given
        Position position = BoardPosition.findPosition(Lettering.A, Numbering.ONE);

        //when
        Position upPosition1 = BoardPosition.findUpPosition(position);
        Position upPosition2 = BoardPosition.findUpPosition(position, 2);

        //then
        Lettering lettering1 = upPosition1.getLettering();
        Numbering numbering1 = upPosition1.getNumbering();
        Lettering lettering2 = upPosition2.getLettering();
        Numbering numbering2 = upPosition2.getNumbering();

        assertAll(
                () -> assertThat(lettering1).isEqualTo(Lettering.A),
                () -> assertThat(numbering1).isEqualTo(Numbering.TWO),
                () -> assertThat(lettering2).isEqualTo(Lettering.A),
                () -> assertThat(numbering2).isEqualTo(Numbering.THREE)
        );
    }

    @Test
    void 전달된_위치의_아래쪽_위치를_조회() {
        //given
        Position position = BoardPosition.findPosition(Lettering.A, Numbering.THREE);

        //when
        Position downPosition1 = BoardPosition.findDownPosition(position);
        Position downPosition2 = BoardPosition.findDownPosition(position, 2);

        //then
        Lettering lettering1 = downPosition1.getLettering();
        Numbering numbering1 = downPosition1.getNumbering();
        Lettering lettering2 = downPosition2.getLettering();
        Numbering numbering2 = downPosition2.getNumbering();

        assertAll(
                () -> assertThat(lettering1).isEqualTo(Lettering.A),
                () -> assertThat(numbering1).isEqualTo(Numbering.TWO),
                () -> assertThat(lettering2).isEqualTo(Lettering.A),
                () -> assertThat(numbering2).isEqualTo(Numbering.ONE)
        );
    }

    @Test
    void 전달된_위치의_왼쪽_위치를_조회() {
        //given
        Position position = BoardPosition.findPosition(Lettering.C, Numbering.ONE);

        //when
        Position leftPosition1 = BoardPosition.findLeftPosition(position);
        Position leftPosition2 = BoardPosition.findLeftPosition(position, 2);

        //then
        Lettering lettering1 = leftPosition1.getLettering();
        Numbering numbering1 = leftPosition1.getNumbering();
        Lettering lettering2 = leftPosition2.getLettering();
        Numbering numbering2 = leftPosition2.getNumbering();

        assertAll(
                () -> assertThat(lettering1).isEqualTo(Lettering.B),
                () -> assertThat(numbering1).isEqualTo(Numbering.ONE),
                () -> assertThat(lettering2).isEqualTo(Lettering.A),
                () -> assertThat(numbering2).isEqualTo(Numbering.ONE)
        );
    }

    @Test
    void 전달된_위치의_오른쪽_위치를_조회() {
        //given
        Position position = BoardPosition.findPosition(Lettering.A, Numbering.ONE);

        //when
        Position rightPosition1 = BoardPosition.findRightPosition(position);
        Position rightPosition2 = BoardPosition.findRightPosition(position, 2);

        //then
        Lettering lettering1 = rightPosition1.getLettering();
        Numbering numbering1 = rightPosition1.getNumbering();
        Lettering lettering2 = rightPosition2.getLettering();
        Numbering numbering2 = rightPosition2.getNumbering();

        assertAll(
                () -> assertThat(lettering1).isEqualTo(Lettering.B),
                () -> assertThat(numbering1).isEqualTo(Numbering.ONE),
                () -> assertThat(lettering2).isEqualTo(Lettering.C),
                () -> assertThat(numbering2).isEqualTo(Numbering.ONE)
        );
    }
}
