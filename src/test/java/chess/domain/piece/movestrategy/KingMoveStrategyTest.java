package chess.domain.piece.movestrategy;

import static chess.domain.TestSetting.C4;
import static chess.domain.TestSetting.C5;
import static chess.domain.TestSetting.C6;
import static chess.domain.TestSetting.D4;
import static chess.domain.TestSetting.D5;
import static chess.domain.TestSetting.D6;
import static chess.domain.TestSetting.E4;
import static chess.domain.TestSetting.E5;
import static chess.domain.TestSetting.E6;
import static chess.domain.TestSetting.KING_BLACK;
import static chess.domain.TestSetting.KING_WHITE;
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
class KingMoveStrategyTest {

    KingMoveStrategy kingMoveStrategy = KingMoveStrategy.getInstance();

    @Test
    void 킹은_모든_방향으로_한_칸_이동_가능() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D5, KING_WHITE); // 이동하려는 킹의 위치
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> kingMoveRange = kingMoveStrategy.move(D5, piecePosition);

        //then
        List<Position> upDirection = List.of(D6);
        List<Position> downDirection = List.of(D4);
        List<Position> leftDirection = List.of(C5);
        List<Position> rightDirection = List.of(E5);
        List<Position> upLeftDirection = List.of(C6);
        List<Position> upRightDirection = List.of(E6);
        List<Position> downLeftDirection = List.of(C4);
        List<Position> downRightDirection = List.of(E4);

        assertThat(kingMoveRange)
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
    void 킹은_이동_방향에_같은_진영의_체스말이_있으면_이동_불가() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D5, KING_WHITE); // 이동하려는 킹의 위치

        testPosition.put(D6, KING_WHITE);
        testPosition.put(D4, KING_WHITE);
        testPosition.put(C5, KING_WHITE);
        testPosition.put(E5, KING_WHITE);
        testPosition.put(C6, KING_WHITE);
        testPosition.put(E6, KING_WHITE);
        testPosition.put(C4, KING_WHITE);
        testPosition.put(E4, KING_WHITE);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> kingMoveRange = kingMoveStrategy.move(D5, piecePosition);

        //then
        assertThat(kingMoveRange).isEmpty();
    }

    @Test
    void 킹은_이동_방향에_다른_진영의_체스말이_있으면_이동_가능() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D5, KING_WHITE); // 이동하려는 킹의 위치

        testPosition.put(D6, KING_BLACK);
        testPosition.put(D4, KING_BLACK);
        testPosition.put(C5, KING_BLACK);
        testPosition.put(E5, KING_BLACK);
        testPosition.put(C6, KING_BLACK);
        testPosition.put(E6, KING_BLACK);
        testPosition.put(C4, KING_BLACK);
        testPosition.put(E4, KING_BLACK);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> kingMoveRange = kingMoveStrategy.move(D5, piecePosition);

        //then
        List<Position> upDirection = List.of(D6);
        List<Position> downDirection = List.of(D4);
        List<Position> leftDirection = List.of(C5);
        List<Position> rightDirection = List.of(E5);
        List<Position> upLeftDirection = List.of(C6);
        List<Position> upRightDirection = List.of(E6);
        List<Position> downLeftDirection = List.of(C4);
        List<Position> downRightDirection = List.of(E4);

        assertThat(kingMoveRange)
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
}
