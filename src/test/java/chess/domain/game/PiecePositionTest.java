package chess.domain.game;

import static chess.domain.TestSetting.A1;
import static chess.domain.TestSetting.A2;
import static chess.domain.TestSetting.A3;
import static chess.domain.TestSetting.A4;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.piece.Camp;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.position.Position;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PiecePositionTest {

    PiecePosition piecePosition;

    @BeforeEach
    void beforeEach() {
        PiecePositionGenerator piecePositionGenerator = PiecePositionGenerator.getInstance();
        this.piecePosition = new PiecePosition(piecePositionGenerator.generatePiecePosition());
    }

    @Test
    void 특정_위치에_놓여있는_체스말을_조회() {
        //given
        Position startPositionOnPiece = A1;

        //when
        Piece chessPieceOnPosition = piecePosition.findChessPieceOnPosition(startPositionOnPiece);

        //then
        assertThat(chessPieceOnPosition).isNotNull();
    }

    @Test
    void 특정_위치에_놓여있는_체스말을_조회시_체스말이_없는_경우_예외발생() {
        //given
        Position notStartPositionOnPiece = A3;

        //then
        assertThatThrownBy(() -> piecePosition.findChessPieceOnPosition(notStartPositionOnPiece))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 체스말의_위치를_조회() {
        //given
        Piece pieceOnA1 = piecePosition.findChessPieceOnPosition(A1);

        //when
        Position positionByPiece = piecePosition.findPositionByPiece(pieceOnA1);

        //then
        assertThat(positionByPiece).isEqualTo(A1);
    }

    @Test
    void 체스말의_위치_조회시_체스말이_없을_경우_예외발생() {
        //given
        Piece pieceOnA1 = piecePosition.findChessPieceOnPosition(A1);
        Piece pieceOnA2 = piecePosition.findChessPieceOnPosition(A2);
        piecePosition.movePiece(pieceOnA1, A2);

        //when, then
        assertThatThrownBy(() -> piecePosition.findPositionByPiece(pieceOnA2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 특정_타입과_진영의_체스말을_모두_조회() {
        //given, when
        List<Piece> expectedWhiteBishopPieces = piecePosition.findPieceByTypeAndCamp(PieceType.BISHOP, Camp.WHITE);

        //then
        boolean isAllBishopAndWhite = expectedWhiteBishopPieces.stream()
                .allMatch(piece -> piece.getPieceType() == PieceType.BISHOP && piece.getCamp() == Camp.WHITE);

        assertAll(
                () -> assertThat(expectedWhiteBishopPieces).hasSize(2),
                () -> assertThat(isAllBishopAndWhite).isTrue()
        );
    }

    @Test
    void 체스말을_특정_위치로_이동시키면_이동된_위치에_있던_체스말을_대신함() {
        //given
        Piece chessPieceOnA1 = piecePosition.findChessPieceOnPosition(A1);
        Piece chessPieceOnA2 = piecePosition.findChessPieceOnPosition(A2);

        //when
        piecePosition.movePiece(chessPieceOnA1, A2);

        //then
        boolean a1HasPiece = piecePosition.hasPieceAt(A1);
        Piece movedPiece = piecePosition.findChessPieceOnPosition(A2);
        assertAll(
                () -> assertThat(chessPieceOnA2).isNotNull(),
                () -> assertThat(a1HasPiece).isFalse(),
                () -> assertThat(movedPiece).isEqualTo(chessPieceOnA1)
        );
    }

    @Test
    void 이동할_체스말이_없는경우_예외발생() {
        //given
        Piece chessPieceOnA1 = piecePosition.findChessPieceOnPosition(A1);
        Piece chessPieceOnA2 = piecePosition.findChessPieceOnPosition(A2);
        piecePosition.movePiece(chessPieceOnA1, A2);

        //when, then
        assertThatThrownBy(() -> piecePosition.movePiece(chessPieceOnA2, A4))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 특정_위치에_놓여있는지_여부를_확인() {
        //given
        Position startPositionOnPiece = A1;
        Position notStartPositionOnPiece = A3;

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
