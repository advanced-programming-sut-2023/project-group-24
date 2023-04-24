package view.menus.gamemenus;

import controller.gamecontrollers.ShowMapController;

import java.util.regex.Matcher;

public class ShowMapMenu {
    private final ShowMapController showMapController;

    public ShowMapMenu(ShowMapController showMapController) {
        this.showMapController = showMapController;
    }

    public void run() {
        //TODO get input from GetInputFromUser class and check command
    }

    private void showMap(Matcher matcher) {
        //TODO connect controller check errors and sout map
    }

    private void moveMap(Matcher matcher) {
        //TODO connect controller check errors and sout moved map
    }

    private void showDetails(Matcher matcher) {
        //TODO connect controller and sout datas
    }
}
