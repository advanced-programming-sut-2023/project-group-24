package utils.enums;

public enum UnitsMode {
    STANDING("standing"),
    DEFENSIVE("defensive"),
    OFFENSIVE("offensive");

    private final String mode;

    UnitsMode(String mode) {
        this.mode = mode;
    }

    public static UnitsMode getMode(String mode) {
        switch (mode) {
            case "standing" -> {
                return STANDING;
            }
            case "defensive" -> {
                return DEFENSIVE;
            }
            case "offensive" -> {
                return OFFENSIVE;
            }
            default -> {
                return null;
            }
        }
    }
}
