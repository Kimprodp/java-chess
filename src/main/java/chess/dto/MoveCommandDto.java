package chess.dto;

public record MoveCommandDto(
        PositionDto moveSource,
        PositionDto target
) {
}
