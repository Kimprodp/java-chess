package chess.domain.position;

import java.util.Arrays;

public enum Numbering {

    ONE,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT;

    private static final int FIRST_NUMBERING_ORDINAL = 0;
    private static final int LAST_NUMBERING_ORDINAL = Numbering.values().length - 1;

    Numbering() {
    }

    public static boolean isLastNumbering(Numbering numbering) {
        return numbering.ordinal() == LAST_NUMBERING_ORDINAL;
    }

    public static boolean isFirstNumbering(Numbering numbering) {
        return numbering.ordinal() == FIRST_NUMBERING_ORDINAL;
    }

    public static Numbering findNextNumbering(Numbering numbering) {
        if (numbering.ordinal() == LAST_NUMBERING_ORDINAL) {
            String errorMessage = String.format("[ERROR] %s는 마지막 Numbering 입니다.", numbering);
            throw new IllegalArgumentException(errorMessage);
        }

        int targetOrdinal = numbering.ordinal() + 1;
        return findNumbering(targetOrdinal);
    }

    public static Numbering findPreviousNumbering(Numbering numbering) {
        if (numbering.ordinal() == FIRST_NUMBERING_ORDINAL) {
            String errorMessage = String.format("[ERROR] %s는 처음 Numbering 입니다.", numbering);
            throw new IllegalArgumentException(errorMessage);
        }

        int targetOrdinal = numbering.ordinal() - 1;
        return findNumbering(targetOrdinal);
    }

    private static Numbering findNumbering(int targetOrdinal) {
        return Arrays.stream(Numbering.values())
                .filter(lettering -> lettering.ordinal() == targetOrdinal)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효한 Numbering 값이 아닙니다. :" + targetOrdinal));
    }
}
