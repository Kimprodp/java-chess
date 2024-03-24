package chess.domain.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BoardPositionTest {

    @Test
    void 가장_위쪽의_위치인지_확인() {
        //given
        Position topPosition = new Position(Lettering.A, Numbering.EIGHT);
        Position nonTopPosition = new Position(Lettering.A, Numbering.SEVEN);

        //when
        boolean expectedTrue = BoardPosition.isTopPosition(topPosition);
        boolean expectedFalse = BoardPosition.isTopPosition(nonTopPosition);

        //then
        assertAll(
                () -> assertThat(expectedTrue).isTrue(),
                () -> assertThat(expectedFalse).isFalse()
        );
    }

    @Test
    void 가장_아래쪽의_위치인지_확인() {
        //given
        Position bottomtopPosition = new Position(Lettering.A, Numbering.ONE);
        Position nonBottomPosition = new Position(Lettering.A, Numbering.TWO);

        //when
        boolean expectedTrue = BoardPosition.isBottomPosition(bottomtopPosition);
        boolean expectedFalse = BoardPosition.isBottomPosition(nonBottomPosition);

        //then
        assertAll(
                () -> assertThat(expectedTrue).isTrue(),
                () -> assertThat(expectedFalse).isFalse()
        );
    }

    @Test
    void 가장_왼쪽의_위치인지_확인() {
        //given
        Position leftMostPosition = new Position(Lettering.A, Numbering.ONE);
        Position nonLeftMostPosition = new Position(Lettering.B, Numbering.ONE);

        //when
        boolean expectedTrue = BoardPosition.isLeftMostPosition(leftMostPosition);
        boolean expectedFalse = BoardPosition.isLeftMostPosition(nonLeftMostPosition);

        //then
        assertAll(
                () -> assertThat(expectedTrue).isTrue(),
                () -> assertThat(expectedFalse).isFalse()
        );
    }

    @Test
    void 가장_오른쪽의_위치인지_확인() {
        //given
        Position rightMostPosition = new Position(Lettering.H, Numbering.ONE);
        Position nonRightMostPosition = new Position(Lettering.G, Numbering.ONE);

        //when
        boolean expectedTrue = BoardPosition.isRightMostPosition(rightMostPosition);
        boolean expectedFalse = BoardPosition.isRightMostPosition(nonRightMostPosition);

        //then
        assertAll(
                () -> assertThat(expectedTrue).isTrue(),
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
        Position upPosition = BoardPosition.findUpPosition(position);

        //then
        Lettering lettering = upPosition.getLettering();
        Numbering numbering = upPosition.getNumbering();

        assertAll(
                () -> assertThat(lettering).isEqualTo(Lettering.A),
                () -> assertThat(numbering).isEqualTo(Numbering.TWO)
        );
    }

    @Test
    void 전달된_위치의_아래쪽_위치를_조회() {
        //given
        Position position = BoardPosition.findPosition(Lettering.A, Numbering.TWO);

        //when
        Position downPosition = BoardPosition.findDownPosition(position);

        //then
        Lettering lettering = downPosition.getLettering();
        Numbering numbering = downPosition.getNumbering();

        assertAll(
                () -> assertThat(lettering).isEqualTo(Lettering.A),
                () -> assertThat(numbering).isEqualTo(Numbering.ONE)
        );
    }

    @Test
    void 전달된_위치의_왼쪽_위치를_조회() {
        //given
        Position position = BoardPosition.findPosition(Lettering.B, Numbering.ONE);

        //when
        Position leftPosition = BoardPosition.findLeftPosition(position);

        //then
        Lettering lettering = leftPosition.getLettering();
        Numbering numbering = leftPosition.getNumbering();

        assertAll(
                () -> assertThat(lettering).isEqualTo(Lettering.A),
                () -> assertThat(numbering).isEqualTo(Numbering.ONE)
        );
    }

    @Test
    void 전달된_위치의_오른쪽_위치를_조회() {
        //given
        Position position = BoardPosition.findPosition(Lettering.A, Numbering.ONE);

        //when
        Position rightPosition = BoardPosition.findRightPosition(position);

        //then
        Lettering lettering = rightPosition.getLettering();
        Numbering numbering = rightPosition.getNumbering();

        assertAll(
                () -> assertThat(lettering).isEqualTo(Lettering.B),
                () -> assertThat(numbering).isEqualTo(Numbering.ONE)
        );
    }
}
