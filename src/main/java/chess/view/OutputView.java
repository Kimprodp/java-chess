package chess.view;

import chess.domain.piece.Camp;
import chess.domain.position.BoardPosition;
import chess.domain.position.Lettering;
import chess.domain.position.Numbering;
import chess.domain.position.Position;
import chess.dto.ChessStatusDto;
import chess.dto.PieceDto;
import chess.dto.PiecePositionDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final String NEW_LINE = System.lineSeparator();
    private static final String NOT_PIECE_FORMAT = ".";

    public static void printGameStart() {
        String startMessage =
                "> 체스 게임을 시작합니다." + NEW_LINE
                        + "> 게임 시작 : start" + NEW_LINE
                        + "> 게임 종료 : end" + NEW_LINE
                        + "> 게임 이동 : move source위치 target위치 - 예. move b2 b3" + NEW_LINE
                        + "> 게임 점수 : status";

        System.out.println(startMessage);
    }

    public static void printLoadGameStart() {
        System.out.println("* 이전에 종료되지 않은 게임이 있습니다. 게임을 불러오려면 'load'를 입력해주세요.");
    }

    public static void printChess(PiecePositionDto piecePositionDto) {
        Map<Position, PieceDto> piecePosition = piecePositionDto.piecePosition();
        List<Numbering> numbering = reverseNumbering();

        for (Numbering number : numbering) {
            List<Position> chessRow = selectChessRow(number);
            printPosition(chessRow, piecePosition);
            System.out.println();
        }
//        printDeadPiece(piecePositionDto.lastDeadPiece());
        System.out.println();
    }

    private static List<Numbering> reverseNumbering() {
        List<Numbering> numbering = new ArrayList<>(List.of(Numbering.values()));
        Collections.reverse(numbering);
        return numbering;
    }

    private static List<Position> selectChessRow(Numbering number) {
        return Arrays.stream(Lettering.values())
                .map(lettering -> BoardPosition.findPosition(lettering, number))
                .toList();
    }

    private static void printPosition(List<Position> chessRow, Map<Position, PieceDto> piecePosition) {
        for (Position position : chessRow) {
            printPositionWithChessPiece(piecePosition, position);
            printPositionWithoutChessPiece(piecePosition, position);
        }
    }

    private static void printPositionWithChessPiece(Map<Position, PieceDto> piecePosition, Position position) {
        if (piecePosition.containsKey(position)) {
            PieceDto pieceDto = piecePosition.get(position);
            String chessPieceNotation = ChessPiecePrintFormat.findChessPieceNotation(pieceDto);
            System.out.print(chessPieceNotation);
        }
    }

    private static void printPositionWithoutChessPiece(Map<Position, PieceDto> piecePosition, Position position) {
        if (!piecePosition.containsKey(position)) {
            System.out.print(NOT_PIECE_FORMAT);
        }
    }

    public static void printGameEnd() {
        System.out.println("게임이 종료되었습니다.");
    }

    public static void printStatus(ChessStatusDto chessStatusDto) {
        System.out.println("게임 결과");
        printWinner(chessStatusDto.winner());
        printScore(chessStatusDto.score());
        System.out.println();
    }

    private static void printWinner(Camp winner) {
        String winnerName = convertWinnerFormat(winner);
        System.out.printf("우승자 : %s", winnerName + NEW_LINE);;
    }

    private static String convertWinnerFormat(Camp winner) {
        if (winner == Camp.WHITE) {
            return "백";
        }
        if (winner == Camp.BLACK) {
            return "흑";
        }
        return "없음";
    }

    private static void printScore(Map<Camp, Double> score) {
        for (Camp camp : score.keySet()) {
            printCampScore(camp, score.get(camp));
        }
    }

    private static void printCampScore(Camp camp, double score) {
        if (camp == Camp.WHITE) {
            System.out.printf("백팀 점수 : %.1f%s", score, NEW_LINE);
        }
        if (camp == Camp.BLACK) {
            System.out.printf("흑팀 점수 : %.1f%s", score, NEW_LINE);
        }
    }
//    private static void printDeadPiece(PieceDto pieceDto) {
//        String chessPieceNotation = ChessPiecePrintFormat.findChessPieceNotation(pieceDto);
//        System.out.print(chessPieceNotation);
//    }
}
