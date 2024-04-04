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

    public boolean canFindNextNumbering() {
        return ordinal() < LAST_NUMBERING_ORDINAL;
    }

    public boolean canFindNextNumbering(int count) {
        return ordinal() + count <= LAST_NUMBERING_ORDINAL;
    }

    public boolean canFindPreviousNumbering() {
        return ordinal() > FIRST_NUMBERING_ORDINAL;
    }

    public boolean canFindPreviousNumbering(int count) {
        return ordinal() - count >= FIRST_NUMBERING_ORDINAL;
    }

    public Numbering findNextNumbering() {
        if (!canFindNextNumbering()) {
            throw new IllegalArgumentException("[ERROR] %s는 마지막 Numbering 입니다.");
        }

        int targetOrdinal = ordinal() + 1;
        return findNumbering(targetOrdinal);
    }

    public Numbering findNextNumbering(int count) {
        if (!canFindNextNumbering(count)) {
            throw new IllegalArgumentException("[ERROR] numbering 범위를 초과합니다.");
        }

        int targetOrdinal = ordinal() + count;
        return findNumbering(targetOrdinal);
    }

    public Numbering findPreviousNumbering() {
        if (!canFindPreviousNumbering()) {
            throw new IllegalArgumentException("[ERROR] %s는 처음 Numbering 입니다.");
        }

        int targetOrdinal = ordinal() - 1;
        return findNumbering(targetOrdinal);
    }

    public Numbering findPreviousNumbering(int count) {
        if (!canFindPreviousNumbering(count)) {
            throw new IllegalArgumentException("[ERROR] numbering 범위 보다 작습니다.");
        }

        int targetOrdinal = ordinal() - count;
        return findNumbering(targetOrdinal);
    }

    private Numbering findNumbering(int targetOrdinal) {
        return Arrays.stream(Numbering.values())
                .filter(lettering -> lettering.ordinal() == targetOrdinal)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효한 Numbering 값이 아닙니다. :" + targetOrdinal));
    }
}
