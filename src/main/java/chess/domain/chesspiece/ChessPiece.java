package chess.domain.chesspiece;

<<<<<<< HEAD
import chess.domain.chessboard.ChessBoard;
import chess.domain.chessboard.Square;
import chess.dto.ChessPieceDto;

=======
>>>>>>> 47d89a9 (refactor: domain 패키지 추가)
public class ChessPiece {

    private final Camp camp;
    private final ChessPieceProperty chessPieceProperty;

    public ChessPiece(Camp camp, ChessPieceProperty chessPieceProperty) {
        this.camp = camp;
        this.chessPieceProperty = chessPieceProperty;
    }

<<<<<<< HEAD
    public void move(ChessBoard chessBoard, Square startSquare, Square targetSquare) {
        chessPieceProperty.executeMoveStrategy(chessBoard, startSquare, targetSquare);
    }

    public ChessPieceDto createDto() {
        return new ChessPieceDto(getChessPieceType(), camp);
=======
    public boolean isBlackCamp() {
        return camp == Camp.BLACK;
    }

    public boolean isWhiteCamp() {
        return camp == Camp.WHITE;
>>>>>>> 47d89a9 (refactor: domain 패키지 추가)
    }

    public ChessPieceType getChessPieceType() {
        return chessPieceProperty.getChessPieceType();
    }
<<<<<<< HEAD

    public Camp getCamp() {
        return camp;
    }
=======
>>>>>>> 47d89a9 (refactor: domain 패키지 추가)
}
