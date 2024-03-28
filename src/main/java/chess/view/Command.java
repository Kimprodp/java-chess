package chess.view;

import chess.domain.position.Lettering;
import chess.domain.position.Numbering;
import chess.dto.MoveCommandDto;
import chess.dto.PositionDto;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Command {

    private final static int FIRST_VALUE = 0;
    private final static int SECOND_VALUE = 1;
    private final static int THIRD_VALUE = 2;
    private final static int NOT_MOVE_COMMAND_LENGTH = 1;
    private final static int MOVE_COMMAND_LENGTH = 3;

    private final CommandType commandType;
    private MoveCommandDto moveCommandDto;

    private Command(CommandType commandType) {
        this.commandType = commandType;
    }

    public static Command create(List<String> separatedInput) {
        validateSize(separatedInput);
        CommandType commandType = CommandType.from(separatedInput.get(FIRST_VALUE));
        Command command = new Command(commandType);
        command.registerMoveCommandDto(separatedInput);
        return command;
    }

    private void registerMoveCommandDto(List<String> separatedInput) {
        validateMove(separatedInput);
        PositionDto moveSource = convertPositionDto(separatedInput.get(SECOND_VALUE));
        PositionDto target = convertPositionDto(separatedInput.get(THIRD_VALUE));
        this.moveCommandDto = new MoveCommandDto(moveSource, target);

    }

    private PositionDto convertPositionDto(String input) {
        try {
            char letteringText = input.charAt(FIRST_VALUE);
            char numberingText = input.charAt(SECOND_VALUE);
            Lettering lettering = Lettering.valueOf(String.valueOf(letteringText).toUpperCase());
            Numbering numbering = findNumberingByOrdinal(numberingText);
            return new PositionDto(lettering, numbering);
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 입력값이 올바르지 않습니다.");
        }
    }

    private Numbering findNumberingByOrdinal(char numberingText) {
        int ordinalValue = Character.getNumericValue(numberingText) - 1;
        return Arrays.stream(Numbering.values())
                .filter(numbering -> numbering.ordinal() == ordinalValue)
                .findFirst()
                .orElseThrow();
    }

    private static void validateSize(List<String> separatedInput) {
        if (separatedInput.size() != NOT_MOVE_COMMAND_LENGTH && separatedInput.size() != MOVE_COMMAND_LENGTH) {
            throw new IllegalArgumentException("[ERROR] 입력값이 올바르지 않습니다.");
        }
    }

    private void validateMove(List<String> separatedInput) {
        if (commandType != CommandType.MOVE) {
            throw new IllegalArgumentException("[ERROR] 체스말 이동은 move를 사용해주세요");
        }
        if (separatedInput.size() != 3) {
            throw new IllegalArgumentException("[ERROR] 체스말 이동은 이동할 위치 정보가 필요합니다.");
        }
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public Optional<MoveCommandDto> getMoveCommandDto() {
        if (moveCommandDto == null) {
            return Optional.empty();
        }
        return Optional.of(moveCommandDto);
    }

    @Override
    public String toString() {
        return "Command{" +
                "commandType=" + commandType +
                ", moveCommandDto=" + moveCommandDto +
                '}';
    }
}
