package view.menus.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    //TODO add commands
    //Login menu commands
    FORGOT_PASSWORD("^forgot my password$"),

    //CreateMapMenu commands

    CREATE_MAP("QD"),//ID and size
    SET_TEXTURE("DSAF"),
    SET_TEXTURE_MULTIPLE("DSFA"),
    CLEAR("D"),
    DROP_ROCK("DSF"),
    DROP_TREE("DSFS"),
    DROP_BUILDING("DS"),
    DROP_UNIT("DFDS"),
    CHANGE_KINGDOM("YYF"),
    NEW_KINGDOM("vhj"),



    //Captcha Menu commands
    CHANGE_CODE("^change code$");
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
