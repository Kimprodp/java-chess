package chess.domain.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class NumberingTest {

    @Test
    void 다음_순서의_넘버링을_조회_가능한지_확인() {
        //given, when
        boolean expectedTrue1 = Numbering.canFindNextNumbering(Numbering.SEVEN);
        boolean expectedTrue2 = Numbering.canFindNextNumbering(Numbering.SIX, 2);
        boolean expectedFalse = Numbering.canFindNextNumbering(Numbering.EIGHT);

        //then
        assertAll(
                () -> assertThat(expectedTrue1).isTrue(),
                () -> assertThat(expectedTrue2).isTrue(),
                () -> assertThat(expectedFalse).isFalse()
        );
    }

    @Test
    void 이전_순서의_넘버링을_조회_가능한지_확인() {
        //given, when
        boolean expectedTrue1 = Numbering.canFindPreviousNumbering(Numbering.TWO);
        boolean expectedTrue2 = Numbering.canFindPreviousNumbering(Numbering.THREE, 2);
        boolean expectedFalse = Numbering.canFindPreviousNumbering(Numbering.ONE);

        //then
        assertAll(
                () -> assertThat(expectedTrue1).isTrue(),
                () -> assertThat(expectedTrue2).isTrue(),
                () -> assertThat(expectedFalse).isFalse()
        );
    }

    @Test
    void 다음_순서의_넘버링을_반환() {
        //given, when
        Numbering nextNumbering1 = Numbering.findNextNumbering(Numbering.ONE);
        Numbering nextNumbering2 = Numbering.findNextNumbering(Numbering.ONE, 2);

        //then
        assertAll(
                () -> assertThat(nextNumbering1).isEqualTo(Numbering.TWO),
                () -> assertThat(nextNumbering2).isEqualTo(Numbering.THREE)
        );
    }

    @Test
    void 다음_순서의_넘버링을_반환할떄_마지막_넘버링을_초과하는_경우_예외발생() {
        //given, when, then
        assertAll(
                () -> assertThatThrownBy(() -> Numbering.findNextNumbering(Numbering.EIGHT))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> Numbering.findNextNumbering(Numbering.SEVEN, 2))
                        .isInstanceOf(IllegalArgumentException.class)

        );
    }

    @Test
    void 이전_순서의_넘버링을_반환() {
        //given, when
        Numbering previousNumbering1 = Numbering.findPreviousNumbering(Numbering.TWO);
        Numbering previousNumbering2 = Numbering.findPreviousNumbering(Numbering.THREE, 2);

        //then
        assertAll(
                () -> assertThat(previousNumbering1).isEqualTo(Numbering.ONE),
                () -> assertThat(previousNumbering2).isEqualTo(Numbering.ONE)
        );
    }

    @Test
    void 이전_순서의_넘버링을_반환할떄_첫번째_넘버링보다_작을_경우_예외발생() {
        //given, when, then
        assertAll(
                () -> assertThatThrownBy(() -> Numbering.findPreviousNumbering(Numbering.ONE))
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> Numbering.findPreviousNumbering(Numbering.TWO, 2))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }
}
