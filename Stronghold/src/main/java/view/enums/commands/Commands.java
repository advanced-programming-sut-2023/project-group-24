package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    //Register menu
    CREATE_USER("^user create -u (?<username>\\S+) -p (?<password>\\S+)( )?(?<passwordConfirm>\\S+)? -e " +
            "(?<email>\\S+) -n (?<nickname>\\S+)(?<sloganTag> -s (?<slogan>\\S+))?$"),
    QUESTION_PICK("(?=.* -q (?<questionNumber>\\w+))(?=.* -a (?<answer>(\".*\")|(\\S+)))(?=.* -c " +
            "(?<answerConfirm>((\".*\")|(\\S+))))^question pick( -[qac] ((\".*\")|(\\S+))){3}$"),

    //Login menu
    LOGIN_USER("^(?=.* -u (?<username>(\".*\")|(\\S+)))(?=.* -p (?<password>(\".*\")|(\\S+)))user login( -[up]" +
            " ((\".*\")|(\\S+))){2}(?<stayLoggedIn> --stay-logged-in)?$"),
    FORGOT_PASSWORD("^forgot my password$"),
    ENTER_REGISTER_MENU("^I don't have account$"),

    //Reset password menu
    RESET_PASSWORD("^reset password -u (?<username>(\".*\")|(\\S+))$"),

    //Captcha menu
    CHANGE_CODE("^change code$"),

    //Profile menu
    CHANGE_USERNAME("^profile change -u (?<username>(\".*\")|(\\S+))$"),
    CHANGE_NICKNAME("^profile change -n (?<nickname>(\".*\")|(\\S+))$"),
    CHANGE_PASSWORD("(?=.* -o (?<oldPassword>(\".*\")|(\\S+)))(?=.* -n (?<newPassword>(\".*\")|(\\S+)))^profile" +
            " change password( -[on] ((\".*\")|(\\S+))){2}$"),
    CHANGE_EMAIL("^profile change -e (?<email>(\".*\")|(\\S+))$"),
    CHANGE_SLOGAN("^profile change slogan -s (?<slogan>(\".*\")|(\\S+))$"),
    REMOVE_SLOGAN("^profile remove slogan$"),
    DISPLAY_HIGHSCORE("^profile display highscore$"),
    DISPLAY_RANK("^profile display rank$"),
    DISPLAY_SLOGAN("^profile display slogan$"),
    PROFILE_DISPLAY("^profile display$"),

    //Main menu
    ENTER_PROFILE_MENU("^enter profile menu$"),
    START_GAME("^start game$"), //TODO hichi maloom nist
    ENTER_CREATE_MAP("^enter create map menu$"),
    LOGOUT("^user logout$"),

    //ShowMapMenu commands
    SHOW_MAP("^show map -x (?<x>\\d+) -y (?<y>\\d+)"),
    MOVE_MAP("^map(( up(?<up> \\d+)?)|( down(?<down> \\d+)?))?(( right(?<right> \\d+)?)|( left(?<left> \\d+)?))?$"),
    SHOW_DETAILS("^show details -x (?<x>\\d+) -y (?<y>\\d+)$"),


    //CreateMapMenu commands
    CREATE_MAP("(?=.* -i (?<id>\\S+))(?=.* -s (?<size>\\w+))^create map( -[is] \\S+){2}$"),//ID and size
    SET_TEXTURE("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))(?=.* -t (?<type>(\".*\")|(\\S+)))^settexture( -[xyt] (\".*\")|(\\S+)){3}$"),
    SET_TEXTURE_MULTIPLE("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))(?=.* -t (?<type>(\".*\")|(\\S+)))^settexture( -[xyt] (\".*\")|(\\S+)){3}$"),
    CLEAR("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))^clear( -[xy] \\S+){2}$"),
    DROP_ROCK("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))(?=.* -d (?<direction>\\S+))^droprock( -[xyd] \\S+){3}$"),
    DROP_TREE("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))(?=.* -t (?<type>(\".*\")|(\\S+)))^droptree( -[xyt] (\".*\")|(\\S+)){3}$"),
    DROP_BUILDING("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))(?=.* -t (?<type>(\".*\")|(\\S+)))^dropbuilding( -[xyt] (\".*\")|(\\S+)){3}$"),
    DROP_UNIT("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))(?=.* -t (?<type>(\".*\")|(\\S+)))(?=.* -c (?<count>\\w+))^dropunit( -[xytc] (\".*\")|(\\S+)){4}$"),
    CHANGE_KINGDOM("^change kingdom -c (?<color>\\S+)$"),
    NEW_KINGDOM("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))(?=.* -c (?<color>\\S+))^new kingdom( -[xyc] \\S+){3}$"),
    EXIT("^exit$"),

    //Trade
    OPEN_TRADE_MENU("^open trade menu$"),
    TRADE_REQUEST("^(?=.* -t (?<resourceType>(\\\".*\\\")|(\\S+)))(?=.* -a (?<resourceAmount>(\\\".*\\\")|(\\S+)))(?=.* -p (?<price>(\\\".*\\\")|(\\S+)))(?=.* -m (?<message>(\\\".*\\\")|(\\S+)))trade( -[tapm] ((\\\".*\\\")|(\\S+))){4}$"),
    TRADE_LIST("^trade list$"),
    TRADE_ACCEPT("^(?=.* -i (?<id>(\\\".*\\\")|(\\S+)))(?=.* -m (?<message>(\\\".*\\\")|(\\S+)))trade accept( -[im] ((\\\".*\\\")|(\\S+))){2}$"),
    TRADE_HISTORY("^trade history$"),

    //Valid formats
    VALID_USERNAME("^[\\w_]+$"),
    VALID_EMAIL("^[\\w_.]+@[\\w_.]+.[\\w_.]+$"),
    PASSWORD_SIZE("^\\S{8,}"),
    PASSWORD_CAPITAL("^(?=.*[A-Z]).+$"),
    PASSWORD_NUMBER("^(?=.*\\d).+$"),
    PASSWORD_SMALL_CHAR("^(?=.*[a-z]).+$");

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
