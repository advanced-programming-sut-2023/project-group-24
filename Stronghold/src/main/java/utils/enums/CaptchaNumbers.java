package utils.enums;

public enum CaptchaNumbers {
    ZERO("""
              ___ \s
             / _ \\\s
            | | | |
            | |_| |
             \\___/\s"""),
    ONE("""
             _\s
            / |
            | |
            | |
            |_|"""),
    TWO("""
             ____ \s
            |___ \\\s
              __) |
             / __/\s
            |_____|"""),
    THREE("""
             _____\s
            |___ /\s
              |_ \\\s
             ___) |
            |____/\s"""),
    FOUR("""
             _  _  \s
            | || | \s
            | || |_\s
            |__   _|
               |_| \s"""),
    FIVE("""
             ____ \s
            | ___|\s
            |___ \\\s
             ___) |
            |____/\s"""),
    SIX("""
              __  \s
             / /_ \s
            | '_ \\\s
            | (_) |
             \\___/\s"""),
    SEVEN("""
             _____\s
            |___  |
               / /\s
              / / \s
             /_/  \s"""),
    EIGHT("""
              ___ \s
             ( _ )\s
             / _ \\\s
            | (_) |
             \\___/\s"""),
    NINE("""
              ___ \s
             / _ \\\s
            | (_) |
             \\__, |
               /_/\s"""),
    EMPTY("""





            """);

    private final String asciiArt;

    CaptchaNumbers(String asciiArt) {
        this.asciiArt = asciiArt;
    }

    public String getAsciiArt() {
        return asciiArt;
    }
}
