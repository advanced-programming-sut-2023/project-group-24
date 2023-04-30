package model.map;

import model.Kingdom;
import model.enums.Direction;

import java.util.ArrayList;

public class Map {
    private final ArrayList<Kingdom> kingdoms;
    private final int size;
    private final Cell[][] map;
    private final String id;

    public Map(int size, String id) {
        this.size = size;
        this.id = id;
        map = new Cell[size][size];
        kingdoms = new ArrayList<>();
        for (int x = 0; x < size; x++)
            for (int y = 0; y < size; y++)
                map[x][y] = new Cell(x, y);
    }

    public String getId() {
        return id;
    }

    public void addKingdom(Kingdom kingdom) {
        kingdoms.add(kingdom);
    }

    public ArrayList<Kingdom> getKingdoms() {
        return kingdoms;
    }

    public int getSize() {
        return size;
    }

    public Cell[][] getMap() {
        return map;
    }

    public boolean canMove(int x, int y, Direction direction) {
        switch (direction) {
            case DOWN -> x--;
            case LEFT -> y--;
            case UP -> x++;
            case RIGHT -> y++;
        }
        return map[x][y].canMove(direction);
    }
}
