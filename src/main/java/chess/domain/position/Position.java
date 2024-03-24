package chess.domain.position;

import java.util.Objects;

public class Position {

    private final Lettering lettering;
    private final Numbering numbering;

    public Position(Lettering lettering, Numbering numbering) {
        this.lettering = lettering;
        this.numbering = numbering;
    }

    public Lettering getLettering() {
        return lettering;
    }

    public Numbering getNumbering() {
        return numbering;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Position position = (Position) object;
        return lettering == position.lettering && numbering == position.numbering;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lettering, numbering);
    }

    @Override
    public String toString() {
        return "Position{" +
                "lettering=" + lettering +
                ", numbering=" + numbering +
                '}';
    }
}
