package chess.domain.piece;

import static chess.domain.TestSetting.D2;
import static chess.domain.TestSetting.D3;
import static chess.domain.TestSetting.D4;
import static chess.domain.TestSetting.PAWN_WHITE;
import static org.assertj.core.api.Assertions.assertThat;

import chess.domain.PiecePosition;
import chess.domain.position.Position;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ChessPieceTypeTest {

    @Test
    void 체스말의_타입에_맞는_행마법을_적용한_이동_가능한_범위를_반환() {
        //given
        Map<Position, Piece> testPosition = new HashMap<>();
        testPosition.put(D2, PAWN_WHITE);
        PiecePosition piecePosition = new PiecePosition(testPosition);

        //when
        Set<Position> pawnMovableRange = ChessPieceType.PAWN.executeMoveStrategy(D2, piecePosition);

        //then
        assertThat(pawnMovableRange).containsExactly(D3, D4);
    }
}
