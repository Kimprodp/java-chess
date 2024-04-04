package chess.domain.position;

import java.util.Arrays;
import java.util.List;

public class BoardPosition {

    private static final List<Position> CHESSBOARD = generateBoard();

    private BoardPosition() {
    }

    private static List<Position> generateBoard() {
        return Arrays.stream(Lettering.values())
                .flatMap(lettering -> Arrays.stream(Numbering.values())
                        .map(numbering -> new Position(lettering, numbering)))
                .toList();
    }

    public static boolean canFindUpPosition(Position position) {
        Numbering numbering = position.getNumbering();
        return numbering.canFindNextNumbering();
    }

    public static boolean canFindUpPosition(Position position, int count) {
        Numbering numbering = position.getNumbering();
        return numbering.canFindNextNumbering(count);
    }

    public static boolean canFindDownPosition(Position position) {
        Numbering numbering = position.getNumbering();
        return numbering.canFindPreviousNumbering();
    }

    public static boolean canFindDownPosition(Position position, int count) {
        Numbering numbering = position.getNumbering();
        return numbering.canFindPreviousNumbering(count);
    }

    public static boolean canFindLeftPosition(Position position) {
        Lettering lettering = position.getLettering();
        return lettering.canFindPreviousLettering();
    }

    public static boolean canFindLeftPosition(Position position, int count) {
        Lettering lettering = position.getLettering();
        return lettering.canFindPreviousLettering(count);
    }

    public static boolean canFindRightPosition(Position position) {
        Lettering lettering = position.getLettering();
        return lettering.canFindNextLettering();
    }

    public static boolean canFindRightPosition(Position position, int count) {
        Lettering lettering = position.getLettering();
        return lettering.canFindNextLettering(count);
    }

    public static Position findPosition(Lettering lettering, Numbering numbering) {
        return CHESSBOARD.stream()
                .filter(position -> position.equals(new Position(lettering, numbering)))
                .findFirst()
                .orElseThrow(() -> {
                    String errorMessage = String.format("[ERROR] 체스판의 위치를 찾을 수 없습니다. : %s, %s", lettering, numbering);
                    return new IllegalArgumentException(errorMessage);
                });
    }

    public static Position findUpPosition(Position position) {
        Numbering numbering = position.getNumbering();
        Numbering nextNumbering = numbering.findNextNumbering();
        return findPosition(position.getLettering(), nextNumbering);
    }

    public static Position findUpPosition(Position position, int count) {
        Numbering numbering = position.getNumbering();
        Numbering nextNumbering = numbering.findNextNumbering(count);
        return findPosition(position.getLettering(), nextNumbering);
    }

    public static Position findDownPosition(Position position) {
        Numbering numbering = position.getNumbering();
        Numbering previousLettering = numbering.findPreviousNumbering();
        return findPosition(position.getLettering(), previousLettering);
    }

    public static Position findDownPosition(Position position, int count) {
        Numbering numbering = position.getNumbering();
        Numbering previousLettering = numbering.findPreviousNumbering(count);
        return findPosition(position.getLettering(), previousLettering);
    }

    public static Position findLeftPosition(Position position) {
        Lettering lettering = position.getLettering();
        Lettering previousLettering = lettering.findPreviousLettering();
        return findPosition(previousLettering, position.getNumbering());
    }

    public static Position findLeftPosition(Position position, int count) {
        Lettering lettering = position.getLettering();
        Lettering previousLettering = lettering.findPreviousLettering(count);
        return findPosition(previousLettering, position.getNumbering());
    }

    public static Position findRightPosition(Position position) {
        Lettering lettering = position.getLettering();
        Lettering nextLettering = lettering.findNextLettering();
        return findPosition(nextLettering, position.getNumbering());
    }

    public static Position findRightPosition(Position position, int count) {
        Lettering lettering = position.getLettering();
        Lettering nextLettering = lettering.findNextLettering(count);
        return findPosition(nextLettering, position.getNumbering());
    }
}
