package chess.domain.position;

import java.util.Arrays;

public enum Lettering {

    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H,
    ;

    private static final int FIRST_LETTERING_ORDINAL = 0;
    private static final int LAST_LETTERING_ORDINAL = Lettering.values().length - 1;

    Lettering() {
    }

    public boolean canFindNextLettering() {
        return ordinal() < LAST_LETTERING_ORDINAL;
    }

    public boolean canFindNextLettering(int count) {
        return ordinal() + count <= LAST_LETTERING_ORDINAL;
    }

    public boolean canFindPreviousLettering() {
        return ordinal() > FIRST_LETTERING_ORDINAL;
    }

    public boolean canFindPreviousLettering(int count) {
        return ordinal() - count >= FIRST_LETTERING_ORDINAL;
    }

    public Lettering findNextLettering() {
        if (!canFindNextLettering()) {
            throw new IllegalArgumentException("[ERROR] %s는 마지막 Lettering 입니다.");
        }

        int targetOrdinal = ordinal() + 1;
        return findLettering(targetOrdinal);
    }

    public Lettering findNextLettering(int count) {
        if (!canFindNextLettering(count)) {
            throw new IllegalArgumentException("[ERROR] Lettering 범위를 초과합니다.");
        }

        int targetOrdinal = ordinal() + count;
        return findLettering(targetOrdinal);
    }

    public Lettering findPreviousLettering() {
        if (!canFindPreviousLettering()) {
            throw new IllegalArgumentException("[ERROR] %s는 처음 Lettering 입니다.");
        }

        int targetOrdinal = ordinal() - 1;
        return findLettering(targetOrdinal);
    }

    public Lettering findPreviousLettering(int count) {
        if (!canFindPreviousLettering(count)) {
            throw new IllegalArgumentException("[ERROR] Lettering 범위 보다 작습니다.");
        }

        int targetOrdinal = ordinal() - count;
        return findLettering(targetOrdinal);
    }

    private Lettering findLettering(int targetOrdinal) {
        return Arrays.stream(Lettering.values())
                .filter(lettering -> lettering.ordinal() == targetOrdinal)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효한 Lettering 값이 아닙니다. :" + targetOrdinal));
    }
}
