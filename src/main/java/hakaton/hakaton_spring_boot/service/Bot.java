package hakaton.hakaton_spring_boot.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class Bot {
    private final Field field;
    private final double probability;

    public Bot(Field field) {
        this.field = field;
        probability = 0.6d;
    }

    public Coordinates chooseCellAndAttack() {
        if (ThreadLocalRandom.current().nextDouble() < probability) {
            List<Coordinates> coordinates = field.getCoordinatesOfCellsPlayerCanAttack(Owner.BUG);
            if (coordinates.isEmpty()) {
                return new Coordinates(-1, -1);
            }
            System.out.println(coordinates);
            Coordinates c = coordinates.get(ThreadLocalRandom.current().nextInt(coordinates.size()));
            field.changeOwnerOfCell(c, Owner.BUG);
            return c;
        }
        return new Coordinates(-1, -1);
    }


}
