package chess.domain.position;

import java.util.Arrays;
import java.util.List;

public class BoardPosition {

    private static final List<Position> chessBoard = generateBoard();

    private BoardPosition() {
    }

    private static List<Position> generateBoard() {
        return Arrays.stream(Lettering.values())
                .flatMap(lettering -> Arrays.stream(Numbering.values())
                        .map(numbering -> new Position(lettering, numbering)))
                .toList();
    }

    public static boolean isTopPosition(Position position) {
        Numbering numbering = position.getNumbering();
        return Numbering.isLastNumbering(numbering);
    }

    public static boolean isBottomPosition(Position position) {
        Numbering numbering = position.getNumbering();
        return Numbering.isFirstNumbering(numbering);
    }

    public static boolean isLeftMostPosition(Position position) {
        Lettering lettering = position.getLettering();
        return Lettering.isFirstLettering(lettering);
    }

    public static boolean isRightMostPosition(Position position) {
        Lettering lettering = position.getLettering();
        return Lettering.isLastLettering(lettering);
    }

    public static Position findPosition(Lettering lettering, Numbering numbering) {
        return chessBoard.stream()
                .filter(position -> position.equals(new Position(lettering, numbering)))
                .findFirst()
                .orElseThrow(() -> {
                    String errorMessage = String.format("[ERROR] 체스판의 위치를 찾을 수 없습니다. : %s, %s", lettering, numbering);
                    return new IllegalArgumentException(errorMessage);
                });
    }

    public static Position findUpPosition(Position position) {
        Numbering numbering = position.getNumbering();
        Numbering nextNumbering = Numbering.findNextNumbering(numbering);
        return findPosition(position.getLettering(), nextNumbering);
    }

    public static Position findDownPosition(Position position) {
        Numbering numbering = position.getNumbering();
        Numbering previousLettering = Numbering.findPreviousNumbering(numbering);
        return findPosition(position.getLettering(), previousLettering);
    }

    public static Position findLeftPosition(Position position) {
        Lettering lettering = position.getLettering();
        Lettering previousLettering = Lettering.findPreviousLettering(lettering);
        return findPosition(previousLettering, position.getNumbering());
    }

    public static Position findRightPosition(Position position) {
        Lettering lettering = position.getLettering();
        Lettering previousLettering = Lettering.findNextLettering(lettering);
        return findPosition(previousLettering, position.getNumbering());
    }
}
