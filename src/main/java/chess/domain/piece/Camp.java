package chess.domain.piece;

import java.util.Arrays;

public enum Camp {

    NONE,
    BLACK,
    WHITE;

    Camp() {
    }

    public static Camp getByOrdinal(int ordinal) {
        return Arrays.stream(Camp.values())
                .filter(camp -> camp.ordinal() == ordinal)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 일치하는 진영이 없습니다. : " + ordinal));
    }
}
