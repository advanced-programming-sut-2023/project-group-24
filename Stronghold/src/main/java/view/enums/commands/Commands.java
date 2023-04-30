package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    //Register menu
    CREATE_USER("(?=.* -u (?<username>(\"[^\"]*\")|(\\S*)))(?=.* -e (?<email>(\"[^\"]*\")|(\\S*)))" +
            "(?=.* -n (?<nickname>(\"[^\"]*\")|(\\S*)))(?=.* -s (?<slogan>(\"[^\"]*\")|(\\S*)))?" +
            "(?=.* -p (?<password>(\"[^\"]*\")|(\\S*)))(?=.* -c (?<passwordConfirm>(\"[^\"]*\")|(\\S*)))" +
            "?^user create( -[unpces] ((\"[^\"]*\")|(\\S*))){4,6}$"),
    QUESTION_PICK("(?=.* -q (?<questionNumber>\\w+))(?=.* -a (?<answer>(\"[^\"]*\")|(\\S*)))" +
            "(?=.* -c (?<answerConfirm>((\"[^\"]*\")|(\\S*))))^question pick( -[qac] ((\"[^\"]*\")|(\\S*))){3}$"),

    //Login menu
    LOGIN_USER("(?=.* -u (?<username>(\"[^\"]*\")|(\\S*)))(?=.* -p (?<password>(\"[^\"]*\")|(\\S*)))" +
            "^user login( -[up] ((\"[^\"]*\")|(\\S*))){2}(?<stayLoggedIn> --stay-logged-in)?$"),
    FORGOT_PASSWORD("^forgot my password$"),
    ENTER_REGISTER_MENU("^I don't have account$"),

    //Reset password menu
    RESET_PASSWORD("^reset password -u (?<username>(\"[^\"]*\")|(\\S*))$"),

    //Captcha menu
    CHANGE_CODE("^change code$"),

    //Profile menu
    CHANGE_USERNAME("^profile change -u (?<username>(\"[^\"]*\")|(\\S*))$"),
    CHANGE_NICKNAME("^profile change -n (?<nickname>(\"[^\"]*\")|(\\S*))$"),
    CHANGE_PASSWORD("(?=.* -o (?<oldPassword>(\"[^\"]*\")|(\\S*)))(?=.* -n (?<newPassword>(\"[^\"]*\")|(\\S*)))" +
            "^profile change password( -[on] ((\"[^\"]*\")|(\\S*))){2}$"),
    CHANGE_EMAIL("^profile change -e (?<email>(\"[^\"]*\")|(\\S*))$"),
    CHANGE_SLOGAN("^profile change slogan -s (?<slogan>(\"[^\"]*\")|(\\S*))$"),
    REMOVE_SLOGAN("^profile remove slogan$"),
    DISPLAY_HIGHSCORE("^profile display highscore$"),
    DISPLAY_RANK("^profile display rank$"),
    DISPLAY_SLOGAN("^profile display slogan$"),
    PROFILE_DISPLAY("^profile display$"),

    //Main menu
    ENTER_PROFILE_MENU("^enter profile menu$"),
    START_GAME("^start game$"),
    ENTER_CREATE_MAP("^enter create map menu$"),
    LOGOUT("^user logout$"),

    //ShowMapMenu commands
    SHOW_MAP("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))^show map( -[xy] \\d+)[2}"),
    MOVE_MAP("^map(( up(?<up> \\d+)?)|( down(?<down> \\d+)?))?(( right(?<right> \\d+)?)|( left(?<left> \\d+)?))?$"),
    SHOW_DETAILS("(?=.* -x (?<x>\\d+))(?=.* -y (?<y>\\d+))^show details( -[xy] \\d+){2}$"),


    //CreateMapMenu commands
    CREATE_MAP("(?=.* -i (?<id>\\S+))(?=.* -s (?<size>\\w+))^create map( -[is] \\S+){2}$"),
    SET_TEXTURE("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))(?=.* -t (?<type>(\"[^\"]*\")|(\\S+)))" +
            "^settexture( -[xyt] (\"[^\"]*\")|(\\S+)){3}$"),
    SET_TEXTURE_MULTIPLE("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))(?=.* -t (?<type>(\"[^\"]*\")|(\\S+)))" +
            "^settexture( -[xyt] (\"[^\"]*\")|(\\S+)){3}$"),
    CLEAR("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))^clear( -[xy] \\S+){2}$"),
    DROP_ROCK("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))(?=.* -d (?<direction>\\S+))^droprock( -[xyd] \\S+){3}$"),
    DROP_TREE("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))(?=.* -t (?<type>(\"[^\"]*\")|(\\S+)))" +
            "^droptree( -[xyt] (\"[^\"]*\")|(\\S+)){3}$"),
    DROP_BUILDING("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))(?=.* -t (?<type>(\"[^\"]*\")|(\\S+)))" +
            "^dropbuilding( -[xyt] (\"[^\"]*\")|(\\S+)){3}$"),
    DROP_UNIT("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))(?=.* -t (?<type>(\"[^\"]*\")|(\\S+)))" +
            "(?=.* -c (?<count>\\w+))^dropunit( -[xytc] (\"[^\"]*\")|(\\S+)){4}$"),
    CHANGE_KINGDOM("^change kingdom -c (?<color>\\S+)$"),
    NEW_KINGDOM("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))(?=.* -c (?<color>\\S+))^new kingdom( -[xyc] \\S+){3}$"),

    //Shop menu commands
    SHOW_PRICE_LIST("^show price list$"),
    BUY_ITEM("(?=.* -i (?<itemName>(\"[^\"]*\")|(\\S+))(?=.* -a (?<itemAmount>\\w+))^buy( -[ia] ((\"[^\"]*\")|(\\S+))){2}$"),
    SELL_ITEM("(?=.* -i (?<itemName>(\"[^\"]*\")|(\\S+))(?=.* -a (?<itemAmount>\\w+))^sell( -[ia] ((\"[^\"]*\")|(\\S+))){2}$"),

    //Game menu commands
    OPEN_TRADE_MENU("^open trade menu$"),
    SELECT_UNIT("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))(?=.* -t (?<type>(\"[^\"]*\")|(\\S+)))" +
            "^select unit( -[xyt] ((\"[^\"]*\")|(\\S+))){2,2}$"),
    MOVE_UNIT("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))^move unit to( -[xy] \\w+){2}$"),
    PATROL_UNIT("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))" +
            "^patrol unit( -[xy] \\w+){2}$"),
    SET_STATE("^set (?<state>\\S+)$"),
    ATTACK("^attack -e (?<enemyX>\\w+) (?<enemyY>\\w+)$"),
    ATTACK_ARCHER("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))^attack( -[xy] \\w+){2}$"),
    POUR_OIL("^pour oil -d (?<direction>\\S+)$"),
    DIG_TUNNEL("(?=.* -x (?<x>\\w+))(?=.* -y (?<y>\\w+))^dig tunnel( -[xy] \\w+){2}$"),
    BUILD("^build -q (?<equipmentType>(\"[^\"]*\")|(\\S+))$"),
    DISBAND("^disband unit$"),

    //Common commands
    EXIT("^exit$"),

    //Trade
    TRADE_REQUEST("(?=.* -t (?<resourceType>(\".*\")|(\\S+)))(?=.* -a (?<resourceAmount>(\".*\")|(\\S+)))" +
            "(?=.* -p (?<price>(\".*\")|(\\S+)))(?=.* -m (?<message>(\".*\")|(\\S+)))^trade( -[tapm] ((\".*\")|(\\S*))){4}$"),
    TRADE_LIST("^trade list$"),
    TRADE_ACCEPT("(?=.* -i (?<id>(\".*\")|(\\S+)))(?=.* -m (?<message>(\".*\")|(\\S+)))^trade accept( -[im] ((\".*\")|(\\S+))){2}$"),
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
