package chess.entity;

import chess.domain.piece.Camp;

public class ChessGameEntity {

    private final int chessGameId;
    private final int piecePositionId;
    private final int statusValue;

    public ChessGameEntity(int chessGameId, int piecePositionId, int statusValue) {
        this.chessGameId = chessGameId;
        this.piecePositionId = piecePositionId;
        this.statusValue = statusValue;
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

    public int getStatusValue() {
        return statusValue;
    }
}
