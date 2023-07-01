package model.buildings;

import model.Kingdom;
import model.army.ArmyType;
import model.army.SoldierType;
import model.map.Cell;

import java.util.ArrayList;

public class TroopMakerBuilding extends Building {
    public TroopMakerBuilding(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        super(kingdom, cell, buildingType);
    }

    @Override
    public ArrayList<String> showDetails() {
        ArrayList<String> output = super.showDetails();
        output.add("soldiers it can make:");
        ArmyType[] values = ArmyType.values();
        for (int i = 0; i < values.length; i++) {
            ArmyType value = values[i];
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("%d) ", i + 1));
            final String name = value.toString().toLowerCase().replaceAll("_", " ");
            builder.append(name);
            SoldierType soldierType = SoldierType.stringToEnum(name);
            if (soldierType == null)
                continue;
            builder.append(" - ").append("weapon: ").append(
                    soldierType.getWeapon() == null ? "none" : soldierType.getWeapon().getName());
            builder.append(" - ").append("weapon: ").append(soldierType.getArmor() == null ?
                    "none" : soldierType.getArmor().getName());
            output.add(builder.toString());
        }
        return output;
    }
}
