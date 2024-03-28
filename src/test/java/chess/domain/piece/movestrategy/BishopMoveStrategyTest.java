package chess.domain.piece.movestrategy;

import static chess.domain.TestSetting.A2;
import static chess.domain.TestSetting.A8;
import static chess.domain.TestSetting.B3;
import static chess.domain.TestSetting.B7;
import static chess.domain.TestSetting.BISHOP_BLACK;
import static chess.domain.TestSetting.BISHOP_WHITE;
import static chess.domain.TestSetting.C4;
import static chess.domain.TestSetting.C6;
import static chess.domain.TestSetting.D5;
import static chess.domain.TestSetting.E4;
import static chess.domain.TestSetting.E6;
import static chess.domain.TestSetting.F3;
import static chess.domain.TestSetting.F7;
import static chess.domain.TestSetting.G2;
import static chess.domain.TestSetting.G8;
import static chess.domain.TestSetting.H1;
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
class BishopMoveStrategyTest {

    BishopMoveStrategy bishopMoveStrategy = BishopMoveStrategy.getInstance();

    @Test
    void 비숍은_대각선으로_끝까지_이동_가능() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D5, BISHOP_WHITE); // 이동하려는 비숍의 위치
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> bishopMoveRange = bishopMoveStrategy.move(D5, piecePosition);

        //then
        List<Position> upLeftDirection = List.of(C6, B7, A8);
        List<Position> upRightDirection = List.of(E6, F7, G8);
        List<Position> downLeftDirection = List.of(C4, B3, A2);
        List<Position> downRightDirection = List.of(E4, F3, G2, H1);

        assertThat(bishopMoveRange)

                .containsAll(upLeftDirection)
                .containsAll(upRightDirection)
                .containsAll(downLeftDirection)
                .containsAll(downRightDirection)
                .hasSize(13);
    }

    @Test
    void 비숍은_이동_경로에_같은_진영의_체스말이_있으면_해당_위치_전까지_이동_가능() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D5, BISHOP_WHITE); // 이동하려는 비숍의 위치

        testPosition.put(B3, BISHOP_WHITE);
        testPosition.put(B7, BISHOP_WHITE);
        testPosition.put(F3, BISHOP_WHITE);
        testPosition.put(F7, BISHOP_WHITE);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> bishopMoveRange = bishopMoveStrategy.move(D5, piecePosition);

        //then
        List<Position> upLeftDirection = List.of(C6);
        List<Position> upRightDirection = List.of(E6);
        List<Position> downLeftDirection = List.of(C4);
        List<Position> downRightDirection = List.of(E4);

        assertThat(bishopMoveRange)

                .containsAll(upLeftDirection)
                .containsAll(upRightDirection)
                .containsAll(downLeftDirection)
                .containsAll(downRightDirection)
                .hasSize(4);
    }

    @Test
    void 비숍은_이동_경로에_다른_진영의_체스말이_있으면_해당_위치까지만_이동_가능() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D5, BISHOP_WHITE); // 이동하려는 비숍의 위치

        testPosition.put(B3, BISHOP_BLACK);
        testPosition.put(B7, BISHOP_BLACK);
        testPosition.put(F3, BISHOP_BLACK);
        testPosition.put(F7, BISHOP_BLACK);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> bishopMoveRange = bishopMoveStrategy.move(D5, piecePosition);

        //then
        List<Position> upLeftDirection = List.of(C6, B7);
        List<Position> upRightDirection = List.of(E6, F7);
        List<Position> downLeftDirection = List.of(C4, B3);
        List<Position> downRightDirection = List.of(E4, F3);

        assertThat(bishopMoveRange)

                .containsAll(upLeftDirection)
                .containsAll(upRightDirection)
                .containsAll(downLeftDirection)
                .containsAll(downRightDirection)
                .hasSize(8);
    }
}
