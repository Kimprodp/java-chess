package chess.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.position.Lettering;
import chess.domain.position.Numbering;
import chess.dto.MoveCommandDto;
import chess.dto.PositionDto;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CommandTest {

    @Test
    void 명령어가_move가_아니면_움직임_정보가_없음() {
        //given
        String input = "start";

        //when
        Command command = Command.create(List.of(input));

        //then
        CommandType commandType = command.getCommandType();
        boolean isEmpty = command.getMoveCommandDto().isEmpty();

        assertAll(
                () -> assertThat(commandType).isEqualTo(CommandType.START),
                () -> assertThat(isEmpty).isTrue()
        );
    }

    @Test
    void 명령어가_move일_경우_움직임_정보가_있음() {
        //given
        String input = "move b2 b3";

        //when
        Command command = Command.create(List.of(input.split(" ")));

        //then
        CommandType commandType = command.getCommandType();
        MoveCommandDto moveCommandDto = command.getMoveCommandDto().orElse(null);
        PositionDto moveSource = moveCommandDto.moveSource();
        PositionDto target = moveCommandDto.target();

        assertAll(
                () -> assertThat(moveCommandDto).isNotNull(),
                () -> assertThat(moveSource.lettering()).isEqualTo(Lettering.B),
                () -> assertThat(moveSource.numbering()).isEqualTo(Numbering.TWO),
                () -> assertThat(target.lettering()).isEqualTo(Lettering.B),
                () -> assertThat(target.numbering()).isEqualTo(Numbering.THREE)
        );
    }

    @Test
    void 입력값이_올바르지_않을_경우_예외발생() {
        //given
        List<String> input1 = List.of("".split(" "));
        List<String> input2 = List.of("move b2 b3 b4".split(" "));
        List<String> input3 = List.of("move p2 b3".split(" "));
        List<String> input4 = List.of("start b2 b3".split(" "));
        List<String> input5 = List.of("move".split(" "));

        //when, then
        assertAll(
                () -> assertThatThrownBy(() -> Command.create(input1)).isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> Command.create(input2)).isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> Command.create(input3)).isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> Command.create(input4)).isInstanceOf(IllegalArgumentException.class),
                () -> assertThatThrownBy(() -> Command.create(input5)).isInstanceOf(IllegalArgumentException.class)
        );
    }
}
