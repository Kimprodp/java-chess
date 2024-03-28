package chess.domain.game;

import static chess.domain.TestSetting.A1;
import static chess.domain.TestSetting.A2;
import static chess.domain.TestSetting.A3;
import static chess.domain.TestSetting.A4;
import static chess.domain.TestSetting.A5;
import static chess.domain.TestSetting.A6;
import static chess.domain.TestSetting.B1;
import static chess.domain.TestSetting.BISHOP_WHITE;
import static chess.domain.TestSetting.D1;
import static chess.domain.TestSetting.D2;
import static chess.domain.TestSetting.D3;
import static chess.domain.TestSetting.KING_BLACK;
import static chess.domain.TestSetting.KING_WHITE;
import static chess.domain.TestSetting.KNIGHT_WHITE;
import static chess.domain.TestSetting.QUEEN_WHITE;
import static chess.domain.TestSetting.ROOK_BLACK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.piece.Camp;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.domain.position.Position;
import chess.dto.ChessStatusDto;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ChessStatusTest {

    ChessStatus chessStatus = new ChessStatus();

    @Test
    void 점수는_초기_0점으로_설정() {
        //given, when
        ChessStatusDto status = chessStatus.getStatus();
        Map<Camp, Double> score = status.score();

        //then
        boolean allScoreInitialized = score.keySet().stream()
                .allMatch(camp -> score.get(camp) == 0);

        assertThat(allScoreInitialized).isTrue();
    }

    @Test
    void 승리팀은_초기_NONE으로_설정() {
        //given, when
        ChessStatusDto status = chessStatus.getStatus();
        Map<Camp, Double> score = status.score();

        //then
        Camp winnerCamp = status.winner();

        assertThat(winnerCamp).isEqualTo(Camp.NONE);
    }

    @Test
    void 킹을_잡은_진영을_승리팀으로_선정() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D2, KING_WHITE);
        testPosition.put(D3, KING_BLACK);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        piecePosition.movePiece(KING_WHITE, D3);
        chessStatus.updateStatus(piecePosition);

        //then
        ChessStatusDto status = chessStatus.getStatus();
        Camp winnerCamp = status.winner();

        assertThat(winnerCamp).isEqualTo(Camp.WHITE);
    }

    @Test
    void 진영별로_현재_남아_있는_말에_대한_점수를_계산() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(A1, new Piece(PieceType.PAWN, Camp.WHITE)); // 0.5
        testPosition.put(A2, new Piece(PieceType.PAWN, Camp.WHITE)); // 0.5
        testPosition.put(A3, KNIGHT_WHITE); // 2.5
        testPosition.put(A4, KING_WHITE); // 0
        testPosition.put(A5, QUEEN_WHITE); // 9
        testPosition.put(A6, BISHOP_WHITE); // 3
        testPosition.put(B1, new Piece(PieceType.PAWN, Camp.WHITE)); // 1

        testPosition.put(D1, ROOK_BLACK); // 5
        testPosition.put(D2, ROOK_BLACK); // 5
        testPosition.put(D3, KING_BLACK); // 0
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        chessStatus.updateStatus(piecePosition);

        //then
        Map<Camp, Double> score = chessStatus.getStatus().score();
        double whiteCampScore = score.get(Camp.WHITE);
        double blackCampScore = score.get(Camp.BLACK);

        assertAll(
                () -> assertThat(whiteCampScore).isEqualTo(16.5),
                () -> assertThat(blackCampScore).isEqualTo(10)
        );
    }

    @Test
    void 승리팀이_있을_경우_게임_진행_불가를_반환() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D2, KING_WHITE);
        testPosition.put(D3, KING_BLACK);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        piecePosition.movePiece(KING_BLACK, D2);
        chessStatus.updateStatus(piecePosition);
        boolean gameInProgress = chessStatus.isGameInProgress();

        //then
        assertThat(gameInProgress).isFalse();
    }
}
