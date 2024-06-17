package hakaton.hakaton_spring_boot.controller;

import hakaton.hakaton_spring_boot.controller.dto.AnswerRequestDto;
import hakaton.hakaton_spring_boot.controller.dto.AnswerResponseDto;
import hakaton.hakaton_spring_boot.controller.dto.StartRequestDto;
import hakaton.hakaton_spring_boot.service.Coordinates;
import hakaton.hakaton_spring_boot.service.GameService;
import hakaton.hakaton_spring_boot.service.dto.QuestionDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/api/start")
    void start(@RequestBody StartRequestDto startRequestDto) {
        System.out.println(startRequestDto);
        gameService.restartGame();
        if (startRequestDto.themes().isEmpty()) {
            if (gameService.getAllowedThemes().isEmpty()) {
                gameService.setAllowedThemes(gameService.getAllThemes().stream().toList());
            }
        } else {
            gameService.setAllowedThemes(startRequestDto.themes());
        }
        switch (startRequestDto.difficulty()) {
            case "easy" -> gameService.setBotProbability(40);
            case "hard" -> gameService.setBotProbability(80);
            case "medium" -> gameService.setBotProbability(60);
        }
    }

    @GetMapping("api/themes")
    Set<String> getThemes() {
        return gameService.getAllThemes();
    }

    @GetMapping("api/bugCells")
    List<Coordinates> getCellsUnderBugControl() {
        return gameService.getCellsUnderBotControl();
    }
    @GetMapping("api/playerCells")
    List<Coordinates> getCellsUnderPlayerControl() {
        return gameService.getCellsUnderPlayerControl();
    }

    @GetMapping("/bugBusters")
    QuestionDto getRandomQuestion() {
        return gameService.getRandomQuestion();
    }

    @PostMapping("/bugBusters")
    AnswerResponseDto getAnswer(@RequestBody AnswerRequestDto answerRequestDto) {
        String correctAnswer = gameService
                .checkAnswerAndAttackIfItRight(
                        (long) answerRequestDto.questionId(),
                        answerRequestDto.answer(),
                        new Coordinates(answerRequestDto.x(), answerRequestDto.y()));

        Coordinates coordinates = gameService.attackByBug();
        return new AnswerResponseDto(correctAnswer, coordinates, gameService.checkIsGameEnds());
    }

}
