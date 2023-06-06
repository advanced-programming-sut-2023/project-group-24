package controller;

import controller.functionalcontrollers.Pair;
import model.databases.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import view.enums.messages.RegisterMenuMessages;

class RegisterControllerTest {
    Database database = new Database();
    RegisterController registerController = new RegisterController(database);

    @Test
    void checkErrorsForRegister() {
        database.addUser("pouria", MainController.getSHA256("Pouriagh1@"), "pouria", "Say my name", "p@g.c", new Pair<>(1, "p"));
        Assertions.assertEquals(RegisterMenuMessages.NULL_USERNAME, registerController.checkErrorsForRegister("", "ascasc", "caca", "casc", "casca", "casc"));
        Assertions.assertEquals(RegisterMenuMessages.NULL_PASSWORD, registerController.checkErrorsForRegister("scac", "", "caca", "casc", "casca", "casc"));
        Assertions.assertEquals(RegisterMenuMessages.NULL_NICKNAME, registerController.checkErrorsForRegister("vavasv", "ascasc", "csacs", "", "casca", "casc"));
        Assertions.assertEquals(RegisterMenuMessages.NULL_EMAIL, registerController.checkErrorsForRegister("vavasv", "ascasc", "csacs", "cascsa", "", "casc"));
        Assertions.assertEquals(RegisterMenuMessages.NULL_SLOGAN, registerController.checkErrorsForRegister("vavasv", "ascasc", "csacs", "csac", "casca", ""));
        Assertions.assertEquals(RegisterMenuMessages.INVALID_USERNAME, registerController.checkErrorsForRegister("pouria`", "ascasc", "caca", "casc", "casca", "casc"));
        Assertions.assertEquals(RegisterMenuMessages.DUPLICATE_USERNAME, registerController.checkErrorsForRegister("pouria", "ascasc", "caca", "casc", "casca", "casc"));
        Assertions.assertEquals(RegisterMenuMessages.NON_SPECIFIC_PASSWORD, registerController.checkErrorsForRegister("pouria1", "Pouria12", "Pouria12", "casc", "casca", "casc"));
        Assertions.assertEquals(RegisterMenuMessages.NON_CAPITAL_PASSWORD, registerController.checkErrorsForRegister("pouria1", "pouria@12", "pouria@12\"", "casc", "casca", "casc"));
        Assertions.assertEquals(RegisterMenuMessages.NON_NUMBER_PASSWORD, registerController.checkErrorsForRegister("pouria1", "Pouria@@", "Pouria@@", "casc", "casca", "casc"));
        Assertions.assertEquals(RegisterMenuMessages.NON_SMALL_PASSWORD, registerController.checkErrorsForRegister("pouria1", "POURIA2@@", "POURIA2@@", "casc", "casca", "casc"));
        Assertions.assertEquals(RegisterMenuMessages.INCORRECT_PASSWORD_CONFIRM, registerController.checkErrorsForRegister("pouria1", "POURIAa2@@", "POURIA2@@", "casc", "casca", "casc"));
        Assertions.assertEquals(RegisterMenuMessages.DUPLICATE_EMAIL, registerController.checkErrorsForRegister("pouria1", "POURIA2a@@", "POURIA2a@@", "casc", "p@g.c", "casc"));
        Assertions.assertEquals(RegisterMenuMessages.INVALID_EMAIL, registerController.checkErrorsForRegister("pouria1", "POURIA2a@@", "POURIA2a@@", "casc", "p@gc", "casc"));
        Assertions.assertEquals(RegisterMenuMessages.INVALID_EMAIL, registerController.checkErrorsForRegister("pouria1", "POURIA2a@@", "POURIA2a@@", "casc", "pg.c", "casc"));
        Assertions.assertEquals(RegisterMenuMessages.SUCCESS, registerController.checkErrorsForRegister("pouria1", "POURIA2a@@", "POURIA2a@@", "casc", "p@gascsa.c", "casc"));

    }

    @Test
    void checkErrorsForSecurityQuestion() {
        database.addUser("pouria", MainController.getSHA256("Pouriagh1@"), "pouria", "Say my name", "p@g.c", new Pair<>(1, MainController.getSHA256("p")));
        Assertions.assertEquals(RegisterMenuMessages.INVALID_NUMBER, registerController.checkErrorsForSecurityQuestion("10", "p", "p"));
        Assertions.assertEquals(RegisterMenuMessages.INVALID_NUMBER, registerController.checkErrorsForSecurityQuestion("-10", "p", "p"));
        Assertions.assertEquals(RegisterMenuMessages.INCORRECT_ANSWER_CONFIRMATION, registerController.checkErrorsForSecurityQuestion("2", "p", "scsa"));
        Assertions.assertEquals(RegisterMenuMessages.SUCCESS, registerController.checkErrorsForSecurityQuestion("2", "p", "p"));
    }

    @Test
    void makeRandomPassword() {
        Assertions.assertTrue(registerController.makeRandomPassword().length() >= 8);
    }

    @Test
    void makeRandomSlogan() {
        Assertions.assertTrue(registerController.makeRandomSlogan().length() >= 1);
    }

    @Test
    void makeNewUsername() {
        Assertions.assertTrue(registerController.makeNewUsername("pouria").length() > "pouria".length());
    }
}