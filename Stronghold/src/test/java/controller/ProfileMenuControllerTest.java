package controller;

import model.User;
import model.databases.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import controller.functionalcontrollers.Pair;
import view.enums.messages.ProfileMenuMessages;

class ProfileMenuControllerTest {
    private final Database database = new Database();
    private final User user;
    {
        database.addUser("username", MainController.getSHA256("password"), "nickname", "slogan", "email", new Pair<>(1, "answer"));
        user = database.getUserByUsername("username");
        AppController.setLoggedInUser(user);
    }
    private final ProfileMenuController profileMenuController = new ProfileMenuController(database);

    @Test
    void changeUsername() {
        Assertions.assertEquals(profileMenuController.changeUsername(null), ProfileMenuMessages.NULL_FIELD);
        Assertions.assertEquals(profileMenuController.changeUsername("hi hello"), ProfileMenuMessages.INVALID_USERNAME);
        Assertions.assertEquals(profileMenuController.changeUsername("username"), ProfileMenuMessages.DUPLICATE_USERNAME);
        Assertions.assertEquals(profileMenuController.changeUsername("hello"), ProfileMenuMessages.SUCCESS);
        Assertions.assertNull(database.getUserByUsername("username"));
    }

    @Test
    void changeNickname() {
        Assertions.assertEquals(profileMenuController.changeNickname(null), ProfileMenuMessages.NULL_FIELD);
        Assertions.assertEquals(profileMenuController.changeNickname("hello"), ProfileMenuMessages.SUCCESS);
        Assertions.assertEquals(user.getNickname(), "hello");
    }

    @Test
    void checkChangePasswordErrors() {
        Assertions.assertEquals(profileMenuController.checkChangePasswordErrors(null, "hello"), ProfileMenuMessages.NULL_FIELD);
        Assertions.assertEquals(profileMenuController.checkChangePasswordErrors(null, null), ProfileMenuMessages.NULL_FIELD);
        Assertions.assertEquals(profileMenuController.checkChangePasswordErrors("hello", null), ProfileMenuMessages.NULL_FIELD);
        Assertions.assertEquals(profileMenuController.checkChangePasswordErrors("hello", "hello"), ProfileMenuMessages.INCORRECT_PASSWORD);
        Assertions.assertEquals(profileMenuController.checkChangePasswordErrors("password", "password"), ProfileMenuMessages.NON_CAPITAL_PASSWORD);
    }

    @Test
    void changePassword() {
        Assertions.assertEquals(profileMenuController.changePassword("password", null), ProfileMenuMessages.NULL_FIELD);
        Assertions.assertEquals(profileMenuController.changePassword("password", "pass"), ProfileMenuMessages.INVALID_PASSWORD_CONFIRM);
        Assertions.assertEquals(profileMenuController.changePassword("password", "password"), ProfileMenuMessages.SUCCESS);
    }

    @Test
    void changeEmail() {
        Assertions.assertEquals(profileMenuController.changeEmail(null), ProfileMenuMessages.NULL_FIELD);
        Assertions.assertEquals(profileMenuController.changeEmail("hello"), ProfileMenuMessages.INVALID_EMAIL_FORMAT);
        Assertions.assertEquals(profileMenuController.changeEmail("a@b.c"), ProfileMenuMessages.SUCCESS);
        Assertions.assertEquals(user.getEmail(), "a@b.c");
    }

    @Test
    void changeSlogan() {
        Assertions.assertEquals(profileMenuController.changeSlogan(null), ProfileMenuMessages.NULL_FIELD);
        Assertions.assertEquals(profileMenuController.changeSlogan("hi"), ProfileMenuMessages.SUCCESS);
        Assertions.assertEquals(user.getSlogan(), "hi");
    }

    @Test
    void removeSlogan() {
        Assertions.assertEquals(profileMenuController.removeSlogan(), ProfileMenuMessages.SUCCESS);
        Assertions.assertEquals(profileMenuController.removeSlogan(), ProfileMenuMessages.EMPTY_SLOGAN);
    }

    @Test
    void showHighScore() {
        Assertions.assertEquals(profileMenuController.showHighScore(), 0);
    }

    @Test
    void showRank() {
        Assertions.assertEquals(profileMenuController.showRank(), 1);
    }

    @Test
    void showSlogan() {
        Assertions.assertEquals(profileMenuController.showSlogan(), user.getSlogan());
    }

    @Test
    void showProfile() {
        Assertions.assertTrue(profileMenuController.showProfile().contains(user.getUsername()));
        Assertions.assertTrue(profileMenuController.showProfile().contains(user.getNickname()));
        Assertions.assertTrue(profileMenuController.showProfile().contains(user.getEmail()));
    }
}