package chess.domain.position;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LetteringTest {

    @Test
    void 다음_순서의_레터링을_조회_가능한지_확인() {
        //given, when
        boolean expectedTrue1 = Lettering.G.canFindNextLettering();
        boolean expectedTrue2 = Lettering.F.canFindNextLettering(2);
        boolean expectedFalse = Lettering.H.canFindNextLettering();

        //then
        assertAll(
                () -> assertThat(expectedTrue1).isTrue(),
                () -> assertThat(expectedTrue2).isTrue(),
                () -> assertThat(expectedFalse).isFalse()
        );
    }

    @Test
    void 이전_순서의_레터링을_조회_가능한지_확인() {
        //given, when
        boolean expectedTrue1 = Lettering.B.canFindPreviousLettering();
        boolean expectedTrue2 = Lettering.C.canFindPreviousLettering(2);
        boolean expectedFalse = Lettering.A.canFindPreviousLettering();

        //then
        assertAll(
                () -> assertThat(expectedTrue1).isTrue(),
                () -> assertThat(expectedTrue2).isTrue(),
                () -> assertThat(expectedFalse).isFalse()
        );
    }

    @Test
    void 다음_순서의_레터링을_반환() {
        //given, when
        Lettering nextLettering1 = Lettering.A.findNextLettering();
        Lettering nextLettering2 = Lettering.A.findNextLettering(2);

        //then
        assertAll(
                () -> assertThat(nextLettering1).isEqualTo(Lettering.B),
                () -> assertThat(nextLettering2).isEqualTo(Lettering.C)
        );
    }

    @Test
    void 다음_순서의_레터링을_반환할떄_마지막_레터링을_초과하는_경우_예외발생() {
        //given, when, then
        assertAll(
                () -> assertThatThrownBy(() -> Lettering.H.findNextLettering())
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> Lettering.G.findNextLettering(2))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 이전_순서의_레터링을_반환() {
        //given, when
        Lettering previousLettering1 = Lettering.B.findPreviousLettering();
        Lettering previousLettering2 = Lettering.C.findPreviousLettering(2);

        //then
        assertAll(
                () -> assertThat(previousLettering1).isEqualTo(Lettering.A),
                () -> assertThat(previousLettering2).isEqualTo(Lettering.A)
        );
    }

    @Test
    void 이전_순서의_레터링을_반환할떄_첫번째_레터링보다_작을_경우_예외발생() {
        //given, when, then
        assertAll(
                () -> assertThatThrownBy(() -> Lettering.A.findPreviousLettering())
                        .isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> Lettering.B.findPreviousLettering(2))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }
}
