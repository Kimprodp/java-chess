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
    EIGHT,
    ;

    private static final int FIRST_NUMBERING_ORDINAL = 0;
    private static final int LAST_NUMBERING_ORDINAL = Numbering.values().length - 1;

    Numbering() {
    }

    public static boolean canFindNextNumbering(Numbering numbering) {
        return numbering.ordinal() < LAST_NUMBERING_ORDINAL;
    }

    public static boolean canFindNextNumbering(Numbering numbering, int count) {
        return numbering.ordinal() + count <= LAST_NUMBERING_ORDINAL;
    }

    public static boolean canFindPreviousNumbering(Numbering numbering) {
        return numbering.ordinal() > FIRST_NUMBERING_ORDINAL;
    }

    public static boolean canFindPreviousNumbering(Numbering numbering, int count) {
        return numbering.ordinal() - count >= FIRST_NUMBERING_ORDINAL;
    }

    public static Numbering findNextNumbering(Numbering numbering) {
        if (!canFindNextNumbering(numbering)) {
            String errorMessage = String.format("[ERROR] %s는 마지막 Numbering 입니다.", numbering);
            throw new IllegalArgumentException(errorMessage);
        }

        int targetOrdinal = numbering.ordinal() + 1;
        return findNumbering(targetOrdinal);
    }

    public static Numbering findNextNumbering(Numbering numbering, int count) {
        if (!canFindNextNumbering(numbering, count)) {
            String errorMessage = String.format("[ERROR] numbering 범위를 초과합니다. : %s, %d", numbering, count);
            throw new IllegalArgumentException(errorMessage);
        }

        int targetOrdinal = numbering.ordinal() + count;
        return findNumbering(targetOrdinal);
    }

    public static Numbering findPreviousNumbering(Numbering numbering) {
        if (!canFindPreviousNumbering(numbering)) {
            String errorMessage = String.format("[ERROR] %s는 처음 Numbering 입니다.", numbering);
            throw new IllegalArgumentException(errorMessage);
        }

        int targetOrdinal = numbering.ordinal() - 1;
        return findNumbering(targetOrdinal);
    }

    public static Numbering findPreviousNumbering(Numbering numbering, int count) {
        if (!canFindPreviousNumbering(numbering, count)) {
            String errorMessage = String.format("[ERROR] numbering 범위 보다 작습니다. : %s, %d", numbering, count);
            throw new IllegalArgumentException(errorMessage);
        }

        int targetOrdinal = numbering.ordinal() - count;
        return findNumbering(targetOrdinal);
    }

    private static Numbering findNumbering(int targetOrdinal) {
        return Arrays.stream(Numbering.values())
                .filter(lettering -> lettering.ordinal() == targetOrdinal)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효한 Numbering 값이 아닙니다. :" + targetOrdinal));
    }
}
