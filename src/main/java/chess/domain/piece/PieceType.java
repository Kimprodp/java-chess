package chess.domain.piece;

import chess.domain.PiecePosition;
import chess.domain.piece.movestrategy.BishopMoveStrategy;
import chess.domain.piece.movestrategy.KingMoveStrategy;
import chess.domain.piece.movestrategy.KnightMoveStrategy;
import chess.domain.piece.movestrategy.MoveStrategy;
import chess.domain.piece.movestrategy.PawnMoveStrategy;
import chess.domain.piece.movestrategy.QueenMoveStrategy;
import chess.domain.piece.movestrategy.RookMoveStrategy;
import chess.domain.position.Lettering;
import chess.domain.position.Position;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public enum PieceType {

    KING(KingMoveStrategy::getInstance, 0.0),
    QUEEN(QueenMoveStrategy::getInstance, 9.0),
    BISHOP(BishopMoveStrategy::getInstance, 3.0),
    KNIGHT(KnightMoveStrategy::getInstance, 2.5),
    ROOK(RookMoveStrategy::getInstance, 5.0),
    PAWN(PawnMoveStrategy::getInstance, 1.0);

    private static final double DEFAULT_SCORE = 0.0;
    private static final double VERTICAL_DUPLICATE_PAWN_SCORE = 0.5;
    private static final int DUPLICATE_SCORE_CRITERIA = 1;

    private final Supplier<MoveStrategy> moveStrategySupplier;
    private final double score;

    PieceType(Supplier<MoveStrategy> moveStrategySupplier, double score) {
        this.moveStrategySupplier = moveStrategySupplier;
        this.score = score;
    }

    public static double calculatePawnScore(List<Piece> pawnPieces, PiecePosition piecePosition) {
        validateEmpty(pawnPieces);
        validatePieceIsPawn(pawnPieces);
        validateSameCamp(pawnPieces);
        return calculateVerticalPawnScore(pawnPieces, piecePosition);
    }

    private static double calculateVerticalPawnScore(List<Piece> pawnPieces, PiecePosition piecePosition) {
        double pawnScore = DEFAULT_SCORE;
        for (Lettering value : Lettering.values()) {
            int pawnCountInLettering = (int) pawnPieces.stream()
                    .filter(piece -> piecePosition.findPositionByPiece(piece).getLettering() == value)
                    .count();

            pawnScore += sumPawnScore(pawnCountInLettering);
        }

        return pawnScore;
    }

    private static double sumPawnScore(int pawnCountInLettering) {
        if (pawnCountInLettering < DUPLICATE_SCORE_CRITERIA) {
            return DEFAULT_SCORE;
        }
        if (pawnCountInLettering == DUPLICATE_SCORE_CRITERIA) {
            return PAWN.score;
        }
        return (double) pawnCountInLettering * VERTICAL_DUPLICATE_PAWN_SCORE;
    }

    private static void validateEmpty(List<Piece> pawnPieces) {
        if (pawnPieces.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 폰 점수를 계산하기 위한 체스말이 존재하지 않습니다.");
        }
    }

    private static void validatePieceIsPawn(List<Piece> pawnPieces) {
        boolean isAllPawn = pawnPieces.stream()
                .allMatch(piece -> piece.getPieceType() == PAWN);

        if (!isAllPawn) {
            throw new IllegalArgumentException("[ERROR] 폰 점수 계산은 폰 타입의 체스말만 가능합니다.");
        }
    }

    private static void validateSameCamp(List<Piece> pawnPieces) {
        Piece standardPiece = pawnPieces.get(0);
        boolean isAllSameCamp = pawnPieces.stream()
                .allMatch(piece -> piece.getCamp() == standardPiece.getCamp());

        if (!isAllSameCamp) {
            throw new IllegalArgumentException("[ERROR] 폰 점수 계산은 같은 진영끼리의 체스말만 가능합니다.");
        }
    }

    public Set<Position> executeMoveStrategy(Position standardPosition, PiecePosition piecePosition) {
        MoveStrategy moveStrategy = moveStrategySupplier.get();
        return moveStrategy.move(standardPosition, piecePosition);
    }

    public double getScore() {
        return score;
    }
}
