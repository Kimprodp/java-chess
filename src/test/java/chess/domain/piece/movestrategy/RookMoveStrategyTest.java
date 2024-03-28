package chess.domain.piece.movestrategy;

import static chess.domain.TestSetting.A5;
import static chess.domain.TestSetting.B5;
import static chess.domain.TestSetting.C5;
import static chess.domain.TestSetting.D1;
import static chess.domain.TestSetting.D2;
import static chess.domain.TestSetting.D3;
import static chess.domain.TestSetting.D4;
import static chess.domain.TestSetting.D5;
import static chess.domain.TestSetting.D6;
import static chess.domain.TestSetting.D7;
import static chess.domain.TestSetting.D8;
import static chess.domain.TestSetting.E5;
import static chess.domain.TestSetting.F5;
import static chess.domain.TestSetting.G5;
import static chess.domain.TestSetting.H5;
import static chess.domain.TestSetting.ROOK_BLACK;
import static chess.domain.TestSetting.ROOK_WHITE;
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
class RookMoveStrategyTest {

    RookMoveStrategy rookMoveStrategy = RookMoveStrategy.getInstance();

    @Test
    void 룩은_일직선_방향으로_끝까지_이동_가능() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D5, ROOK_WHITE); // 이동하려는 룩의 위치
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> rookMoveRange = rookMoveStrategy.move(D5, piecePosition);

        //then
        List<Position> upDirection = List.of(D6, D7, D8);
        List<Position> downDirection = List.of(D4, D3, D2, D1);
        List<Position> leftDirection = List.of(A5, B5, C5);
        List<Position> rightDirection = List.of(E5, F5, G5, H5);

        assertThat(rookMoveRange)
                .containsAll(upDirection)
                .containsAll(downDirection)
                .containsAll(leftDirection)
                .containsAll(rightDirection)
                .hasSize(14);
    }

    @Test
    void 룩은_이동_경로에_같은_진영의_체스말이_있으면_해당_위치_전까지_이동_가능() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D5, ROOK_WHITE); // 이동하려는 룩의 위치

        testPosition.put(B5, ROOK_WHITE);
        testPosition.put(D3, ROOK_WHITE);
        testPosition.put(D7, ROOK_WHITE);
        testPosition.put(F5, ROOK_WHITE);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> rookMoveRange = rookMoveStrategy.move(D5, piecePosition);

        //then
        List<Position> upDirection = List.of(D6);
        List<Position> downDirection = List.of(D4);
        List<Position> leftDirection = List.of(C5);
        List<Position> rightDirection = List.of(E5);

        assertThat(rookMoveRange)
                .containsAll(upDirection)
                .containsAll(downDirection)
                .containsAll(leftDirection)
                .containsAll(rightDirection)
                .hasSize(4);
    }

    @Test
    void 룩은_이동_경로에_다른_진영의_체스말이_있으면_해당_위치까지만_이동_가능() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D5, ROOK_WHITE); // 이동하려는 룩의 위치

        testPosition.put(B5, ROOK_BLACK);
        testPosition.put(D3, ROOK_BLACK);
        testPosition.put(D7, ROOK_BLACK);
        testPosition.put(F5, ROOK_BLACK);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> rookMoveRange = rookMoveStrategy.move(D5, piecePosition);

        //then
        List<Position> upDirection = List.of(D6, D7);
        List<Position> downDirection = List.of(D4, D3);
        List<Position> leftDirection = List.of(C5, B5);
        List<Position> rightDirection = List.of(E5, F5);

        assertThat(rookMoveRange)
                .containsAll(upDirection)
                .containsAll(downDirection)
                .containsAll(leftDirection)
                .containsAll(rightDirection)
                .hasSize(8);
    }
}
