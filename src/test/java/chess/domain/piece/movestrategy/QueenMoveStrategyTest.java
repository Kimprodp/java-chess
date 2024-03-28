package chess.domain.piece.movestrategy;

import static chess.domain.TestSetting.A2;
import static chess.domain.TestSetting.A5;
import static chess.domain.TestSetting.A8;
import static chess.domain.TestSetting.B3;
import static chess.domain.TestSetting.B5;
import static chess.domain.TestSetting.B7;
import static chess.domain.TestSetting.C4;
import static chess.domain.TestSetting.C5;
import static chess.domain.TestSetting.C6;
import static chess.domain.TestSetting.D1;
import static chess.domain.TestSetting.D2;
import static chess.domain.TestSetting.D3;
import static chess.domain.TestSetting.D4;
import static chess.domain.TestSetting.D5;
import static chess.domain.TestSetting.D6;
import static chess.domain.TestSetting.D7;
import static chess.domain.TestSetting.D8;
import static chess.domain.TestSetting.E4;
import static chess.domain.TestSetting.E5;
import static chess.domain.TestSetting.E6;
import static chess.domain.TestSetting.F3;
import static chess.domain.TestSetting.F5;
import static chess.domain.TestSetting.F7;
import static chess.domain.TestSetting.G2;
import static chess.domain.TestSetting.G5;
import static chess.domain.TestSetting.G8;
import static chess.domain.TestSetting.H1;
import static chess.domain.TestSetting.H5;
import static chess.domain.TestSetting.QUEEN_BLACK;
import static chess.domain.TestSetting.QUEEN_WHITE;
import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.game.PiecePosition;
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
class QueenMoveStrategyTest {

    QueenMoveStrategy queenMoveStrategy = QueenMoveStrategy.getInstance();

    @Test
    void 퀸은_모든_방향으로_끝까지_이동_가능() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D5, QUEEN_WHITE); // 이동하려는 퀸의 위치
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> queenMoveRange = queenMoveStrategy.move(D5, piecePosition);

        //then
        List<Position> upDirection = List.of(D6, D7, D8);
        List<Position> downDirection = List.of(D4, D3, D2, D1);
        List<Position> leftDirection = List.of(A5, B5, C5);
        List<Position> rightDirection = List.of(E5, F5, G5, H5);
        List<Position> upLeftDirection = List.of(C6, B7, A8);
        List<Position> upRightDirection = List.of(E6, F7, G8);
        List<Position> downLeftDirection = List.of(C4, B3, A2);
        List<Position> downRightDirection = List.of(E4, F3, G2, H1);

        assertThat(queenMoveRange)
                .containsAll(upDirection)
                .containsAll(downDirection)
                .containsAll(leftDirection)
                .containsAll(rightDirection)
                .containsAll(upLeftDirection)
                .containsAll(upRightDirection)
                .containsAll(downLeftDirection)
                .containsAll(downRightDirection)
                .hasSize(27);
    }

    @Test
    void 퀸은_이동_경로에_같은_진영의_체스말이_있으면_해당_위치_전까지_이동_가능() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D5, QUEEN_WHITE); // 이동하려는 퀸의 위치

        testPosition.put(B3, QUEEN_WHITE);
        testPosition.put(B5, QUEEN_WHITE);
        testPosition.put(B7, QUEEN_WHITE);
        testPosition.put(D3, QUEEN_WHITE);
        testPosition.put(D7, QUEEN_WHITE);
        testPosition.put(F3, QUEEN_WHITE);
        testPosition.put(F5, QUEEN_WHITE);
        testPosition.put(F7, QUEEN_WHITE);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> queenMoveRange = queenMoveStrategy.move(D5, piecePosition);

        //then
        List<Position> upDirection = List.of(D6);
        List<Position> downDirection = List.of(D4);
        List<Position> leftDirection = List.of(C5);
        List<Position> rightDirection = List.of(E5);
        List<Position> upLeftDirection = List.of(C6);
        List<Position> upRightDirection = List.of(E6);
        List<Position> downLeftDirection = List.of(C4);
        List<Position> downRightDirection = List.of(E4);

        assertThat(queenMoveRange)
                .containsAll(upDirection)
                .containsAll(downDirection)
                .containsAll(leftDirection)
                .containsAll(rightDirection)
                .containsAll(upLeftDirection)
                .containsAll(upRightDirection)
                .containsAll(downLeftDirection)
                .containsAll(downRightDirection)
                .hasSize(8);
    }

    @Test
    void 퀸은_이동_경로에_다른_진영의_체스말이_있으면_해당_위치까지만_이동_가능() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D5, QUEEN_WHITE); // 이동하려는 퀸의 위치

        testPosition.put(B3, QUEEN_BLACK);
        testPosition.put(B5, QUEEN_BLACK);
        testPosition.put(B7, QUEEN_BLACK);
        testPosition.put(D3, QUEEN_BLACK);
        testPosition.put(D7, QUEEN_BLACK);
        testPosition.put(F3, QUEEN_BLACK);
        testPosition.put(F5, QUEEN_BLACK);
        testPosition.put(F7, QUEEN_BLACK);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> queenMoveRange = queenMoveStrategy.move(D5, piecePosition);

        //then
        List<Position> upDirection = List.of(D6, D7);
        List<Position> downDirection = List.of(D4, D3);
        List<Position> leftDirection = List.of(C5, B5);
        List<Position> rightDirection = List.of(E5, F5);
        List<Position> upLeftDirection = List.of(C6, B7);
        List<Position> upRightDirection = List.of(E6, F7);
        List<Position> downLeftDirection = List.of(C4, B3);
        List<Position> downRightDirection = List.of(E4, F3);

        assertThat(queenMoveRange)
                .containsAll(upDirection)
                .containsAll(downDirection)
                .containsAll(leftDirection)
                .containsAll(rightDirection)
                .containsAll(upLeftDirection)
                .containsAll(upRightDirection)
                .containsAll(downLeftDirection)
                .containsAll(downRightDirection)
                .hasSize(16);
    }
}
