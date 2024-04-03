package chess.domain.game;

import chess.entity.ChessGameEntity;

public record InProgressGameInfo(
        ChessGameEntity chessGameData,
        ChessGame chessGame
) {
}
