package model.buildings;

import model.Kingdom;
import model.army.ArmyType;
import model.army.Soldier;
import model.army.SoldierType;
import model.map.Cell;
import utils.Pair;

import java.util.Objects;

public class TroopMakerBuilding extends Building {
    public TroopMakerBuilding(Kingdom kingdom, Cell cell, BuildingType buildingType) {
        super(kingdom, cell, buildingType);
    }

    public boolean canProduceTroop(String name) {
        SoldierType soldierType = SoldierType.stringToEnum(name);
        if (soldierType == null) return false;
        if (soldierType.getArmor() != null && getKingdom().getStockedNumber(soldierType.getArmor()) == 0) return false;
        if (soldierType.getWeapon() != null && getKingdom().getStockedNumber(soldierType.getWeapon()) == 0) return false;
        return getKingdom().getGold() >= Objects.requireNonNull(ArmyType.stringToEnum(name)).getPrice();
        //TODO check for the available population of that kingdom
    }

    public void produceTroop(String name) {
        if (!canProduceTroop(name)) return;
        ArmyType armyType = Objects.requireNonNull(ArmyType.stringToEnum(name));
        SoldierType soldierType = Objects.requireNonNull(SoldierType.stringToEnum(name));
        getKingdom().changeStockNumber(new Pair<>(soldierType.getArmor(), -1));
        getKingdom().changeStockNumber(new Pair<>(soldierType.getWeapon(), -1));
        getKingdom().changeGold(-armyType.getPrice());
        //TODO assign one of the available people to become soldier
        Soldier soldier = new Soldier(getLocation(), armyType, getKingdom(), soldierType);
        getKingdom().addArmy(soldier);
        getLocation().addArmy(soldier);
    }
}
