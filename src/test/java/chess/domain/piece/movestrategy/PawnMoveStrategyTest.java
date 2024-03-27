package chess.domain.piece.movestrategy;

import static chess.domain.TestSetting.C6;
import static chess.domain.TestSetting.D2;
import static chess.domain.TestSetting.D3;
import static chess.domain.TestSetting.D4;
import static chess.domain.TestSetting.D5;
import static chess.domain.TestSetting.D6;
import static chess.domain.TestSetting.E4;
import static chess.domain.TestSetting.E5;
import static chess.domain.TestSetting.E6;
import static chess.domain.TestSetting.E7;
import static chess.domain.TestSetting.F4;
import static chess.domain.TestSetting.PAWN_BLACK;
import static chess.domain.TestSetting.PAWN_WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
class PawnMoveStrategyTest {

    PawnMoveStrategy pawnMoveStrategy = PawnMoveStrategy.getInstance();

    @Test
    void 폰은_처음_움직일_경우_진영_기준으로_앞으로_두_칸까지_이동_가능() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D2, PAWN_WHITE); // 이동하려는 흰색 폰의 위치
        testPosition.put(E7, PAWN_BLACK); // 이동하려는 검정 폰의 위치
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> whitePawnMoveRange = pawnMoveStrategy.move(D2, piecePosition);
        Set<Position> blackPawnMoveRange = pawnMoveStrategy.move(E7, piecePosition);

        //then
        List<Position> whitePawnRange = List.of(D3, D4);
        List<Position> blackPawnRange = List.of(E6, E5);

        assertAll(
                () -> assertThat(whitePawnMoveRange).containsAll(whitePawnRange),
                () -> assertThat(blackPawnMoveRange).containsAll(blackPawnRange)
        );
    }

    @Test
    void 폰은_이동_경로에_다른_체스말이_있으면_해당_위치까지만_이동_가능() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D2, PAWN_WHITE); // 이동하려는 흰색 폰의 위치
        testPosition.put(E7, PAWN_BLACK); // 이동하려는 검정 폰의 위치

        testPosition.put(D4, PAWN_BLACK);
        testPosition.put(E6, PAWN_BLACK);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> whitePawnMoveRange = pawnMoveStrategy.move(D2, piecePosition);
        Set<Position> blackPawnMoveRange = pawnMoveStrategy.move(E7, piecePosition);

        //then
        assertAll(
                () -> assertThat(whitePawnMoveRange).containsExactly(D3),
                () -> assertThat(blackPawnMoveRange).isEmpty()
        );
    }

    @Test
    void 폰은_처음_움직임이_아닐_경우_진영_기준으로_앞으로_한_칸만_이동_가능() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D5, PAWN_WHITE); // 이동하려는 폰의 위치
        testPosition.put(E5, PAWN_BLACK); // 이동하려는 폰의 위치
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        pawnMoveStrategy.move(D5, piecePosition);
        pawnMoveStrategy.move(E5, piecePosition);

        Set<Position> whitePawnMoveRange = pawnMoveStrategy.move(D5, piecePosition);
        Set<Position> blackPawnMoveRange = pawnMoveStrategy.move(E5, piecePosition);

        //then
        assertAll(
                () -> assertThat(whitePawnMoveRange).containsExactly(D6),
                () -> assertThat(blackPawnMoveRange).containsExactly(E4)
        );
    }

    @Test
    void 폰은_진영_기준으로_전방_대각선에_다른_진영의_체스말이_있는_경우만_이동_가능() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D5, PAWN_WHITE); // 이동하려는 폰의 위치
        testPosition.put(E5, PAWN_BLACK); // 이동하려는 폰의 위치

        testPosition.put(C6, PAWN_BLACK);
        testPosition.put(E6, PAWN_WHITE);
        testPosition.put(D4, PAWN_WHITE);
        testPosition.put(F4, PAWN_BLACK);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        pawnMoveStrategy.move(D5, piecePosition);
        pawnMoveStrategy.move(E5, piecePosition);

        Set<Position> whitePawnMoveRange = pawnMoveStrategy.move(D5, piecePosition);
        Set<Position> blackPawnMoveRange = pawnMoveStrategy.move(E5, piecePosition);

        //then
        assertAll(
                () -> assertThat(whitePawnMoveRange).contains(C6),
                () -> assertThat(whitePawnMoveRange).doesNotContain(E6),
                () -> assertThat(blackPawnMoveRange).contains(D4),
                () -> assertThat(blackPawnMoveRange).doesNotContain(F4)
        );
    }
}
