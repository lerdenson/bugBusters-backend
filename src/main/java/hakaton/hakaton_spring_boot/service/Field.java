package hakaton.hakaton_spring_boot.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Field {
    private Map<Coordinates, Owner> cells;

    public Field() {
        initializeField();
    }

    public void changeOwnerOfCell(Coordinates coordinates, Owner newOwner) {
        cells.replace(coordinates, newOwner);
    }

    public Owner getOwnerOfCell(Coordinates coordinates) {
        return cells.get(coordinates);
    }

    public boolean checkCell(Coordinates coordinates, Owner owner) {
        if (coordinates.x() == 0 && coordinates.y() == 0) {
            return checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, 0, 1) ||
                    checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, 1, 0);
        }
        if (coordinates.x() == 0 && coordinates.y() < 3) {
            return checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, 0, 1) ||
                    checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, 1, 0) ||
                    checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, 0, -1);
        }

        if (coordinates.x() == 0 && coordinates.y() == 3) {
            return checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, 1, 0) ||
                    checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, 0, -1);
        }

        if (coordinates.x() < 3 && coordinates.y() == 0) {
            return checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, 0, 1) ||
                    checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, 1, 0) ||
                    checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, -1, 0);
        }

        if (coordinates.x() == 3 && coordinates.y() == 0) {
            return checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, 0, 1) ||
                    checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, -1, 0);
        }
        if (coordinates.x() < 3 && coordinates.y() < 3) {
            return checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, 0, 1) ||
                    checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, 1, 0) ||
                    checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, 0, -1) ||
                    checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, -1, 0);
        }
        if (coordinates.x() == 3 && coordinates.y() < 3) {
            return checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, 0, 1) ||
                    checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, 0, -1) ||
                    checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, -1, 0);
        }
        if (coordinates.x() < 3 && coordinates.y() == 3) {
            return checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, 1, 0) ||
                    checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, 0, -1) ||
                    checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, -1, 0);
        }
        if (coordinates.x() == 3 && coordinates.y() == 3) {
            return checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, 0, -1) ||
                    checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(coordinates, owner, -1, 0);
        }
        return false;
    }

    private boolean checkIsNeighbourCellControlledBySpecificOwnerWhileItNot(
            Coordinates coordinates,
            Owner owner,
            int xBias, int yBias) {
        return !cells.get(coordinates).equals(owner) &&
                cells.get(new Coordinates(
                        coordinates.x() + xBias,
                        coordinates.y() + yBias)
                ).equals(owner);
    }

    public List<Coordinates> getCoordinatesOfCellsPlayerCanAttack(Owner owner) {
        List<Coordinates> coordinates = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Coordinates c = new Coordinates(i, j);
                if (checkCell(c, owner)) {
                    coordinates.add(c);
                }
            }
        }
        return coordinates;
    }


    public void initializeField() {
        Map<Coordinates, Owner> cells = new HashMap<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                cells.put(new Coordinates(i, j), Owner.NEUTRAL);
            }
        }

        this.cells = cells;
    }

    public long countCellsUnderOwnerControl(Owner owner) {
        return cells
                .entrySet()
                .stream()
                .filter(e -> e.getValue().equals(owner))
                .count();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Coordinates c : cells.keySet()) {
            str.append("x: ").append(c.x())
                    .append(", y: ").append(c.y())
                    .append(", owner: ").append(cells.get(c).toString()).append('\n');
        }
        return str.toString();
    }
}
