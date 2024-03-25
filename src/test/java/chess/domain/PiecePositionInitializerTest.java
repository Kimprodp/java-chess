package chess.domain;

import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.piece.Piece;
import chess.domain.position.Position;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PiecePositionInitializerTest {

    PiecePositionInitializer piecePositionInitializer = PiecePositionInitializer.getInstance();

    @Test
    void 체스말_32개의_초기_위치가_설정된_체스_상태를_생성() {
        //given, when
        Map<Position, Piece> initialState = piecePositionInitializer.generateInitializedPiecePosition();

        //then
        assertThat(initialState).hasSize(32);
    }

    @Test
    void 동일한_위치에_체스말이_위치할_수_없음() {
        //given, when
        Map<Position, Piece> initialState = piecePositionInitializer.generateInitializedPiecePosition();

        int initialStateSize = initialState.size();
        Set<Position> notDuplicatedState = initialState.keySet();

        //then
        assertThat(notDuplicatedState).hasSize(initialStateSize);
    }

    @Test
    void 위치마다_놓인_체스말에는_빈_값이_있을_수_없음() {
        //given, when
        Map<Position, Piece> initialState = piecePositionInitializer.generateInitializedPiecePosition();

        int numberOfNullPiece = (int) initialState.keySet().stream()
                .filter(position -> initialState.get(position) != null)
                .count();

        //then
        assertThat(numberOfNullPiece).isEqualTo(0);
    }
}
