package chess.domain.chesspiece;

<<<<<<< HEAD
import chess.domain.chessboard.ChessBoard;
import chess.domain.chessboard.Square;
=======
>>>>>>> 47d89a9 (refactor: domain 패키지 추가)
import chess.domain.chesspiece.movestrategy.MoveStrategy;

public class ChessPieceProperty {

    private final ChessPieceType chessPieceType;
    private final MoveStrategy moveStrategy;

    public ChessPieceProperty(ChessPieceType chessPieceType, MoveStrategy moveStrategy) {
        this.chessPieceType = chessPieceType;
        this.moveStrategy = moveStrategy;
    }

<<<<<<< HEAD
    public void executeMoveStrategy(ChessBoard chessBoard, Square startSquare, Square targetSquare) {
        moveStrategy.move(chessBoard, startSquare, targetSquare);
    }

=======
>>>>>>> 47d89a9 (refactor: domain 패키지 추가)
    public ChessPieceType getChessPieceType() {
        return chessPieceType;
    }
}
