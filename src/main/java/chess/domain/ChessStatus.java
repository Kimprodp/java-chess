package chess.domain;

import chess.domain.piece.Camp;
import chess.domain.piece.Piece;
import chess.domain.piece.PieceType;
import chess.dto.ChessStatusDto;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChessStatus {

    private static final double DEFAULT_SCORE = 0.0;

    private Camp winner;
    private final Map<Camp, Double> score;

    public ChessStatus() {
        this.winner = Camp.NONE;
        this.score = initialSetting();
    }

    private Map<Camp, Double> initialSetting() {
        Map<Camp, Double> score = new HashMap<>();
        score.put(Camp.WHITE, DEFAULT_SCORE);
        score.put(Camp.BLACK, DEFAULT_SCORE);

        return score;
    }

    public void updateStatus(PiecePosition piecePosition) {
        updateWinner(piecePosition);
        updateStatusForm(piecePosition, Camp.WHITE);
        updateStatusForm(piecePosition, Camp.BLACK);
    }

    public boolean isGameInProgress() {
        return winner == Camp.NONE;
    }

    private void updateWinner(PiecePosition piecePosition) {
        List<Piece> whiteKing = piecePosition.findPieceByTypeAndCamp(PieceType.KING, Camp.WHITE);
        List<Piece> blackKing = piecePosition.findPieceByTypeAndCamp(PieceType.KING, Camp.BLACK);
        if (whiteKing.isEmpty()) {
            winner = Camp.BLACK;
        }
        if (blackKing.isEmpty()) {
            winner = Camp.WHITE;
        }
    }

    private void updateStatusForm(PiecePosition piecePosition, Camp camp) {
        updatePawnScore(piecePosition, camp);
        List<PieceType> pieceTypeToCalculateScore = excludeKingAndPawnPieceTypes();
        updateOtherTypeScore(piecePosition, camp, pieceTypeToCalculateScore);
    }

    private void updatePawnScore(PiecePosition piecePosition, Camp camp) {
        List<Piece> pawnPieces = piecePosition.findPieceByTypeAndCamp(PieceType.PAWN, camp);
        if (!pawnPieces.isEmpty()) {
            double pawnScore = PieceType.calculatePawnScore(pawnPieces, piecePosition);
            double addedScore = score.get(camp) + pawnScore;
            score.put(camp, addedScore);
        }
    }

    private void updateOtherTypeScore(PiecePosition piecePosition, Camp camp,
                                      List<PieceType> pieceTypeToCalculateScore) {
        for (PieceType pieceType : pieceTypeToCalculateScore) {
            List<Piece> pieces = piecePosition.findPieceByTypeAndCamp(pieceType, camp);
            int numberOfPieceType = pieces.size();
            double pieceTypeScore = numberOfPieceType * pieceType.getScore();
            double addedScore = score.get(camp) + pieceTypeScore;
            score.put(camp, addedScore);
        }
    }

    private List<PieceType> excludeKingAndPawnPieceTypes() {
        return Arrays.stream(PieceType.values())
                .filter(pieceType -> pieceType != PieceType.KING && pieceType != PieceType.PAWN)
                .toList();
    }

    public ChessStatusDto getStatus() {
        HashMap<Camp, Double> copiedScore = new HashMap<>(score);
        return new ChessStatusDto(winner, copiedScore);
    }
}
