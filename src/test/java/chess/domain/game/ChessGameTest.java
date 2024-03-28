package chess.domain.game;

import static chess.domain.TestSetting.D2;
import static chess.domain.TestSetting.D3;
import static chess.domain.TestSetting.D4;
import static chess.domain.TestSetting.KING_BLACK;
import static chess.domain.TestSetting.KING_WHITE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import chess.domain.piece.Piece;
import chess.domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ChessGameTest {

    @Test
    void 턴_실행_시_이미_승리팀이_있을_경우_예외_발생() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D2, KING_WHITE);
        testPosition.put(D3, KING_BLACK);
        PiecePosition piecePosition = new PiecePosition(testPosition);
        TurnExecutor turnExecutor = new TurnExecutor(piecePosition);
        ChessStatus chessStatus = new ChessStatus();

        ChessGame chessGame = new ChessGame(turnExecutor, chessStatus);
        chessGame.executeTurn(D2, D3);

        //when, then
        assertThatThrownBy(() -> chessGame.executeTurn(D3, D4))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
