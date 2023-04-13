package view.enums;

public enum Slogan {

    FIRST("I fight for peace");
    private final String slogan;

    Slogan(String slogan) {
        this.slogan = slogan;
    }

    public static String getRandomSlogan() {
        return values()[(int) (Math.random() * values().length)].toString();
    }
}
