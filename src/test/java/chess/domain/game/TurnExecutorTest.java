package chess.domain.game;

import static chess.domain.TestSetting.D2;
import static chess.domain.TestSetting.D3;
import static chess.domain.TestSetting.D4;
import static chess.domain.TestSetting.D5;
import static chess.domain.TestSetting.ROOK_BLACK;
import static chess.domain.TestSetting.ROOK_WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.piece.Camp;
import chess.domain.piece.Piece;
import chess.domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TurnExecutorTest {

    TurnExecutor turnExecutor;
    ChessStatus chessStatus;

    @BeforeEach
    void beforeEach() {
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D2, ROOK_WHITE);
        testPosition.put(D3, ROOK_BLACK);
        PiecePosition piecePosition = new PiecePosition(testPosition);
        this.turnExecutor = new TurnExecutor(piecePosition, Camp.WHITE);
        this.chessStatus = new ChessStatus(piecePosition);
    }

    @Test
    void 턴을_실행하면_실행_결과를_반환() {
        //given, when
        TurnResult turnResult = turnExecutor.execute(D2, D3, chessStatus);

        //then
        Piece movedPiece = turnResult.movedPiece();
        Position target = turnResult.target();

        assertAll(
                () -> assertThat(movedPiece).isEqualTo(ROOK_WHITE),
                () -> assertThat(target).isEqualTo(D3)
        );
    }

    @Test
    void 체스말이_없는_위치를_시작_위치로_설정할_경우_예외발생() {
        //given, when, then
        assertThatThrownBy(() -> turnExecutor.execute(D4, D5, chessStatus))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 처음_시작_턴은_흰색_진영이며_검정색_진영을_움직일_경우_예외발생() {
        //given, when, then
        assertThatThrownBy(() -> turnExecutor.execute(D3, D2, chessStatus))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 같은_진영을_연속으로_두번_움직일_경우_예외_발생() {
        //given
        turnExecutor.execute(D2, D3, chessStatus);

        //when, then
        assertThatThrownBy(() -> turnExecutor.execute(D3, D2, chessStatus))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
