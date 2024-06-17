package hakaton.hakaton_spring_boot.service;

import hakaton.hakaton_spring_boot.repository.QuestionRepository;
import hakaton.hakaton_spring_boot.repository.entity.Question;
import hakaton.hakaton_spring_boot.service.dto.QuestionDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class GameService {
    private final Field field;
    private final Bot bot;
    private final QuestionRepository questionRepository;
    private List<String> allowedThemes;


    public GameService(QuestionRepository questionRepository, Field field, Bot bot) {
        this.questionRepository = questionRepository;
        this.field = field;
        this.bot = bot;
        initializeField();
    }

    public QuestionDto getRandomQuestion() {
//        if (allowedThemes.isEmpty()) {
//            setAllowedThemes(getAllThemes().stream().toList());
//        }
        List<Question> questions = questionRepository.findAll().stream().filter(question -> allowedThemes.contains(question.getTheme())).toList();
        Question randomQuestion = questions.get(ThreadLocalRandom.current().nextInt(questions.size()));
        return new QuestionDto(
                randomQuestion.getId(),
                randomQuestion.getTheme(),
                randomQuestion.getQuestion(),
                randomQuestion.getAnswer1(),
                randomQuestion.getAnswer2(),
                randomQuestion.getAnswer3(),
                randomQuestion.getAnswer4());
    }

/*
    public QuestionDto getQuestion() {
        return new QuestionDto(
                19L,
                "Basics of Databases",
                "What is the purpose of an index in a database?",
                "To store data permanently",
                "To perform mathematical calculations",
                "To optimize data retrieval",
                "To visualize data"
                );
    }

    public String getAn(Long id) {
        return "To optimize data retrieval";
    }
*/

    private String getCorrectAnswer(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        return question.isPresent() ? question.get().getCorrectanswer() : "";
    }

    public Set<String> getAllThemes() {
        return questionRepository.findAll().stream().map(Question::getTheme).collect(Collectors.toSet());
    }

    public String checkAnswerAndAttackIfItRight(Long questionId, String answer, Coordinates coordinates) {
        String correctAnswer = getCorrectAnswer(questionId);
        if (correctAnswer.equals(answer) && field.checkIfOwnerCanAttackCell(coordinates, Owner.DEFENDER)) {
            field.changeOwnerOfCell(coordinates, Owner.DEFENDER);

        }
        return correctAnswer;
    }

    public List<Coordinates> getCellsUnderBotControl() {
        return field.getCoordinatesUnderOwnerControl(Owner.BUG);
    }

    public List<Coordinates> getCellsUnderPlayerControl() {
        return field.getCoordinatesUnderOwnerControl(Owner.DEFENDER);
    }

    public Coordinates attackByBug() {
        return bot.chooseCellAndAttack();
    }

    public void setBotProbability(int probability) {
        bot.setProbability(probability);
    }

    public void setAllowedThemes(List<String> themes) {
        this.allowedThemes = themes;
    }
    public List<String> getAllowedThemes() {
        return this.allowedThemes.stream().toList();
    }

    //return bug if bug wins, defender if player wins and neutral if game continues
    public int checkIsGameEnds() {
        if (getCellsUnderBotControl().isEmpty()) return 1;
        if (getCellsUnderPlayerControl().isEmpty()) return -1;
        return 0;
    }

    public void restartGame() {
        initializeField();
    }

    private void initializeField() {
        field.initializeField();
        field.changeOwnerOfCell(new Coordinates(0, 3), Owner.DEFENDER);
        field.changeOwnerOfCell(new Coordinates(3, 0), Owner.BUG);
    }
}
