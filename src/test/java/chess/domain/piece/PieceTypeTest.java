package chess.domain.piece;

import static chess.domain.TestSetting.D2;
import static chess.domain.TestSetting.D3;
import static chess.domain.TestSetting.D4;
import static chess.domain.TestSetting.E1;
import static chess.domain.TestSetting.KNIGHT_WHITE;
import static chess.domain.TestSetting.PAWN_BLACK;
import static chess.domain.TestSetting.PAWN_WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.game.PiecePosition;
import chess.domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PieceTypeTest {

    @Test
    void 체스말의_타입에_맞는_행마법을_적용한_이동_가능한_범위를_반환() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D2, PAWN_WHITE);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> pawnMovableRange = PieceType.PAWN.executeMoveStrategy(D2, piecePosition);

        //then
        assertThat(pawnMovableRange).containsExactlyInAnyOrder(D3, D4);
    }

    @Test
    void 폰의_기본_점수는_1점으로_계산() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D2, PAWN_WHITE);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        double pawnScore = PieceType.calculatePawnScore(List.of(PAWN_WHITE), piecePosition);

        //then
        assertThat(pawnScore).isEqualTo(1);
    }

    @Test
    void 폰은_같은_세로줄에_같은_색의_폰이_있는_경우_1점이_아닌_05점으로_계산() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D2, new Piece(PieceType.PAWN, Camp.WHITE)); // 0.5
        testPosition.put(D3, new Piece(PieceType.PAWN, Camp.WHITE)); // 0.5
        testPosition.put(D4, new Piece(PieceType.PAWN, Camp.WHITE)); // 0.5
        testPosition.put(E1, new Piece(PieceType.PAWN, Camp.WHITE)); // 1.0
        PiecePosition piecePosition = new PiecePosition(testPosition);
        List<Piece> pawnPieces = piecePosition.findPieceByTypeAndCamp(PieceType.PAWN, Camp.WHITE);

        //when
        double pawnScore = PieceType.calculatePawnScore(pawnPieces, piecePosition);

        //then
        assertThat(pawnScore).isEqualTo(2.5);
    }

    @Test
    void 폰_점수_계산시_체스말이_없을_경우_예외발생() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when, then
        assertThatThrownBy(() -> PieceType.calculatePawnScore(List.of(), piecePosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 폰_점수_계산시_체스말이_폰이_아닐_경우_예외발생() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D2, KNIGHT_WHITE);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when, then
        assertThatThrownBy(() -> PieceType.calculatePawnScore(List.of(KNIGHT_WHITE), piecePosition))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 폰_점수_계산시_진영이_다를_경우_예외발생() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D2, PAWN_WHITE);
        testPosition.put(D3, PAWN_BLACK);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when, then
        assertThatThrownBy(() -> PieceType.calculatePawnScore(List.of(PAWN_WHITE, PAWN_BLACK), piecePosition))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
