package hakaton.hakaton_spring_boot.controller.dto;

public record AnswerRequestDto(int questionId, String answer, int x, int y) {
}
