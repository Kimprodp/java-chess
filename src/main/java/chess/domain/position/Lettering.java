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

    public static boolean canFindNextLettering(Lettering lettering) {
        return lettering.ordinal() < LAST_LETTERING_ORDINAL;
    }

    public static boolean canFindNextLettering(Lettering lettering, int count) {
        return lettering.ordinal() + count <= LAST_LETTERING_ORDINAL;
    }

    public static boolean canFindPreviousLettering(Lettering lettering) {
        return lettering.ordinal() > FIRST_LETTERING_ORDINAL;
    }

    public static boolean canFindPreviousLettering(Lettering lettering, int count) {
        return lettering.ordinal() - count >= FIRST_LETTERING_ORDINAL;
    }

    public static Lettering findNextLettering(Lettering lettering) {
        if (!canFindNextLettering(lettering)) {
            String errorMessage = String.format("[ERROR] %s는 마지막 Lettering 입니다.", lettering);
            throw new IllegalArgumentException(errorMessage);
        }

        int targetOrdinal = lettering.ordinal() + 1;
        return findLettering(targetOrdinal);
    }

    public static Lettering findNextLettering(Lettering lettering, int count) {
        if (!canFindNextLettering(lettering, count)) {
            String errorMessage = String.format("[ERROR] Lettering 범위를 초과합니다. : %s, %d", lettering, count);
            throw new IllegalArgumentException(errorMessage);
        }

        int targetOrdinal = lettering.ordinal() + count;
        return findLettering(targetOrdinal);
    }

    public static Lettering findPreviousLettering(Lettering lettering) {
        if (!canFindPreviousLettering(lettering)) {
            String errorMessage = String.format("[ERROR] %s는 처음 Lettering 입니다.", lettering);
            throw new IllegalArgumentException(errorMessage);
        }

        int targetOrdinal = lettering.ordinal() - 1;
        return findLettering(targetOrdinal);
    }

    public static Lettering findPreviousLettering(Lettering lettering, int count) {
        if (!canFindPreviousLettering(lettering, count)) {
            String errorMessage = String.format("[ERROR] Lettering 범위 보다 작습니다. : %s, %d", lettering, count);
            throw new IllegalArgumentException(errorMessage);
        }

        int targetOrdinal = lettering.ordinal() - count;
        return findLettering(targetOrdinal);
    }

    private static Lettering findLettering(int targetOrdinal) {
        return Arrays.stream(Lettering.values())
                .filter(lettering -> lettering.ordinal() == targetOrdinal)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효한 Lettering 값이 아닙니다. :" + targetOrdinal));
    }
}
