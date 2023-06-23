package controller.gamecontrollers;

import model.army.Army;
import model.army.ArmyType;
import model.army.Soldier;
import model.databases.GameDatabase;
import model.map.Cell;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowMapController {

    private final int height;// real height = 2 * height + 1
    private final int width;// real width = 2 * height + 1
    private final GameDatabase gameDatabase;
    private final Cell[][] map;
    private int currentMapX;
    private int currentMapY;

    public ShowMapController(GameDatabase gameDatabase) {
        this.height = 2;
        this.width = 5;
        this.gameDatabase = gameDatabase;
        this.map = gameDatabase.getMap().getMap();
    }

    public boolean checkInvalidIndex(int x, int y) {
        return x > map.length || y > map.length;
    }

    private void checkIndex(int x, int y) {
        if (x < height) currentMapX = height;
        else if (x >= map[1].length - height) currentMapX = map.length - height - 1;
        else currentMapX = x;
        if (y < width) currentMapY = width;
        else if (y >= map.length - width) currentMapY = map.length - width - 1;
        else currentMapY = y;
    }

    public String showDetails(Cell cell) {
        String building = "";
        if (cell.getExistingBuilding() != null)
            building = "building: " + cell.getExistingBuilding().getBuildingType().getName() + "    ";
        String texture = "texture: " + cell.getTexture().name() + "    ";
        String tree = "";
        if (cell.getTree() != null) tree = "tree: " + cell.getTree().name() + "    ";
        String army = getArmiesString(cell.getArmies());
        return " " + building + texture + tree + '\n' + army;
    }

    private String getArmiesString(ArrayList<Army> armies) {
        StringBuilder outputArmy = new StringBuilder();
        HashMap<ArmyType, Integer> armyCount = new HashMap<>();
        for (Army army : armies) {
            if (army.getArmyType().equals(ArmyType.ASSASSIN) && !army.getOwner().equals(gameDatabase.getCurrentKingdom())
                    && !((Soldier) army).visibility())
                continue;
            ArmyType armyType = army.getArmyType();
            if (armyCount.containsKey(armyType)) armyCount.replace(armyType, armyCount.get(armyType) + 1);
            else armyCount.put(armyType, 1);
        }
        int amount = 0;
        for (ArmyType armyType : armyCount.keySet()) {
            outputArmy.append(armyType.toString()).append(" : ").append(armyCount.get(armyType)).append("  ");
            amount++;
            if (amount % 5 == 0)
                outputArmy.append('\n');
        }
        return outputArmy.toString();
    }
}
