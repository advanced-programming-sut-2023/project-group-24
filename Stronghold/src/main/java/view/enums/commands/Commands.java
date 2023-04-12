package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    //TODO add commands
    //Login menu commands
    FORGOT_PASSWORD("^forgot my password$");
    private final String regex;

    Commands(String regex) {
        this.regex = regex;
    }

    static public Matcher getMatcher(String input, Commands commands) {
        Pattern pattern = Pattern.compile(commands.regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) return matcher;
        else return null;
    }
}
