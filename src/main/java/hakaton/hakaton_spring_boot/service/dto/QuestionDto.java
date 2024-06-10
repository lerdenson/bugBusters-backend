package hakaton.hakaton_spring_boot.service.dto;

public record QuestionDto(
        Long id,
        String theme,
        String question,
        String answer1,
        String answer2,
        String answer3,
        String answer4) {
}
