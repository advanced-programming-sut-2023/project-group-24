package view.enums;

public enum Slogan {

    FIRST("I fight for peace");
    private final String slogan;

    Slogan(String slogan) {
        this.slogan = slogan;
    }

    public static String getRandomSlogan() {
        switch ((int) (Math.random() % 10)) {
            case 1:
                return FIRST.slogan;
            default:
                return null;
        }
    }
}
