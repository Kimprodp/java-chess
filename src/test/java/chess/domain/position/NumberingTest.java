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
        boolean expectedTrue1 = Numbering.SEVEN.canFindNextNumbering();
        boolean expectedTrue2 = Numbering.SIX.canFindNextNumbering(2);
        boolean expectedFalse = Numbering.EIGHT.canFindNextNumbering();

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
        boolean expectedTrue1 = Numbering.TWO.canFindPreviousNumbering();
        boolean expectedTrue2 = Numbering.THREE.canFindPreviousNumbering(2);
        boolean expectedFalse = Numbering.ONE.canFindPreviousNumbering();

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
        Numbering nextNumbering1 = Numbering.ONE.findNextNumbering();
        Numbering nextNumbering2 = Numbering.ONE.findNextNumbering(2);

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
                () -> assertThatThrownBy(() -> Numbering.EIGHT.findNextNumbering())
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> Numbering.SEVEN.findNextNumbering(2))
                        .isInstanceOf(IllegalArgumentException.class)

        );
    }

    @Test
    void 이전_순서의_넘버링을_반환() {
        //given, when
        Numbering previousNumbering1 = Numbering.TWO.findPreviousNumbering();
        Numbering previousNumbering2 = Numbering.THREE.findPreviousNumbering(2);

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
                () -> assertThatThrownBy(() -> Numbering.ONE.findPreviousNumbering())
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> Numbering.TWO.findPreviousNumbering(2))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }
}
