package controller.gamecontrollers;

import model.army.Army;
import model.army.ArmyType;
import model.army.Soldier;
import model.buildings.Building;
import model.buildings.ProducerBuilding;
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

    public String showDetails(Cell cell) {
        String building = "";
        if (cell.getExistingBuilding() != null)
            building = "building: " + cell.getExistingBuilding().getBuildingType().getName() + "\n";
        String texture = "texture: " + cell.getTexture().name() + "\n";
        String tree = "";
        if (cell.getTree() != null) tree = "tree: " + cell.getTree().name() + "\n";
        String army = getArmiesString(cell.getArmies());
        return "" + building + texture + tree + '\n' + army;
    }

    public String showCellsDetails(ArrayList<Cell> cells) {
        String result = "";
        int soldiers = 0;
        int maxProduct = 0;
        int minProduct = 100;
        int products = 0;
        for (Cell cell : cells) {
            soldiers += cell.getArmies().size();
            Building building = cell.getExistingBuilding();
            if (building instanceof ProducerBuilding building1) {
                int number = building1.getBuildingType().getProduces().get(building1.getItemToProduce()).getObject2();
                if (number > maxProduct) maxProduct = number;
                if (number < minProduct) minProduct = number;
                products += number;
            }
        }
        if (minProduct == 100) minProduct = 0;
        double average = products / (double) cells.size();
        result += "Soldiers = " + soldiers;
        result += "\nMax produce rate = " + maxProduct;
        result += "\nMin produce rate = " + minProduct;
        result += "\nAverage of produce rate = " + average;
        return result;
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
            int hp = army.getHp();
            outputArmy.append(army.getArmyType().toString()).append(" : ").append(hp + "hp\n");
        }
        return outputArmy.toString();
    }
}
