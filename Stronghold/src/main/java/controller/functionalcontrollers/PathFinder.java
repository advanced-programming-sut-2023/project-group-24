package controller.functionalcontrollers;

import model.enums.Direction;
import model.enums.MovingType;
import model.map.Cell;
import model.map.Map;
import utils.Pair;

import java.util.ArrayList;
import java.util.Stack;

/*
 * HOW TO USE THIS CLASS (for our project's future use):
 * STEP 1: use the constructor and create an object for the map and the starting point
 * STEP 2: use the search method so the object finds the best path from the starting point to the destination
 *       this method will return if there was an error or, it was successful
 * STEP 3: use the findPath method to get the path as a meaningful ArrayList, rather than some random data in the object
 * */

public class PathFinder {
    private final Map map;
    private final PathFinderCellDetails[][] cellDetails;
    private final Pair<Integer, Integer> src;
    private Pair<Integer, Integer> dest;
    private Stack<Pair<Integer, Integer>> path;
    private final MovingType movingType;

    public PathFinder(Map map, Pair<Integer, Integer> startingPoint, MovingType movingType) {
        this.map = map;
        this.src = startingPoint;
        this.cellDetails = new PathFinderCellDetails[map.getSize()][map.getSize()];
        this.movingType = movingType;
        setupCellDetails();
    }

    public OutputState search(Pair<Integer, Integer> destination) {
        this.dest = destination;
        if (checkForErrors() != OutputState.NO_ERRORS) return checkForErrors();

        boolean[][] closedList = new boolean[map.getSize()][map.getSize()];
        ArrayList<Pair<Integer, Pair<Integer, Integer>>> openList = new ArrayList<>();
        openList.add(new Pair<>(0, new Pair<>(src.getObject1(), src.getObject2())));

        while (!openList.isEmpty()) {
            Pair<Integer, Pair<Integer, Integer>> pair = openList.remove(0);
            int pairX = pair.getObject2().getObject1();
            int pairY = pair.getObject2().getObject2();
            closedList[pairX][pairY] = true;

            if (moveToDirection(closedList, openList, new Pair<>(pairX, pairY), new Pair<>(pairX - 1, pairY))
                    || moveToDirection(closedList, openList, new Pair<>(pairX, pairY), new Pair<>(pairX + 1, pairY))
                    || moveToDirection(closedList, openList, new Pair<>(pairX, pairY), new Pair<>(pairX, pairY - 1))
                    || moveToDirection(closedList, openList, new Pair<>(pairX, pairY), new Pair<>(pairX, pairY + 1)))
                return OutputState.NO_ERRORS;
        }
        return OutputState.BLOCKED;
    }

    private boolean moveToDirection(boolean[][] closedList, ArrayList<Pair<Integer, Pair<Integer, Integer>>> openList,
                                    Pair<Integer, Integer> initialCoordinates, Pair<Integer, Integer> newCoordinates) {
        int i = initialCoordinates.getObject1(), j = initialCoordinates.getObject2();
        int newI = newCoordinates.getObject1(), newJ = newCoordinates.getObject2();
        if (isValid(newI, newJ)) {
            if (isDestination(newI, newJ)) {
                cellDetails[newI][newJ].setAll(i, j, 0, 0);
                return true;
            } else if (!closedList[newI][newJ] && canMoveTo(i, j, newI, newJ)) {
                int gNew = cellDetails[i][j].getG() + 1;
                int fNew = gNew + calculateHValue(newI, newJ);
                if (cellDetails[newI][newJ].getF() == PathFinderCellDetails.FLT_MAX
                        || cellDetails[newI][newJ].getF() > fNew) {
                    openList.add(new Pair<>(fNew, new Pair<>(newI, newJ)));
                    cellDetails[newI][newJ].setAll(i, j, fNew, gNew);
                }
            }
        }
        return false;
    }


    public ArrayList<Cell> findPath() {
        tracePath();
        if (path == null || path.size() == 0) return null;
        ArrayList<Cell> output = new ArrayList<>();
        while (path.size() > 0) {
            Pair<Integer, Integer> cellCoordinates = path.pop();
            output.add(map.getMap()[cellCoordinates.getObject1()][cellCoordinates.getObject2()]);
        }
        output.remove(0);

        return output;
    }

    private OutputState checkForErrors() {
        if (isBlocked(src.getObject1(), src.getObject2()) || isBlocked(dest.getObject1(), dest.getObject2()))
            return OutputState.BLOCKED;

        if (isDestination(src.getObject1(), src.getObject2())) return OutputState.ALREADY_AT_DESTINATION;

        return OutputState.NO_ERRORS;
    }

    private void setupCellDetails() {
        for (int i = 0; i < cellDetails.length; i++) {
            for (int j = 0; j < cellDetails[i].length; j++) {
                cellDetails[i][j] = new PathFinderCellDetails();
            }
        }
        int i = src.getObject1(), j = src.getObject2();
        cellDetails[i][j].setAll(i, j, 0, 0);
    }

    private boolean isBlocked(int x, int y) {
        return !map.getMap()[x][y].canDropUnit();
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < map.getSize() && col >= 0 && col < map.getSize();
    }

    private boolean canMoveTo(int x1, int y1, int x2, int y2) {
        if (x1 - 1 == x2 && y1 == y2)
            return map.getMap()[x2][y2].canMove(Direction.UP, map.getMap()[x1][y1], movingType);
        if (x1 + 1 == x2 && y1 == y2)
            return map.getMap()[x2][y2].canMove(Direction.DOWN, map.getMap()[x1][y1], movingType);
        if (x1 == x2 && y1 - 1 == y2)
            return map.getMap()[x2][y2].canMove(Direction.LEFT, map.getMap()[x1][y1], movingType);
        if (x1 == x2 && y1 + 1 == y2)
            return map.getMap()[x2][y2].canMove(Direction.RIGHT, map.getMap()[x1][y1], movingType);
        return false;
    }

    private boolean isDestination(int row, int col) {
        return dest.getObject1() == row && dest.getObject2() == col;
    }

    private int calculateHValue(int row, int col) {
        return Math.abs(dest.getObject1() - row) + Math.abs(dest.getObject2() - col);
    }

    private void tracePath() {
        int row = dest.getObject1();
        int col = dest.getObject2();
        path = new Stack<>();

        while (!(cellDetails[row][col].getParentX() == row && cellDetails[row][col].getParentY() == col)) {
            path.push(new Pair<>(row, col));
            int temp_row = cellDetails[row][col].getParentX();
            int temp_col = cellDetails[row][col].getParentY();
            row = temp_row;
            col = temp_col;
        }

    }

    public enum OutputState {
        ALREADY_AT_DESTINATION,
        BLOCKED,
        NO_ERRORS
    }
}
