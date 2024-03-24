package chess.domain.piece;

import chess.domain.position.Numbering;
import java.util.List;

public class PieceCampDeterminer {

    private static final PieceCampDeterminer INSTANCE = new PieceCampDeterminer();
    private static final List<Numbering> WHITE_STARTING_NUMBERING = List.of(Numbering.ONE, Numbering.TWO);
    private static final List<Numbering> BLACK_STARTING_NUMBERING = List.of(Numbering.SEVEN, Numbering.EIGHT);

    private PieceCampDeterminer() {
    }

    public static PieceCampDeterminer getInstance() {
        return INSTANCE;
    }

    public Camp determineCamp(Numbering numbering) {
        if (WHITE_STARTING_NUMBERING.contains(numbering)) {
            return Camp.WHITE;
        }
        if (BLACK_STARTING_NUMBERING.contains(numbering)) {
            return Camp.BLACK;
        }
        throw new IllegalArgumentException("[ERROR] 진영 배정에 유효한 위치가 아닙니다.");
    }
}
