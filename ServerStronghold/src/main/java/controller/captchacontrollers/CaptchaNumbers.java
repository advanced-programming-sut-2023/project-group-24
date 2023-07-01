package controller.captchacontrollers;

public enum CaptchaNumbers {
    ZERO("  ___  \n" +
            " / _ \\ \n" +
            "| | | |\n" +
            "| |_| |\n" +
            " \\___/ "),
    ONE(" _ \n" +
            "/ |\n" +
            "| |\n" +
            "| |\n" +
            "|_|"),
    TWO(" ____  \n" +
            "|___ \\ \n" +
            "  __) |\n" +
            " / __/ \n" +
            "|_____|"),
    THREE(" _____ \n" +
            "|___ / \n" +
            "  |_ \\ \n" +
            " ___) |\n" +
            "|____/ "),
    FOUR(" _  _   \n" +
            "| || |  \n" +
            "| || |_ \n" +
            "|__   _|\n" +
            "   |_|  "),
    FIVE(" ____  \n" +
            "| ___| \n" +
            "|___ \\ \n" +
            " ___) |\n" +
            "|____/ "),
    SIX("  __   \n" +
            " / /_  \n" +
            "| '_ \\ \n" +
            "| (_) |\n" +
            " \\___/ "),
    SEVEN(" _____ \n" +
            "|___  |\n" +
            "   / / \n" +
            "  / /  \n" +
            " /_/   "),
    EIGHT("  ___  \n" +
            " ( _ ) \n" +
            " / _ \\ \n" +
            "| (_) |\n" +
            " \\___/ "),
    NINE("  ___  \n" +
            " / _ \\ \n" +
            "| (_) |\n" +
            " \\__, |\n" +
            "   /_/ "),
    EMPTY("\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n");

    private final String asciiArt;

    CaptchaNumbers(String asciiArt) {
        this.asciiArt = asciiArt;
    }

    public String getAsciiArt() {
        return asciiArt;
    }
}