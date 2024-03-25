package chess.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.piece.Piece;
import chess.domain.position.Lettering;
import chess.domain.position.Numbering;
import chess.domain.position.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PiecePositionTest {

    PiecePosition piecePosition;

    @BeforeEach
    void beforeEach() {
        PiecePositionInitializer piecePositionInitializer = PiecePositionInitializer.getInstance();
        this.piecePosition = new PiecePosition(piecePositionInitializer.generateInitializedPiecePosition());
    }

    @Test
    void 특정_위치에_놓여있는_체스말을_조회() {
        //given
        Position startPositionOnPiece = new Position(Lettering.A, Numbering.ONE);

        //when
        Piece chessPieceOnPosition = piecePosition.findChessPieceOnPosition(startPositionOnPiece);

        //then
        assertThat(chessPieceOnPosition).isNotNull();
    }

    @Test
    void 특정_위치에_놓여있는_체스말을_조회시_체스말이_없는_경우_예외발생() {
        //given
        Position notStartPositionOnPiece = new Position(Lettering.A, Numbering.THREE);

        //then
        assertThatThrownBy(() -> piecePosition.findChessPieceOnPosition(notStartPositionOnPiece))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 특정_위치에_놓여있는지_여부를_확인() {
        //given
        Position startPositionOnPiece = new Position(Lettering.A, Numbering.ONE);
        Position notStartPositionOnPiece = new Position(Lettering.A, Numbering.THREE);

        //when
        boolean expectedTrue = piecePosition.hasPieceAt(startPositionOnPiece);
        boolean expectedFalse = piecePosition.hasPieceAt(notStartPositionOnPiece);

        //then
        assertAll(
                () -> assertThat(expectedTrue).isTrue(),
                () -> assertThat(expectedFalse).isFalse()
        );
    }
}
