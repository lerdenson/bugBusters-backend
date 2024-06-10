package hakaton.hakaton_spring_boot.controller;

import hakaton.hakaton_spring_boot.controller.dto.RequestDto;
import hakaton.hakaton_spring_boot.controller.dto.ResponseDto;
import hakaton.hakaton_spring_boot.service.Coordinates;
import hakaton.hakaton_spring_boot.service.GameService;
import hakaton.hakaton_spring_boot.service.dto.QuestionDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/api/start")
    void start() {
        gameService.restartGame();
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
    ResponseDto getAnswer(@RequestBody RequestDto requestDto) {
        String correctAnswer = gameService
                .checkAnswerAndAttackIfItRight(
                        (long)requestDto.questionId(),
                        requestDto.answer(),
                        new Coordinates(requestDto.x(), requestDto.y()));

        Coordinates coordinates = gameService.attackByBug();
        return new ResponseDto(correctAnswer, coordinates, gameService.checkIsGameEnds());
    }

}
