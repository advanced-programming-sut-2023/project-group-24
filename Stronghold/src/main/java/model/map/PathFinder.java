package model.map;

import model.enums.Direction;
import utils.Pair;

import java.util.*;

public class PathFinder {
    private Map map;
    private PathFinderCellDetails[][] cellDetails;
    private Pair<Integer, Integer> dest;
    private Pair<Integer, Integer> src;
    private Stack<Pair<Integer, Integer>> path;

    public PathFinder(Map map, Pair<Integer, Integer> startingPoint, Pair<Integer, Integer> destination) {
        this.map = map;
        this.src = startingPoint;
        this.dest = destination;
        this.cellDetails = new PathFinderCellDetails[map.getSize()][map.getSize()];
        setupCellDetails();
    }

    public void setDestination(Pair<Integer, Integer> destination) {
        this.dest = destination;
    }

    public OutputState search() {
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
        return OutputState.CANNOT_REACH_DESTINATION;
    }

    private boolean moveToDirection(boolean[][] closedList, ArrayList<Pair<Integer, Pair<Integer, Integer>>> openList,
                                    Pair<Integer, Integer> initialCoordinates, Pair<Integer, Integer> newCoordinates) {
        int i = initialCoordinates.getObject1(), j = initialCoordinates.getObject2();
        int newI = newCoordinates.getObject1(), newJ = newCoordinates.getObject2();
        if (isValid(newI, newJ)) {
            if (isDestination(newI, newJ)) {
                cellDetails[newI][newJ].setAll(i, j, 0, 0, 0);
                return true;
            }
            else if (!closedList[newI][newJ] && canMoveTo(i, j, newI, newJ)) {
                int gNew = cellDetails[i][j].getG() + 1;
                int hNew = calculateHValue(newI, newJ);
                int fNew = gNew + hNew;
                if (cellDetails[newI][newJ].getF() == PathFinderCellDetails.FLT_MAX
                        || cellDetails[newI][newJ].getF() > fNew) {
                    openList.add(new Pair<>(fNew, new Pair<>(newI, newJ)));
                    cellDetails[newI][newJ].setAll(i, j, fNew, gNew, hNew);
                }
            }
        }
        return false;
    }


    public Vector<Cell> findPath() {
        tracePath();
        if (path == null || path.size() == 0) return null;
        Vector<Cell> output = new Vector<>();
        while (path.size() > 0) {
            Pair<Integer, Integer> cellCoordinates = path.pop();
            output.add(map.getMap()[cellCoordinates.getObject1()][cellCoordinates.getObject2()]);
        }

        return output;
    }

    private OutputState checkForErrors() {
        if (!isValid(src.getObject1(), src.getObject2())) return OutputState.INVALID_STARTING_POINT;

        if (!isValid(dest.getObject1(), dest.getObject2())) return OutputState.INVALID_DESTINATION;

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
        cellDetails[i][j].setF(0);
        cellDetails[i][j].setG(0);
        cellDetails[i][j].setH(0);
        cellDetails[i][j].setParentX(i);
        cellDetails[i][j].setParentY(j);
    }

    private boolean isBlocked(int x, int y) {
        return !map.getMap()[x][y].canDropUnit();
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < map.getSize() && col >= 0 && col < map.getSize();
    }

    private boolean canMoveTo(int x1, int y1, int x2, int y2) {
        if (x1 - 1 == x2 && y1 == y2)
            return map.getMap()[x1][x2].canMove(Direction.UP);
        if (x1 + 1 == x2 && y1 == y2)
            return map.getMap()[x1][x2].canMove(Direction.DOWN);
        if (x1 == x2 && y1 - 1 == y2)
            return map.getMap()[x1][x2].canMove(Direction.LEFT);
        if (x1 == x2 && y1 + 1 == y2)
            return map.getMap()[x1][x2].canMove(Direction.RIGHT);
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
        INVALID_STARTING_POINT,
        INVALID_DESTINATION,
        ALREADY_AT_DESTINATION,
        BLOCKED,
        CANNOT_REACH_DESTINATION,
        NO_ERRORS
    }
}
