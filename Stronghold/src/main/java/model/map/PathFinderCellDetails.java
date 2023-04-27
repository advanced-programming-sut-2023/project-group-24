package model.map;

public class PathFinderCellDetails {
    static final int FLT_MAX = 100000000;
    private int parentX;
    private int parentY;
    private int f;
    private int g;
    private int h;

    public PathFinderCellDetails(int parentX, int parentY, int f, int g, int h) {
        this.parentX = parentX;
        this.parentY = parentY;
        this.f = f;
        this.g = g;
        this.h = h;
    }

    public PathFinderCellDetails() {
        this.parentX = -1;
        this.parentY = -1;
        this.f = FLT_MAX;
        this.g = FLT_MAX;
        this.h = FLT_MAX;
    }

    public int getParentX() {
        return parentX;
    }

    public int getParentY() {
        return parentY;
    }

    public int getF() {
        return f;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public void setAll(int parentX, int parentY, int f, int g, int h) {
        this.parentX = parentX;
        this.parentY = parentY;
        this.f = f;
        this.g = g;
        this.h = h;
    }

    public void setParentX(int parentX) {
        this.parentX = parentX;
    }

    public void setParentY(int parentY) {
        this.parentY = parentY;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setH(int h) {
        this.h = h;
    }
}
