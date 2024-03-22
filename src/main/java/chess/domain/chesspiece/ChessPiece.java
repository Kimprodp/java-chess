package chess.domain.chesspiece;

import chess.domain.chessboard.ChessBoard;
import chess.domain.chessboard.Square;
import chess.dto.ChessPieceDto;

public class ChessPiece {

    private final Camp camp;
    private final ChessPieceProperty chessPieceProperty;

    public ChessPiece(Camp camp, ChessPieceProperty chessPieceProperty) {
        this.camp = camp;
        this.chessPieceProperty = chessPieceProperty;
    }

    public void move(ChessBoard chessBoard, Square startSquare, Square targetSquare) {
        chessPieceProperty.executeMoveStrategy(chessBoard, startSquare, targetSquare);
    }

    public ChessPieceDto createDto() {
        return new ChessPieceDto(getChessPieceType(), camp);

    }

    public boolean isBlackCamp() {
        return camp == Camp.BLACK;
    }

    public boolean isWhiteCamp() {
        return camp == Camp.WHITE;

    }

    public ChessPieceType getChessPieceType() {
        return chessPieceProperty.getChessPieceType();
    }

    public Camp getCamp() {
        return camp;
    }
}
