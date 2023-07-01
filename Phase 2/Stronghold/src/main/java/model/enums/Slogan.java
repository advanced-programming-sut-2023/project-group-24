package model.enums;

public enum Slogan {

    FIRST("I fight for peace"),
    SECOND("I'm not in DANGER , I am the DANGER"),
    THIRD("U play the game, I live in the game"),
    FORTH("It's not hard to defeat me, It's Impossible"),
    FIFTH("Life has tree parts, gaming, eating and sleeping");
    private final String slogan;

    Slogan(String slogan) {
        this.slogan = slogan;
    }

    public static String getRandomSlogan() {
        return values()[(int) (Math.random() * values().length)].toString();
    }

    public static String[] getAll() {
        String[] output = new String[values().length];
        for (int i = 0; i < output.length; i++) output[i] = values()[i].toString();
        return output;
    }

    @Override
    public String toString() {
        return slogan;
    }
}
