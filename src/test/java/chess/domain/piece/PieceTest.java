package chess.domain.piece;

import static chess.domain.TestSetting.C2;
import static chess.domain.TestSetting.C4;
import static chess.domain.TestSetting.D2;
import static chess.domain.TestSetting.D4;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.game.PiecePosition;
import chess.domain.game.PiecePositionGenerator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PieceTest {

    @Test
    void 특정_위치로_체스말을_이동() {
        //given
        PiecePositionGenerator piecePositionGenerator = PiecePositionGenerator.getInstance();
        PiecePosition piecePosition = new PiecePosition(piecePositionGenerator.generatePiecePosition());
        Piece whitePawn = piecePosition.findChessPieceOnPosition(D2);

        //when
        whitePawn.move(D2, D4, piecePosition);

        //then
        Piece chessPieceOnPosition = piecePosition.findChessPieceOnPosition(D4);

        assertThat(chessPieceOnPosition).isEqualTo(whitePawn);
    }

    @Test
    void 이동할_체스말이_올바른_위치에_있지_않을_경우_예외발생() {
        //given
        PiecePositionGenerator piecePositionGenerator = PiecePositionGenerator.getInstance();
        PiecePosition piecePosition = new PiecePosition(piecePositionGenerator.generatePiecePosition());
        Piece whitePawn = piecePosition.findChessPieceOnPosition(D2);

        //when, then
        assertThatThrownBy(() -> whitePawn.move(C2, C4, piecePosition))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
