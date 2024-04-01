package chess.entity;

import chess.domain.piece.Camp;
import chess.domain.piece.PieceType;

public class PieceEntity {

    private final String type;
    private final String camp;

    public PieceEntity(String type, String camp) {
        this.type = type;
        this.camp = camp;
    }

    public PieceEntity(PieceType pieceType, Camp camp) {
        this(pieceType.toString(), camp.toString());
    }

    public String getType() {
        return type;
    }

    public String getCamp() {
        return camp;
    }

    @Override
    public String toString() {
        return "PieceEntity{" +
                "type='" + type + '\'' +
                ", camp='" + camp + '\'' +
                '}';
    }
}
