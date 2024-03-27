package chess.domain.piece.movestrategy;

import static chess.domain.TestSetting.B4;
import static chess.domain.TestSetting.B6;
import static chess.domain.TestSetting.C3;
import static chess.domain.TestSetting.C4;
import static chess.domain.TestSetting.C5;
import static chess.domain.TestSetting.C6;
import static chess.domain.TestSetting.C7;
import static chess.domain.TestSetting.D5;
import static chess.domain.TestSetting.E3;
import static chess.domain.TestSetting.E4;
import static chess.domain.TestSetting.E5;
import static chess.domain.TestSetting.E6;
import static chess.domain.TestSetting.E7;
import static chess.domain.TestSetting.F4;
import static chess.domain.TestSetting.F6;
import static chess.domain.TestSetting.KNIGHT_BLACK;
import static chess.domain.TestSetting.KNIGHT_WHITE;
import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.PiecePosition;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class KnightMoveStrategyTest {

    KnightMoveStrategy knightMoveStrategy = KnightMoveStrategy.getInstance();

    @Test
    void 나이트는_상하좌우의_L자_방향으로_이동_가능() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D5, KNIGHT_WHITE); // 이동하려는 나이트의 위치
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> knightMoveRange = knightMoveStrategy.move(D5, piecePosition);

        //then
        List<Position> upDirection = List.of(C7, E7);
        List<Position> downDirection = List.of(C3, E3);
        List<Position> leftDirection = List.of(B4, B6);
        List<Position> rightDirection = List.of(F4, F6);

        assertThat(knightMoveRange)
                .containsAll(upDirection)
                .containsAll(downDirection)
                .containsAll(leftDirection)
                .containsAll(rightDirection)
                .hasSize(8);
    }

    @Test
    void 나이트는_이동_경로에_다른_체스말이_있어도_해당_위치까지_이동_가능() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D5, KNIGHT_WHITE); // 이동하려는 나이트의 위치

        testPosition.put(C4, KNIGHT_WHITE);
        testPosition.put(C5, KNIGHT_WHITE);
        testPosition.put(C6, KNIGHT_WHITE);
        testPosition.put(E4, KNIGHT_BLACK);
        testPosition.put(E5, KNIGHT_BLACK);
        testPosition.put(E6, KNIGHT_BLACK);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> knightMoveRange = knightMoveStrategy.move(D5, piecePosition);

        //then
        List<Position> upDirection = List.of(C7, E7);
        List<Position> downDirection = List.of(C3, E3);
        List<Position> leftDirection = List.of(B4, B6);
        List<Position> rightDirection = List.of(F4, F6);

        assertThat(knightMoveRange)
                .containsAll(upDirection)
                .containsAll(downDirection)
                .containsAll(leftDirection)
                .containsAll(rightDirection)
                .hasSize(8);
    }

    @Test
    void 나이트는_목적지에_같은_진영의_체스말이_있으면_이동_불가() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D5, KNIGHT_WHITE); // 이동하려는 나이트의 위치

        testPosition.put(C7, KNIGHT_WHITE);
        testPosition.put(E7, KNIGHT_WHITE);
        testPosition.put(C3, KNIGHT_WHITE);
        testPosition.put(E3, KNIGHT_WHITE);
        testPosition.put(B4, KNIGHT_WHITE);
        testPosition.put(B6, KNIGHT_WHITE);
        testPosition.put(F4, KNIGHT_WHITE);
        testPosition.put(F6, KNIGHT_WHITE);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> knightMoveRange = knightMoveStrategy.move(D5, piecePosition);

        //then
        assertThat(knightMoveRange).isEmpty();
    }

    @Test
    void 나이트는_목적지에_다른_진영의_체스말이_있으면_이동_가능() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D5, KNIGHT_WHITE); // 이동하려는 나이트의 위치

        testPosition.put(C7, KNIGHT_BLACK);
        testPosition.put(E7, KNIGHT_BLACK);
        testPosition.put(C3, KNIGHT_BLACK);
        testPosition.put(E3, KNIGHT_BLACK);
        testPosition.put(B4, KNIGHT_BLACK);
        testPosition.put(B6, KNIGHT_BLACK);
        testPosition.put(F4, KNIGHT_BLACK);
        testPosition.put(F6, KNIGHT_BLACK);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> knightMoveRange = knightMoveStrategy.move(D5, piecePosition);

        //then
        List<Position> upDirection = List.of(C7, E7);
        List<Position> downDirection = List.of(C3, E3);
        List<Position> leftDirection = List.of(B4, B6);
        List<Position> rightDirection = List.of(F4, F6);

        assertThat(knightMoveRange)
                .containsAll(upDirection)
                .containsAll(downDirection)
                .containsAll(leftDirection)
                .containsAll(rightDirection)
                .hasSize(8);
    }
}
