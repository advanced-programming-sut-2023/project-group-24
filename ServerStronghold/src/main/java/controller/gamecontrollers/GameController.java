package controller.gamecontrollers;

import controller.functionalcontrollers.Pair;
import controller.functionalcontrollers.PathFinder;
import model.Kingdom;
import model.army.*;
import model.buildings.*;
import model.databases.GameDatabase;
import model.enums.Direction;
import model.enums.MovingType;
import model.map.Cell;
import model.map.Map;
import model.map.Texture;

import java.util.ArrayList;

public class GameController {
    private final GameDatabase gameDatabase;
    private Kingdom currentKingdom;

    public GameController(GameDatabase gameDatabase) {
        this.gameDatabase = gameDatabase;
    }

    public void nextTurn(KingdomController kingdomController) {
        gameDatabase.setSelectedUnits(new ArrayList<>());
        gameDatabase.setCurrentBuilding(null);
        currentKingdom = gameDatabase.getCurrentKingdom();
        makeWarMachines();
        checkStateOfUnits();
        moveUnits();
        handleBrazier();
        war();
        gameDatabase.nextTurn();
        canGateBeCaptured();
        kingdomController.nextTurn();
        hasKingdomsFallen();
        giveLastPlayerScoreAndEndGame();
        handleEngineerOil();
    }

    private void handleBrazier() {
        BuildingController buildingController = new BuildingController(gameDatabase);
        for (Army army : currentKingdom.getArmies()) {
            if (army instanceof Soldier
                    && army.getArmyType().getRange() > 1
                    && buildingController.checkNeighborContains(army.getLocation(), BuildingType.BRAZIER))
                ((Soldier) army).setCanBurnOnce(true);
        }
    }

    private void handleEngineerOil() {
        for (Army army : currentKingdom.getArmies()) {
            Building building = army.getLocation().getExistingBuilding();
            if (building != null) {
                if (building.getBuildingType().equals(BuildingType.OIL_SMELTER)) {
                    if (((EngineersNeededBuilding) building).hasEnoughWorkers())
                        army.changeEngineerState();
                    else {
                        ((EngineersNeededBuilding) building).assignEngineers((Soldier) army);
                        army.getLocation().removeArmy(army);
                    }
                }
            }
        }
    }

    private void makeWarMachines() {
        outer:
        while (true) {
            for (Building building : currentKingdom.getBuildings()) {
                if (building.getBuildingType().equals(BuildingType.SIEGE_TENT)) {
                    WarMachineType warMachineType = ((SiegeTent) building).getProducingWarMachine();
                    if (warMachineType == null) continue;
                    ArmyType armyType = ArmyType.stringToEnum(warMachineType.toString());
                    new WarMachine(building.getLocation(), armyType, currentKingdom, warMachineType);
                    currentKingdom.removeBuilding(building);
                    building.getLocation().setExistingBuilding(null);
                    continue outer;
                }
            }
            break;
        }
    }

    public boolean isGameDone() {
        return gameDatabase.getKingdoms().size() == 1;
    }

    public String getWinner() {
        return gameDatabase.getKingdoms().get(0).getOwner().getUsername();
    }

    public String getCurrentUser() {
        return gameDatabase.getCurrentKingdom().getOwner().getUsername();
    }

    private void giveLastPlayerScoreAndEndGame() {
        if (gameDatabase.getKingdoms().size() == 1) {
            int score = 2 * gameDatabase.getTurnPlayed();
            if (gameDatabase.getKingdoms().get(0).getOwner().getHighScore() < score)
                gameDatabase.getKingdoms().get(0).getOwner().setHighScore(score);
        }
    }

    private void war() {
        for (Army army : currentKingdom.getArmies()) {
            if (army.getTarget() == null)
                checkOtherTargets(army);
            if (army.getTarget() == null)
                continue;
            if (army.getTarget().getHp() < 0)
                setTarget(army);
            if (army.getTarget() == null)
                continue;
            if (army.canAttack())
                army.getTarget().takeDamage((int) (army.getArmyType().getDamage() * army.getOwner().getFearRate()));
        }
    }

    private void checkOtherTargets(Army army) {
        if (army.getTargetCell() != null) {
            for (Army enemy : army.getTargetCell().getArmies()) {
                if (!enemy.getOwner().equals(army.getOwner())) {
                    enemy.takeDamage((int) (army.getArmyType().getDamage() * army.getOwner().getFearRate()));
                    return;
                }
            }
        } else if (army.getTargetBuilding() != null) {
            Building building = army.getTargetBuilding();
            if (checkNeighbor(army, army.getTargetBuilding())) {
                building.takeDamage((int) (army.getArmyType().getDamage() * army.getOwner().getFearRate()));
                if (army.canBurn()) building.startBurning();
                if (army instanceof Soldier) ((Soldier) army).setCanBurnOnce(false);
            }
        }
    }

    private boolean checkNeighbor(Army army, Building building) {
        int changeX = army.getLocation().getX() - building.getLocation().getX();
        int changeY = army.getLocation().getY() - building.getLocation().getY();
        return changeX < 2 && changeY < 2 && changeY > -2 && changeX > -2;
    }


    private void checkStateOfUnits() {
        for (Army army : currentKingdom.getArmies())
            if (army.getTarget() == null || army.getTargetBuilding() == null || army.getTargetCell() == null)
                setTarget(army);
    }

    private void setTarget(Army army) {
        if (army instanceof WarMachine)
            return;
        if (army.getArmyType().getRange() > 0) {
            setArcherTarget(army);
            return;
        }
        for (Army enemy : army.getLocation().getArmies())
            if (!enemy.getOwner().equals(army.getOwner())) {
                army.setTarget(enemy);
                return;
            }
        if (army.getUnitState().equals(UnitState.STANDING))
            return;
        for (int i = 0; i < 4; i++)
            if (getNeighbor(army, i, UnitState.DEFENSIVE.getFireRange()) != null)
                for (Army enemy : getNeighbor(army, i, UnitState.DEFENSIVE.getFireRange()).getArmies())
                    if (!enemy.getOwner().equals(army.getOwner())) {
                        if (army.getArmyType().equals(ArmyType.ASSASSIN) &&
                                !army.getOwner().equals(gameDatabase.getCurrentKingdom())
                                && !((Soldier) army).visibility())
                            continue;
                        army.setTarget(enemy);
                        return;
                    }
        if (army.getUnitState().equals(UnitState.DEFENSIVE))
            return;
        for (int i = 0; i < 8; i++)
            for (Army enemy : getNeighbor(army, i, UnitState.OFFENSIVE.getFireRange()).getArmies())
                if (!enemy.getOwner().equals(army.getOwner())) {
                    if (army.getArmyType().equals(ArmyType.ASSASSIN) &&
                            !army.getOwner().equals(gameDatabase.getCurrentKingdom())
                            && !((Soldier) army).visibility())
                        continue;
                    PathFinder pathFinder = new PathFinder(gameDatabase.getMap(),
                            new Pair<>(army.getLocation().getX(), army.getLocation().getY()), getMovingType(army));
                    if (pathFinder.search(new Pair<>(army.getPath().get(army.getPath().size() - 1).getX(),
                            army.getPath().get(army.getPath().size() - 1).getY())).equals(PathFinder.OutputState.NO_ERRORS))
                        army.setPath(pathFinder.findPath());
                    army.setTarget(enemy);
                    return;
                }
    }

    private boolean checkXY(int x, int y) {
        return x < 0 || x >= gameDatabase.getMap().getSize() ||
                y < 0 || y >= gameDatabase.getMap().getSize();
    }

    private void setArcherTarget(Army army) {
        Cell cell = army.getLocation();
        int radius = army.getArmyType().getRange() - 1;
        if (army.getLocation().getExistingBuilding() != null)
            radius += army.getLocation().getExistingBuilding().getBuildingType().getAttackPoint();
        int cellX = cell.getX();
        int cellY = cell.getY();
        Map map = gameDatabase.getMap();
        for (int y = -1 * radius; y < 2 * radius; y++)
            for (int x = -1 * radius; x < 2 * radius; x++)
                if (!checkXY(cellX + x, cellY + y))
                    for (Army enemy : map.getMap()[cellX + x][cellY + y].getArmies())
                        if (!enemy.getOwner().equals(army.getOwner())) {
                            if (army.getArmyType().equals(ArmyType.ASSASSIN) &&
                                    !army.getOwner().equals(gameDatabase.getCurrentKingdom())
                                    && !((Soldier) army).visibility())
                                continue;
                            army.setTarget(enemy);
                            if (army.canAttack())
                                return;
                        }
        if (!army.canAttack())
            army.setTarget(null);
    }

    private Cell getNeighbor(Army army, int i, int radius) {
        int x = army.getLocation().getX();
        int y = army.getLocation().getY();
        switch (i) {
            case 0:
                return y >= radius ? gameDatabase.getMap().getMap()[x][y - radius] : null;
            case 1:
                return y + radius < gameDatabase.getMap().getSize() ? gameDatabase.getMap().getMap()[x][y + radius] : null;
            case 2:
                return x >= radius ? gameDatabase.getMap().getMap()[x - radius][y] : null;
            case 3:
                return x + radius < gameDatabase.getMap().getSize() ? gameDatabase.getMap().getMap()[x + radius][y] : null;
            case 4:
                return checkInBounds(x - 1, y - 1) ? gameDatabase.getMap().getMap()[x - 1][y - 1] : null;
            case 5:
                return checkInBounds(x - 1, y + 1) ? gameDatabase.getMap().getMap()[x - 1][y + 1] : null;
            case 6:
                return checkInBounds(x + 1, y - 1) ? gameDatabase.getMap().getMap()[x + 1][y - 1] : null;
            default:
                return checkInBounds(x + 1, y + 1) ? gameDatabase.getMap().getMap()[x + 1][y + 1] : null;
        }
    }

    private boolean checkInBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < gameDatabase.getMap().getSize() && y < gameDatabase.getMap().getSize();
    }

    private void moveUnits() {
        for (Army army : currentKingdom.getArmies()) {
            if (army.getPath().size() == 0) continue;
            for (int i = 0; i < army.getArmyType().getSpeed(); i++) {
                if (army.getPath().size() == 0) break;
                if (!army.getPath().get(0).canMove(getDirection(army), army.getLocation(), getMovingType(army))) {
                    PathFinder pathFinder = new PathFinder(gameDatabase.getMap(),
                            new Pair<>(army.getLocation().getX(), army.getLocation().getY()), getMovingType(army));
                    if (pathFinder.search(new Pair<>(army.getPath().get(army.getPath().size() - 1).getX(),
                            army.getPath().get(army.getPath().size() - 1).getY())).equals(PathFinder.OutputState.NO_ERRORS))
                        army.setPath(pathFinder.findPath());
                    else
                        army.setPath(new ArrayList<>());
                }
                army.moveArmy();
                checkAndUseTrap(army.getLocation());
                if (army.getLocation().getTexture().equals(Texture.SHALLOW_WATER)) i++;
                if (army.getPath().size() == 0) handlePatrol(army);
            }
        }
    }

    private void handlePatrol(Army army) {
        if (army.getPatrol() != null) {
            if (army.getPatrol().getObject1().equals(army.getLocation())) {
                PathFinder pathFinder = new PathFinder(gameDatabase.getMap(),
                        new Pair<>(army.getLocation().getX(), army.getLocation().getY()), getMovingType(army));
                if (pathFinder.search(new Pair<>(army.getPatrol().getObject2().getX(),
                        army.getPatrol().getObject2().getY())).equals(PathFinder.OutputState.NO_ERRORS))
                    army.setPath(pathFinder.findPath());
                else
                    army.setPath(new ArrayList<>());
            } else if (army.getPatrol().getObject2().equals(army.getLocation())) {
                PathFinder pathFinder = new PathFinder(gameDatabase.getMap(),
                        new Pair<>(army.getLocation().getX(), army.getLocation().getY()), getMovingType(army));
                if (pathFinder.search(new Pair<>(army.getPatrol().getObject1().getX(),
                        army.getPatrol().getObject1().getY())).equals(PathFinder.OutputState.NO_ERRORS))
                    army.setPath(pathFinder.findPath());
                else
                    army.setPath(new ArrayList<>());
            }
        }
    }


    private Direction getDirection(Army army) {
        Cell currentCell = army.getLocation();
        Cell targetCell = army.getPath().get(0);
        int currentX = currentCell.getX();
        int currentY = currentCell.getY();
        int targetX = targetCell.getX();
        int targetY = targetCell.getY();
        switch (targetX - currentX) {
            case 1:
                return Direction.UP;
            case -1:
                return Direction.DOWN;
            default:
                switch (targetY - currentY) {
                    case 1:
                        return Direction.LEFT;
                    case -1:
                        return Direction.RIGHT;
                }
                break;
        }
        return null;
    }

    private MovingType getMovingType(Army army) {
        if (army.getArmyType().equals(ArmyType.ASSASSIN))
            return MovingType.ASSASSIN;
        if (army instanceof Soldier)
            if (((Soldier) army).getSoldierType().isCanClimbLadder())
                return MovingType.CAN_CLIMB_LADDER;
        return MovingType.CAN_NOT_CLIMB_LADDER;
    }

    private void checkAndUseTrap(Cell cell) {
        if (cell.getExistingBuilding() != null &&
                cell.getExistingBuilding().getBuildingType().equals(BuildingType.KILLING_PIT))
            activeTrap(cell);
    }

    private void activeTrap(Cell cell) {
        cell.setExistingBuilding(null);
        for (Army army : cell.getArmies())
            army.takeDamage(BuildingType.KILLING_PIT.getAttackPoint());
    }

    private void canGateBeCaptured() {
        for (Kingdom kingdom : gameDatabase.getKingdoms()) {
            for (Building building : kingdom.getBuildings()) {
                if (building.getBuildingType().equals(BuildingType.SMALL_STONE_GATEHOUSE) ||
                        building.getBuildingType().equals(BuildingType.LARGE_STONE_GATEHOUSE)) {
                    for (Army e : building.getLocation().getArmies()) {
                        if (!e.getOwner().equals(kingdom)) captureGate(building);
                    }
                }
            }
        }
    }

    private void captureGate(Building building) {
        GateAndStairs gate = ((GateAndStairs) building);
        if (gate.isClosed())
            gate.changeClosedState();
    }

    private void hasKingdomsFallen() {
        out:
        while (true) {
            for (Kingdom kingdom : gameDatabase.getKingdoms()) {
                if (kingdom.getArmies().size() == 0 || kingdom.getBuildings().size() == 0) {
                    removeKingdom(kingdom);
                    continue out;
                }
                if (!kingdom.getArmies().get(0).getArmyType().equals(ArmyType.LORD) ||
                        !kingdom.getBuildings().get(0).getBuildingType().equals(BuildingType.TOWN_HALL)) {
                    removeKingdom(kingdom);
                    continue out;
                }
            }
            break;
        }
    }

    private void removeKingdom(Kingdom kingdom) {
        for (Building building : kingdom.getBuildings())
            building.getLocation().setExistingBuilding(null);
        for (Army army : kingdom.getArmies())
            army.getLocation().removeArmy(army);
        int score = gameDatabase.getTurnPlayed();
        if (kingdom.getOwner().getHighScore() < score)
            kingdom.getOwner().setHighScore(score);
        gameDatabase.removeKingdom(kingdom);
    }
}
