package chess.domain.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LetteringTest {

    @Test
    void 첫번째_레터링일_경우_True() {
        //given, when
        boolean isFirstLettering = Lettering.isFirstLettering(Lettering.A);

        //then
        assertThat(isFirstLettering).isTrue();
    }

    @Test
    void 첫번째_레터링이_아닌_경우_False() {
        //given, when
        boolean isFirstLettering = Lettering.isFirstLettering(Lettering.B);

        //then
        assertThat(isFirstLettering).isFalse();
    }

    @Test
    void 마지막_레터링일_경우_True() {
        //given, when
        boolean isLastLettering = Lettering.isLastLettering(Lettering.H);

        //then
        assertThat(isLastLettering).isTrue();
    }

    @Test
    void 마지막_레터링이_아닌_경우_False() {
        //given, when
        boolean isLastLettering = Lettering.isLastLettering(Lettering.G);

        //then
        assertThat(isLastLettering).isFalse();
    }

    @Test
    void 다음_순서의_레터링을_반환() {
        //given, when
        Lettering nextLettering = Lettering.findNextLettering(Lettering.A);

        //then
        assertThat(nextLettering).isEqualTo(Lettering.B);
    }

    @Test
    void 다음_순서의_레터링을_반환할떄_마지막_레터링일경우_예외발생() {
        //given, when, then
        assertThatThrownBy(() -> Lettering.findNextLettering(Lettering.H))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이전_순서의_레터링을_반환() {
        //given, when
        Lettering previousLettering = Lettering.findPreviousLettering(Lettering.B);

        //then
        assertThat(previousLettering).isEqualTo(Lettering.A);
    }

    @Test
    void 이전_순서의_레터링을_반환할떄_첫번째_레터링일경우_예외발생() {
        //given, when, then
        assertThatThrownBy(() -> Lettering.findPreviousLettering(Lettering.A))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
