package hakaton.hakaton_spring_boot.service;

public record Question(
        int id,
        String theme,
        String question,
        String answer1,
        String answer2,
        String answer3,
        String answer4,
        String correctAnswer
) {
}
