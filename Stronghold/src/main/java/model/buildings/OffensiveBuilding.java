package model.buildings;

import java.util.ArrayList;

public class OffensiveBuilding extends Building {
    private boolean canAttack;
    private int damage;
    private boolean
    private ArrayList<String> troopsToCreate;

    public OffensiveBuilding(String type,
                             String category,
                             User owner,
                             String materialToBuild,
                             int numberOfMaterialToBuild,
                             int price,
                             int damage,
                             ArrayList<String> troopsToCreate) {
        //TODO
    }

    public int getDamage() {
        return damage;
    }

    public boolean canCreateTroop(String troopName) {
        //TODO
    }
}
