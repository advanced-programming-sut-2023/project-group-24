package controller;

import model.databases.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.Pair;
import view.enums.messages.RegisterMenuMessages;

import static org.junit.jupiter.api.Assertions.*;

class RegisterMenuControllerTest {
    Database database = new Database();
    RegisterMenuController registerMenuController = new RegisterMenuController(database);
    @Test
    void checkErrorsForRegister() {
        database.addUser("pouria", MainController.getSHA256("Pouriagh1@"), "pouria", "Say my name", "p@g.c", new Pair<>(1, "p"));
        Assertions.assertEquals(RegisterMenuMessages.NULL_USERNAME, registerMenuController.checkErrorsForRegister("", "ascasc", "caca","casc", "casca","casc"));
        Assertions.assertEquals(RegisterMenuMessages.NULL_PASSWORD, registerMenuController.checkErrorsForRegister("scac", "", "caca","casc", "casca","casc"));
        Assertions.assertEquals(RegisterMenuMessages.NULL_NICKNAME, registerMenuController.checkErrorsForRegister("vavasv", "ascasc", "csacs","", "casca","casc"));
        Assertions.assertEquals(RegisterMenuMessages.NULL_EMAIL, registerMenuController.checkErrorsForRegister("vavasv", "ascasc", "csacs","cascsa", "","casc"));
        Assertions.assertEquals(RegisterMenuMessages.NULL_SLOGAN, registerMenuController.checkErrorsForRegister("vavasv", "ascasc", "csacs","csac", "casca",""));
        Assertions.assertEquals(RegisterMenuMessages.INVALID_USERNAME, registerMenuController.checkErrorsForRegister("pouria`", "ascasc", "caca","casc", "casca","casc"));
        Assertions.assertEquals(RegisterMenuMessages.DUPLICATE_USERNAME, registerMenuController.checkErrorsForRegister("pouria", "ascasc", "caca","casc", "casca","casc"));
//        Assertions.assertEquals(RegisterMenuMessages.NON_SPECIFIC_PASSWORD, registerMenuController.checkErrorsForRegister("pouria1", "Pouria12", "Pouria12","casc", "casca","casc"));
        Assertions.assertEquals(RegisterMenuMessages.NON_CAPITAL_PASSWORD, registerMenuController.checkErrorsForRegister("pouria1", "pouria@12", "pouria@12\"","casc", "casca","casc"));
        Assertions.assertEquals(RegisterMenuMessages.NON_NUMBER_PASSWORD, registerMenuController.checkErrorsForRegister("pouria1", "Pouria@@", "Pouria@@","casc", "casca","casc"));
        Assertions.assertEquals(RegisterMenuMessages.NON_SMALL_PASSWORD, registerMenuController.checkErrorsForRegister("pouria1", "POURIA2@@", "POURIA2@@","casc", "casca","casc"));
        Assertions.assertEquals(RegisterMenuMessages.INCORRECT_PASSWORD_CONFIRM, registerMenuController.checkErrorsForRegister("pouria1", "POURIAa2@@", "POURIA2@@","casc", "casca","casc"));
        Assertions.assertEquals(RegisterMenuMessages.DUPLICATE_EMAIL, registerMenuController.checkErrorsForRegister("pouria1", "POURIA2a@@", "POURIA2a@@","casc", "p@g.c","casc"));
        Assertions.assertEquals(RegisterMenuMessages.INVALID_EMAIL, registerMenuController.checkErrorsForRegister("pouria1", "POURIA2a@@", "POURIA2a@@","casc", "p@gc","casc"));
        Assertions.assertEquals(RegisterMenuMessages.INVALID_EMAIL, registerMenuController.checkErrorsForRegister("pouria1", "POURIA2a@@", "POURIA2a@@","casc", "pg.c","casc"));
        Assertions.assertEquals(RegisterMenuMessages.SUCCESS, registerMenuController.checkErrorsForRegister("pouria1", "POURIA2a@@", "POURIA2a@@","casc", "p@gascsa.c","casc"));

    }

    @Test
    void checkErrorsForSecurityQuestion() {
        database.addUser("pouria", MainController.getSHA256("Pouriagh1@"), "pouria", "Say my name", "p@g.c", new Pair<>(1, MainController.getSHA256("p")));
        Assertions.assertEquals(RegisterMenuMessages.INVALID_NUMBER, registerMenuController.checkErrorsForSecurityQuestion("10", "p", "p"));
        Assertions.assertEquals(RegisterMenuMessages.INVALID_NUMBER, registerMenuController.checkErrorsForSecurityQuestion("-10", "p", "p"));
        Assertions.assertEquals(RegisterMenuMessages.INCORRECT_ANSWER_CONFIRMATION, registerMenuController.checkErrorsForSecurityQuestion("2", "p", "scsa"));
        Assertions.assertEquals(RegisterMenuMessages.SUCCESS, registerMenuController.checkErrorsForSecurityQuestion("2", "p", "p"));
    }

    @Test
    void makeRandomPassword() {
        Assertions.assertTrue(registerMenuController.makeRandomPassword().length() >= 8);
    }

    @Test
    void makeRandomSlogan() {
        Assertions.assertTrue(registerMenuController.makeRandomSlogan().length() >= 0);
    }

    @Test
    void makeNewUsername() {
        Assertions.assertTrue(registerMenuController.makeNewUsername("pouria").length() > "pouria".length());
    }
}