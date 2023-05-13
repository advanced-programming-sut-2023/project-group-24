package controller;

import controller.functionalcontrollers.Pair;
import model.databases.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import view.enums.messages.LoginMenuMessages;

class LoginMenuControllerTest {

    @Test
    void loginUser() {
        Database database = new Database();
        database.addUser("pouria", MainController.getSHA256("Pouriagh1@"), "pouria", "Say my name", "p@g.c", new Pair<>(1, "p"));
        LoginMenuController loginMenuController = new LoginMenuController(database);
        Assertions.assertEquals(LoginMenuMessages.USER_NOT_FOUND, loginMenuController.loginUser("aref", "Password", false));
        Assertions.assertEquals(LoginMenuMessages.INCORRECT_PASSWORD, loginMenuController.loginUser("pouria", "Password", false));
    }
}