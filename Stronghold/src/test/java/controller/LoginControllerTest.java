package controller;

import controller.functionalcontrollers.Pair;
import controller.nongame.LoginController;
import model.databases.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import view.enums.messages.LoginMenuMessages;

class LoginControllerTest {

    @Test
    void loginUser() {
        Database database = new Database();
        database.addUser("pouria", MainController.getSHA256("Pouriagh1@"), "pouria", "Say my name", "p@g.c", new Pair<>(1, "p"));
        LoginController loginMenuController = new LoginController(database);
        Assertions.assertEquals(LoginMenuMessages.USER_NOT_FOUND, loginMenuController.loginUser("aref", "Password", false));
        Assertions.assertEquals(LoginMenuMessages.INCORRECT_PASSWORD, loginMenuController.loginUser("pouria", "Password", false));
    }
}