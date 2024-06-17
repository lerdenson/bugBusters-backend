package hakaton.hakaton_spring_boot.controller.dto;

import hakaton.hakaton_spring_boot.service.Coordinates;

public record AnswerResponseDto(
        String answer,
        Coordinates coordinates,
        int winnerId
) {
}
