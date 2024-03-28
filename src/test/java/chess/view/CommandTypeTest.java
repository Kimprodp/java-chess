package chess.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CommandTypeTest {

    @Test
    void 입력값과_일치하는_명령어를_반환() {
        //given
        String start = "start";
        String end = "end";
        String move = "move";
        String status = "status";

        //when
        CommandType formStart = CommandType.from(start);
        CommandType formEnd = CommandType.from(end);
        CommandType formMove = CommandType.from(move);
        CommandType formStatus = CommandType.from(status);

        //then
        assertAll(
                () -> assertThat(formStart).isEqualTo(CommandType.START),
                () -> assertThat(formEnd).isEqualTo(CommandType.END),
                () -> assertThat(formMove).isEqualTo(CommandType.MOVE),
                () -> assertThat(formStatus).isEqualTo(CommandType.STATUS)
        );
    }

    @Test
    void 입력값과_일치하는_명령어가_없을_경우_예외발생() {
        //given
        String input = "tset";

        //when, then
        assertThatThrownBy(() -> CommandType.from(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
