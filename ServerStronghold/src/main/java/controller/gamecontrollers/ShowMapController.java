package controller.gamecontrollers;

import controller.AppController;
import controller.MenusName;
import model.Kingdom;
import model.army.Army;
import model.army.ArmyType;
import model.army.Soldier;
import model.buildings.Building;
import model.buildings.BuildingType;
import model.buildings.DefenceBuilding;
import model.databases.GameDatabase;
import model.enums.Color;
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

    public String showMap(int x, int y) {
        checkIndex(x, y);
        StringBuilder outputMap = new StringBuilder();
        String boarder = "\n|" + "-".repeat(65) + "|\n";
        for (int i = currentMapX - height; i <= currentMapX + height; i++) {
            outputMap.append('|');
            for (int j = currentMapY - width; j <= currentMapY + width; j++)
                outputMap.append(FirstLine(i, j)).append('|');
            outputMap.append("\n");
            outputMap.append('|');
            for (int j = currentMapY - width; j <= currentMapY + width; j++)
                outputMap.append(SecondLine(i, j)).append('|');
            outputMap.append(boarder);
        }
        return outputMap.toString();
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

    private String SecondLine(int i, int j) {
        Kingdom kingdom = gameDatabase.getCurrentKingdom();
        Cell cell = map[i][j];
        Building building = cell.getExistingBuilding();
        char buildingIcon = 'B';
        if (building == null || (building.getBuildingType().equals(BuildingType.KILLING_PIT)
                && !building.getKingdom().equals(kingdom))) buildingIcon = ' ';
        else if (building instanceof DefenceBuilding || building.getBuildingType().getName().contains("tower") ||
                building.getBuildingType().getName().contains("turret")) buildingIcon = 'W';
        char troop = ' ';
        for (Army army : cell.getArmies()) {
            if (army.getArmyType().equals(ArmyType.ASSASSIN) && !army.getOwner().equals(gameDatabase.getCurrentKingdom())
                    && !((Soldier) army).visibility())
                continue;
            if (army.getPath().size() == 0) {
                troop = 'S';
                break;
            }
        }
        return cell.getTexture().getColor().toString() + " " + buildingIcon + ' ' + troop + ' ' + Color.RESET;
    }

    private String FirstLine(int i, int j) {
        Cell cell = map[i][j];
        char movingTroop = ' ';
        for (Army army : cell.getArmies()) {
            if (army.getArmyType().equals(ArmyType.ASSASSIN) && !army.getOwner().equals(gameDatabase.getCurrentKingdom())
                    && !((Soldier) army).visibility())
                continue;
            if (army.getPath().size() > 0) {
                movingTroop = 'M';
                break;
            }
        }
        char tree = ' ';
        if (cell.getTree() != null) tree = 'T';
        return cell.getTexture().getColor().toString() + " " + movingTroop + ' ' + tree + " " + Color.RESET;
    }

    public String moveMap(int changeY, int changeX) {
        return showMap(currentMapX + changeY, currentMapY + changeX);
    }

    public String showDetails(int x, int y) {
        if (x < 0 || x >= map.length || y < 0 || y >= map.length) return "\033[0;31mIndex out of bounds!\033[0m";
        Cell cell = map[x][y];
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
