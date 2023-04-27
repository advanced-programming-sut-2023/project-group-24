package model.enums;

public enum Slogan {

    FIRST("I fight for peace"),
    SECOND("I'm not in DANGER , I am the DANGER"),
    THIRD("");
    private final String slogan;

    Slogan(String slogan) {
        this.slogan = slogan;
    }

    public static String getRandomSlogan() {
        return values()[(int) (Math.random() * values().length)].toString();
    }

    @Override
    public String toString() {
        return slogan;
    }
}
