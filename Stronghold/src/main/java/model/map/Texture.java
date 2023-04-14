package model.map;

public enum Texture {
    Ground(true, true, Color.YELLOW);
    private boolean canPass;
    private boolean canBuild;
    private Color color;

    Texture(boolean canPass, boolean canBuild, Color color) {
        this.canPass = canPass;
        this.canBuild = canBuild;
        this.color = color;
    }
}