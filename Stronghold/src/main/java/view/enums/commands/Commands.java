package view.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    //Register menu
    CREATE_USER("(?=.* -u (?<username>(\"[^\"]*\")|(\\S*)))(?=.* -e (?<email>(\"[^\"]*\")|(\\S*)))" +
            "(?=.* -n (?<nickname>(\"[^\"]*\")|(\\S*)))(?=.* -s (?<slogan>(\"[^\"]*\")|(\\S*)))?" +
            "(?=.* -p (?<password>(\"[^\"]*\")|(\\S*)))(?=.* -c (?<passwordConfirm>(\"[^\"]*\")|(\\S*)))" +
            "?^user create( -[unpces] ((\"[^\"]*\")|(\\S*))){4,6}$"),
    QUESTION_PICK("(?=.* -q (?<questionNumber>\\d*))(?=.* -a (?<answer>(\"[^\"]*\")|(\\S*)))" +
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
    SHOW_MAP("(?=.* -x (?<x>\\d*))(?=.* -y (?<y>\\d*))^show map( -[xy] \\d*){2}"),
    MOVE_MAP("^map(?<direction>( ((up)|(down)|(left)|(right))\\d*)+)$"),
    SHOW_DETAILS("(?=.* -x (?<x>\\d*))(?=.* -y (?<y>\\d*))^show details( -[xy] \\d*){2}$"),


    //CreateMapMenu commands
    CREATE_MAP("(?=.* -i (?<id>\\S*))(?=.* -s (?<size>\\d*))^create map( -[is] \\S*){2}$"),
    SET_TEXTURE("(?=.* -x (?<x>\\d*))(?=.* -y (?<y>\\d*))(?=.* -t (?<texture>(\"[^\"]*\")|(\\S*)))" +
            "^settexture( -[xyt] ((\"[^\"]*\")|(\\S*))){3}$"),
    SET_TEXTURE_MULTIPLE("(?=.* -x (?<x>\\d*))(?=.* -y (?<y>\\d*))(?=.* -t (?<texture>(\"[^\"]*\")|(\\S*)))" +
            "^settexture( -[xyt] ((\"[^\"]*\")|(\\S*))){3}$"),
    CLEAR("(?=.* -x (?<x>\\d*))(?=.* -y (?<y>\\d*))^clear( -[xy] \\d*){2}$"),
    DROP_ROCK("(?=.* -x (?<x>\\d*))(?=.* -y (?<y>\\d*))(?=.* -d (?<direction>\\S*))^droprock( -[xyd] \\S*){3}$"),
    DROP_TREE("(?=.* -x (?<x>\\d*))(?=.* -y (?<y>\\d*))(?=.* -t (?<type>(\"[^\"]*\")|(\\S*)))" +
            "^droptree( -[xyt] ((\"[^\"]*\")|(\\S*))){3}$"),
    DROP_BUILDING("(?=.* -x (?<x>\\d*))(?=.* -y (?<y>\\d*))(?=.* -t (?<type>(\"[^\"]*\")|(\\d*)))" +
            "^dropbuilding( -[xyt] ((\"[^\"]*\")|(\\d*))){3}$"),
    DROP_UNIT("(?=.* -x (?<x>\\d*))(?=.* -y (?<y>\\d*))(?=.* -t (?<type>(\"[^\"]*\")|(\\S*)))" +
            "(?=.* -c (?<count>\\d*))^dropunit( -[xytc] ((\"[^\"]*\")|(\\S*))){4}$"),
    CHANGE_KINGDOM("^change kingdom -c (?<color>\\S+)$"),
    NEW_KINGDOM("(?=.* -x (?<x>\\d*))(?=.* -y (?<y>\\d*))(?=.* -c (?<color>\\S*))^new kingdom( -[xyc] \\S*){3}$"),

    //Shop menu commands
    SHOW_PRICE_LIST("^show price list$"),
    BUY_ITEM("(?=.* -i (?<itemName>(\"[^\"]*\")|(\\S*)))(?=.* -a (?<itemAmount>\\d*))" +
            "^buy( -[ia] ((\"[^\"]*\")|(\\S*))){2}$"),
    SELL_ITEM("(?=.* -i (?<itemName>(\"[^\"]*\")|(\\S*)))(?=.* -a (?<itemAmount>\\d*))" +
            "^sell( -[ia] ((\"[^\"]*\")|(\\S*))){2}$"),

    //Game menu commands
    OPEN_TRADE_MENU("^open trade menu$"),
    OPEN_SHOW_MAP_MENU("^open show map menu$"),
    TURN_PLAYED("^how many turn played$"),
    ROUND_PLAYED("^how many round played$"),
    NEXT_TURN("^next turn$"),
    //Units
    SELECT_UNIT("(?=.* -x (?<x>\\d*))(?=.* -y (?<y>\\d*))(?=.* -t (?<type>(\"[^\"]*\")|(\\S*)))?" +
            "^select unit( -[xyt] ((\"[^\"]*\")|(\\S*))){2,3}$"),
    MOVE_UNIT("(?=.* -x (?<x>\\d*))(?=.* -y (?<y>\\d*))^move unit to( -[xy] \\d*){2}$"),
    PATROL_UNIT("(?=.* -x (?<x>\\d*))(?=.* -y (?<y>\\d*))" +
            "^patrol unit( -[xy] \\d*){2}$"),
    STOP_PATROL("^stop patrol$"),
    SET_STATE("^set (?<state>\\S*)$"),
    ATTACK("^attack -e (?<enemyX>\\d*) (?<enemyY>\\d*)$"),
    ATTACK_ARCHER("(?=.* -x (?<x>\\d*))(?=.* -y (?<y>\\d*))^attack( -[xy] \\d*){2}$"),
    POUR_OIL("^pour oil -d (?<direction>\\S*)$"),
    DIG_TUNNEL("^dig tunnel$"),
    BUILD("^build -q (?<equipmentType>(\"[^\"]*\")|(\\S*))$"),
    DISBAND("^disband unit$"),
    DIG_MOAT("^dig moat -d (?<direction>\\S*)$"),
    FILL_MOAT("fill moat -d (?<direction>\\S*)}$"),
    ATTACK_BUILDING("(?=.* -x (?<x>\\d*))(?=.* -y (?<y>\\d*))^attackbuilding( -[xy] \\d*){2}$"),
    STOP("^stop$"),
    //Kingdom
    SHOW_POPULARITY_FACTORS("^show popularity factors$"),
    SHOW_POPULARITY("^show popularity"),
    SHOW_FOOD_LIST("^show food list$"),
    FOOD_RATE("^food rate -r (?<rateNumber>-?\\d*)$"),
    FOOD_RATE_SHOW("^food rate show$"),
    TAX_RATE("^tax rate -r (?<rateNumber>-?\\d*)$"),
    TAX_RATE_SHOW("^tax rate show$"),
    //Building
    DROP_BUILDING_GAME("(?=.* -x (?<x>\\d*))(?=.* -y (?<y>\\d*))(?=.* -t (?<type>(\"[^\"]*\")|(\\S*)))" +
            "^dropbuilding( -[xyt] ((\\S*)|(\"[^\"]*\"))){3}$"),
    SELECT_BUILDING("(?=.* -x (?<x>\\d*))(?=.* -y (?<y>\\d*))^select building( -[xy] \\d*){2}$"),
    CREATE_UNIT("(?=.* -c (?<count>\\d*))(?=.* -t (?<type>(\"[^\"]*\")|(\\S*)))" +
            "^createunit( -[tc] ((\"[^\"]*\")|(\\S*))){2}$"),
    CHANGE_GATE_STATE("^change gate state$"),
    OPEN_DOG_CAGE("^open dog cage$"),
    REMOVE_MOAT("(?=.* -x (?<x>\\d*))(?=.* -y (?<y>\\d*))^remove moat( -[xy] \\d*){2}$"),
    SHOW_BUILDING_DETAILS("^show building details$"),
    PRODUCE_LEATHER("^produce leather$"),
    PRODUCE_ITEM("^produce item -n (?<name>(\\S*)|(\"[^\"]*\"))$"),
    REPAIR("^repair$"),

    //Common commands
    EXIT("^exit$"),
    SHOW_CURRENT_MENU("^show current menu$"),

    //Trade
    TRADE_REQUEST("(?=.* -t (?<resourceType>(\"[^\"]*\")|(\\S*)))(?=.* -a (?<resourceAmount>(\"[^\"]*\")|(\\S*)))" +
            "(?=.* -p (?<price>(\"[^\"]*\")|(\\S*)))(?=.* -m (?<message>(\"[^\"]*\")|(\\S*)))^trade( -[tapm] ((\"[^\"]*\")|(\\S*))){4}$"),
    TRADE_LIST("^trade list$"),
    TRADE_ACCEPT("(?=.* -i (?<id>(\"[^\"]*\")|(\\S*)))(?=.* -m (?<message>(\"[^\"]*\")|(\\S*)))" +
            "^trade accept( -[im] ((\"[^\"]*\")|(\\S*))){2}$"),
    TRADE_HISTORY("^trade history$"),

    //Valid formats
    VALID_USERNAME("^[\\w_]+$"),
    VALID_EMAIL("^[\\w_.]+@[\\w_.]+.[\\w_.]+$"),
    PASSWORD_SIZE("^\\S{8,}"),
    PASSWORD_CAPITAL("^(?=.*[A-Z]).+$"),
    PASSWORD_NUMBER("^(?=.*\\d).+$"),
    PASSWORD_SMALL_CHAR("^(?=.*[a-z]).+$"),
    PASSWORD_SPECIFIC_CHAR("^.*[`~!@#\\$%\\^&\\*\\(\\)_\\-=\\+\";:\\?\\/\\.<,>]+.*$");


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