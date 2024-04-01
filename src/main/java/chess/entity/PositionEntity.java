package chess.entity;

import chess.domain.position.Lettering;
import chess.domain.position.Numbering;

public class PositionEntity {

    private final String lettering;
    private final String numbering;

    public PositionEntity(String lettering, String numbering) {
        this.lettering = lettering;
        this.numbering = numbering;
    }

    public PositionEntity(Lettering lettering, Numbering numbering) {
        this(lettering.toString(), numbering.toString());
    }

    public String getLettering() {
        return lettering;
    }

    public String getNumbering() {
        return numbering;
    }

    @Override
    public String toString() {
        return "PositionEntity{" +
                "lettering='" + lettering + '\'' +
                ", numbering='" + numbering + '\'' +
                '}';
    }
}
