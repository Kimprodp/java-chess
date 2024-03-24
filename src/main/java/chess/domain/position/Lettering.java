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
    H;

    private static final int FIRST_LETTERING_ORDINAL = 0;
    private static final int LAST_LETTERING_ORDINAL = Lettering.values().length - 1;

    Lettering() {
    }

    public static boolean isFirstLettering(Lettering lettering) {
        return lettering.ordinal() == FIRST_LETTERING_ORDINAL;
    }

    public static boolean isLastLettering(Lettering lettering) {
        return lettering.ordinal() == LAST_LETTERING_ORDINAL;
    }

    public static Lettering findNextLettering(Lettering lettering) {
        if (lettering.ordinal() == LAST_LETTERING_ORDINAL) {
            String errorMessage = String.format("[ERROR] %s는 마지막 Lettering 입니다.", lettering);
            throw new IllegalArgumentException(errorMessage);
        }

        int targetOrdinal = lettering.ordinal() + 1;
        return findLettering(targetOrdinal);
    }

    public static Lettering findPreviousLettering(Lettering lettering) {
        if (lettering.ordinal() == FIRST_LETTERING_ORDINAL) {
            String errorMessage = String.format("[ERROR] %s는 처음 Lettering 입니다.", lettering);
            throw new IllegalArgumentException(errorMessage);
        }

        int targetOrdinal = lettering.ordinal() - 1;
        return findLettering(targetOrdinal);
    }

    private static Lettering findLettering(int targetOrdinal) {
        return Arrays.stream(Lettering.values())
                .filter(lettering -> lettering.ordinal() == targetOrdinal)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효한 Lettering 값이 아닙니다. :" + targetOrdinal));
    }
}
