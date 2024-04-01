package chess.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.position.Lettering;
import chess.domain.position.Numbering;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class InitialPieceGeneratorTest {

    InitialPieceGenerator initialPieceGenerator = InitialPieceGenerator.getInstance();

    @Test
    void 위치를_전달하면_시작_위치로_놓일_수_있는_체스말을_생성() {
        //given
        Position position = new Position(Lettering.B, Numbering.ONE);

        //when
        Piece piece = initialPieceGenerator.generate(position);

        //then
        PieceType expectedKnight = piece.getPieceType();
        Camp expectedWhite = piece.getCamp();

        assertAll(
                () -> assertThat(expectedKnight).isEqualTo(PieceType.KNIGHT),
                () -> assertThat(expectedWhite).isEqualTo(Camp.WHITE)
        );
    }
}
