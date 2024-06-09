package hakaton.hakaton_spring_boot.controller;

import hakaton.hakaton_spring_boot.controller.dto.RequestDto;
import hakaton.hakaton_spring_boot.controller.dto.ResponseDto;
import hakaton.hakaton_spring_boot.service.Coordinates;
import hakaton.hakaton_spring_boot.service.GameService;
import hakaton.hakaton_spring_boot.service.Question;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/bugBusters")
    Question getRandomQuestion() {
        return gameService.getRandomQuestion();
    }

    @PostMapping("/bugBusters")
    ResponseDto getAnswer(@RequestBody RequestDto requestDto) {
        String correctAnswer = gameService
                .checkAnswerAndAttackIfItRight(
                        requestDto.questionId(),
                        requestDto.answer(),
                        new Coordinates(requestDto.x(), requestDto.y()));

        Coordinates coordinates = gameService.attackByBug();
        return new ResponseDto(correctAnswer, coordinates, gameService.checkIsGameEnds());
    }

}
