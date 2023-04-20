package model.map;

import model.Kingdom;

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

    public void addKingdom(Color color) {
        kingdoms.add(new Kingdom(color));
    }

    public ArrayList<Kingdom> getKingdoms() {
        return kingdoms;
    }
}
