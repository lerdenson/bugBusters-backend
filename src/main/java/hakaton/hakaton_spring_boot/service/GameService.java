package hakaton.hakaton_spring_boot.service;

import org.springframework.stereotype.Service;

@Service
public class GameService {
    private Field field;
    private Bot bot;

    public GameService(Field field, Bot bot) {
        this.field= field;
        this.bot = bot;
    }

    public Question getRandomQuestion(){
        return new Question(1, "1", "2", "3", "4", "5", "6", "4");
    }
    public String getCorrectAnswer(int questionId) {
        return "4";
    }

    public String checkAnswerAndAttackIfItRight(int questionId, String answer, Coordinates coordinates) {
        String correctAnswer = getCorrectAnswer(questionId);
        if (correctAnswer.equals(answer) && field.checkCell(coordinates, Owner.DEFENDER)) {
            field.changeOwnerOfCell(coordinates, Owner.DEFENDER);

        }
        return correctAnswer;
    }

    public Coordinates attackByBug() {
        return bot.chooseCellAndAttack();
    }

    //return bug if bug wins, defender if player wins and neutral if game continues
    public int checkIsGameEnds() {
        if (field.countCellsUnderOwnerControl(Owner.DEFENDER) == 0) return -1;
        if (field.countCellsUnderOwnerControl(Owner.BUG) == 0) return 1;
        return 0;
    }

    public void restartGame() {
        initializeField();
    }

    private void initializeField() {
        field.initializeField();
        field.changeOwnerOfCell(new Coordinates(0, 0), Owner.DEFENDER);
        field.changeOwnerOfCell(new Coordinates(3, 3), Owner.BUG);
    }

    public Field getField() {
        return field;
    }
}
