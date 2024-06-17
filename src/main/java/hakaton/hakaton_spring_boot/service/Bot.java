package hakaton.hakaton_spring_boot.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class Bot {
    private final Field field;

//    in percents
    private int probability;

    public Bot(Field field) {
        this.field = field;
        probability = 60;
    }

    public Coordinates chooseCellAndAttack() {
        if (ThreadLocalRandom.current().nextInt(100) < probability) {
            List<Coordinates> allCoordinatesBotCanAttack = field.getCoordinatesOfCellsPlayerCanAttack(Owner.BUG);
            if (allCoordinatesBotCanAttack.isEmpty()) {
                return new Coordinates(-1, -1);
            }
            List<Coordinates> coordinatesBotCanAttackUnderDefenderControl = allCoordinatesBotCanAttack.stream()
                    .filter(coordinates -> field.getOwnerOfCell(coordinates).equals(Owner.DEFENDER)).toList();

            Coordinates c;
            if (!coordinatesBotCanAttackUnderDefenderControl.isEmpty()) {
                c = coordinatesBotCanAttackUnderDefenderControl
                        .get(ThreadLocalRandom.current()
                                .nextInt(coordinatesBotCanAttackUnderDefenderControl.size()));
            }
             else {
                c = allCoordinatesBotCanAttack
                        .get(ThreadLocalRandom.current()
                                .nextInt(allCoordinatesBotCanAttack.size()));
            }
            field.changeOwnerOfCell(c, Owner.BUG);
            return c;
        }
        return new Coordinates(-1, -1);
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }
}
