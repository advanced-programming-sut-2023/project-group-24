package controller.functionalcontrollers;

class PathFinderCellDetails {
    static final int FLT_MAX = 100000000;
    private int parentX;
    private int parentY;
    private int f;
    private int g;

    PathFinderCellDetails() {
        this.parentX = -1;
        this.parentY = -1;
        this.f = FLT_MAX;
        this.g = FLT_MAX;
    }

    int getParentX() {
        return parentX;
    }

    int getParentY() {
        return parentY;
    }

    int getF() {
        return f;
    }

    int getG() {
        return g;
    }

    void setAll(int parentX, int parentY, int f, int g) {
        this.parentX = parentX;
        this.parentY = parentY;
        this.f = f;
        this.g = g;
    }
}
