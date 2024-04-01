package chess.view;

import java.util.Arrays;

public enum CommandType {

    START("start"),
    LOAD_GAME("load"),
    END("end"),
    MOVE("move"),
    STATUS("status");

    private final String name;

    CommandType(String name) {
        this.name = name;
    }

    public static CommandType from(String commandTypeName) {
        return Arrays.stream(CommandType.values())
                .filter(commandType -> commandType.name.equals(commandTypeName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 명령어가 올바르지 않습니다. : " + commandTypeName));
    }
}
