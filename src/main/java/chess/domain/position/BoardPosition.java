package chess.domain.position;

import chess.service.ChessDataInitializer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BoardPosition {

    private static final List<Position> CHESSBOARD = new ArrayList<>();

    private BoardPosition() {
    }

    public static void generateBoard() {
        ChessDataInitializer dataInitializer = ChessDataInitializer.getInstance();

        Arrays.stream(Lettering.values())
                .flatMap(lettering -> Arrays.stream(Numbering.values())
                        .map(numbering -> dataInitializer.findPosition(lettering, numbering)))
                .forEach(CHESSBOARD::add);
    }

    public static boolean canFindUpPosition(Position position) {
        Numbering numbering = position.getNumbering();
        return Numbering.canFindNextNumbering(numbering);
    }

    public static boolean canFindUpPosition(Position position, int count) {
        Numbering numbering = position.getNumbering();
        return Numbering.canFindNextNumbering(numbering, count);
    }

    public static boolean canFindDownPosition(Position position) {
        Numbering numbering = position.getNumbering();
        return Numbering.canFindPreviousNumbering(numbering);
    }

    public static boolean canFindDownPosition(Position position, int count) {
        Numbering numbering = position.getNumbering();
        return Numbering.canFindPreviousNumbering(numbering, count);
    }

    public static boolean canFindLeftPosition(Position position) {
        Lettering lettering = position.getLettering();
        return Lettering.canFindPreviousLettering(lettering);
    }

    public static boolean canFindLeftPosition(Position position, int count) {
        Lettering lettering = position.getLettering();
        return Lettering.canFindPreviousLettering(lettering, count);
    }

    public static boolean canFindRightPosition(Position position) {
        Lettering lettering = position.getLettering();
        return Lettering.canFindNextLettering(lettering);
    }

    public static boolean canFindRightPosition(Position position, int count) {
        Lettering lettering = position.getLettering();
        return Lettering.canFindNextLettering(lettering, count);
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
        Numbering nextNumbering = Numbering.findNextNumbering(numbering);
        return findPosition(position.getLettering(), nextNumbering);
    }

    public static Position findUpPosition(Position position, int count) {
        Numbering numbering = position.getNumbering();
        Numbering nextNumbering = Numbering.findNextNumbering(numbering, count);
        return findPosition(position.getLettering(), nextNumbering);
    }

    public static Position findDownPosition(Position position) {
        Numbering numbering = position.getNumbering();
        Numbering previousLettering = Numbering.findPreviousNumbering(numbering);
        return findPosition(position.getLettering(), previousLettering);
    }

    public static Position findDownPosition(Position position, int count) {
        Numbering numbering = position.getNumbering();
        Numbering previousLettering = Numbering.findPreviousNumbering(numbering, count);
        return findPosition(position.getLettering(), previousLettering);
    }

    public static Position findLeftPosition(Position position) {
        Lettering lettering = position.getLettering();
        Lettering previousLettering = Lettering.findPreviousLettering(lettering);
        return findPosition(previousLettering, position.getNumbering());
    }

    public static Position findLeftPosition(Position position, int count) {
        Lettering lettering = position.getLettering();
        Lettering previousLettering = Lettering.findPreviousLettering(lettering, count);
        return findPosition(previousLettering, position.getNumbering());
    }

    public static Position findRightPosition(Position position) {
        Lettering lettering = position.getLettering();
        Lettering previousLettering = Lettering.findNextLettering(lettering);
        return findPosition(previousLettering, position.getNumbering());
    }

    public static Position findRightPosition(Position position, int count) {
        Lettering lettering = position.getLettering();
        Lettering previousLettering = Lettering.findNextLettering(lettering, count);
        return findPosition(previousLettering, position.getNumbering());
    }
}
