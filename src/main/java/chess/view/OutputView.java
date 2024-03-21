package chess.view;

import chess.domain.chessboard.Numbering;
import chess.domain.chessboard.Square;
import chess.dto.ChessBoardDto;
import chess.dto.ChessPieceDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OutputView {

    private static final String SQUARE_FORMAT = ".";

    public static void printChessBoard(ChessBoardDto chessBoardDto) {
        Map<Square, Optional<ChessPieceDto>> chessBoard = chessBoardDto.chessBoard();
        List<Numbering> numbering = reverseNumbering();

        for (Numbering number : numbering) {
            List<Square> chessRow = selectChessRow(number, chessBoard);
            printSquare(chessRow, chessBoard);
            System.out.println();
        }
    }

    private static List<Numbering> reverseNumbering() {
        List<Numbering> numbering = new ArrayList<>(List.of(Numbering.values()));
        Collections.reverse(numbering);
        return numbering;
    }

    private static List<Square> selectChessRow(Numbering number, Map<Square, Optional<ChessPieceDto>> chessBoard) {
        return chessBoard.keySet().stream()
                .filter(square -> square.getNumbering() == number)
                .toList();
    }

    private static void printSquare(List<Square> chessRow, Map<Square, Optional<ChessPieceDto>> chessBoard) {
        for (Square square : chessRow) {
            ChessPieceDto chessPieceDto = chessBoard.get(square).orElse(null);
            printSquareWithChessPiece(chessPieceDto);
            printSquareWithoutChessPiece(chessPieceDto);
        }
    }

    private static void printSquareWithChessPiece(ChessPieceDto chessPieceDto) {
        if (chessPieceDto != null) {
            String chessPieceNotation = ChessPiecePrintFormat.findChessPieceNotation(chessPieceDto);
            System.out.print(chessPieceNotation);
        }
    }

    private static void printSquareWithoutChessPiece(ChessPieceDto chessPieceDto) {
        if (chessPieceDto == null) {
            System.out.print(SQUARE_FORMAT);
        }
    }
}
