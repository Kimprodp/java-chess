package chess.view;

import chess.domain.position.BoardPosition;
import chess.domain.position.Lettering;
import chess.domain.position.Numbering;
import chess.domain.position.Position;
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
                        + "> 게임 이동 : move source위치 target위치 - 예. move b2 b3";

        System.out.println(startMessage);
    }

    public static void printChess(PiecePositionDto piecePositionDto) {
        Map<Position, PieceDto> piecePosition = piecePositionDto.piecePosition();
        List<Numbering> numbering = reverseNumbering();

        for (Numbering number : numbering) {
            List<Position> chessRow = selectChessRow(number);
            printPosition(chessRow, piecePosition);
            System.out.println();
        }
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
}
