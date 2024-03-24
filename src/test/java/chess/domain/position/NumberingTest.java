package chess.domain.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class NumberingTest {

    @Test
    void 첫번째_넘버링일_경우_True() {
        //given, when
        boolean isFirstNumbering = Numbering.isFirstNumbering(Numbering.ONE);

        //then
        assertThat(isFirstNumbering).isTrue();
    }

    @Test
    void 첫번째_넘버링이_아닌_경우_False() {
        //given, when
        boolean isFirstNumbering = Numbering.isFirstNumbering(Numbering.TWO);

        //then
        assertThat(isFirstNumbering).isFalse();
    }

    @Test
    void 마지막_넘버링일_경우_True() {
        //given, when
        boolean isLastNumbering = Numbering.isLastNumbering(Numbering.EIGHT);

        //then
        assertThat(isLastNumbering).isTrue();
    }

    @Test
    void 마지막_넘버링이_아닌_경우_False() {
        //given, when
        boolean isLastNumbering = Numbering.isLastNumbering(Numbering.SEVEN);

        //then
        assertThat(isLastNumbering).isFalse();
    }

    @Test
    void 다음_순서의_넘버링을_반환() {
        //given, when
        Numbering nextNumbering = Numbering.findNextNumbering(Numbering.ONE);

        //then
        assertThat(nextNumbering).isEqualTo(Numbering.TWO);
    }

    @Test
    void 다음_순서의_넘버링을_반환할떄_마지막_넘버링일경우_예외발생() {
        //given, when, then
        assertThatThrownBy(() -> Numbering.findNextNumbering(Numbering.EIGHT))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이전_순서의_넘버링을_반환() {
        //given, when
        Numbering previousNumbering = Numbering.findPreviousNumbering(Numbering.TWO);

        //then
        assertThat(previousNumbering).isEqualTo(Numbering.ONE);
    }

    @Test
    void 이전_순서의_넘버링을_반환할떄_첫번째_넘버링일경우_예외발생() {
        //given, when, then
        assertThatThrownBy(() -> Numbering.findPreviousNumbering(Numbering.ONE))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
