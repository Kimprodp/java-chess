package chess.entity;

import chess.domain.piece.Camp;

public class ChessGameEntity {

    private final int chessGameId;
    private final int piecePositionId;
    private final int status_value;

    public ChessGameEntity(int chessGameId, int piecePositionId, int status_value) {
        this.chessGameId = chessGameId;
        this.piecePositionId = piecePositionId;
        this.status_value = status_value;
    }

    public ChessGameEntity(int chessGameId, int piecePositionId, Camp turnToMove) {
        this(chessGameId, piecePositionId, turnToMove.ordinal());
    }

    public int getChessGameId() {
        return chessGameId;
    }

    public int getPiecePositionId() {
        return piecePositionId;
    }

    public int getStatus_value() {
        return status_value;
    }
}
