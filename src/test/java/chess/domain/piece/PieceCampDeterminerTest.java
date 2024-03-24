package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.position.Numbering;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PieceCampDeterminerTest {

    PieceCampDeterminer pieceCampDeterminer = PieceCampDeterminer.getInstance();

    @Test
    void 넘버링이_1_또는_2인_경우_진영을_흰색으로_결정() {
        //given, when
        Camp camp1 = pieceCampDeterminer.determineCamp(Numbering.ONE);
        Camp camp2 = pieceCampDeterminer.determineCamp(Numbering.TWO);

        //then
        assertAll(
                () -> assertThat(camp1).isEqualTo(Camp.WHITE),
                () -> assertThat(camp2).isEqualTo(Camp.WHITE)
        );
    }

    @Test
    void 넘버링이_7_또는_8인_경우_진영은_검정색으로_결정() {
        //given, when
        Camp camp1 = pieceCampDeterminer.determineCamp(Numbering.SEVEN);
        Camp camp2 = pieceCampDeterminer.determineCamp(Numbering.EIGHT);

        //then
        assertAll(
                () -> assertThat(camp1).isEqualTo(Camp.BLACK),
                () -> assertThat(camp2).isEqualTo(Camp.BLACK)
        );
    }

    @Test
    void 넘버링이_3_4_5_6인_경우_예외발생() {
        //given, when, then
        assertAll(
                () -> assertThatThrownBy(() -> pieceCampDeterminer.determineCamp(Numbering.THREE)),
                () -> assertThatThrownBy(() -> pieceCampDeterminer.determineCamp(Numbering.FOUR)),
                () -> assertThatThrownBy(() -> pieceCampDeterminer.determineCamp(Numbering.FIVE)),
                () -> assertThatThrownBy(() -> pieceCampDeterminer.determineCamp(Numbering.SIX))
        );
    }
}
