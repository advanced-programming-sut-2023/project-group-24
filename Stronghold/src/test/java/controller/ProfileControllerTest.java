package controller;

import controller.functionalcontrollers.Pair;
import controller.nongame.ProfileController;
import model.User;
import model.databases.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import view.enums.messages.ProfileMenuMessages;

class ProfileControllerTest {
    private final Database database = new Database();
    private final User user;
    private final ProfileController profileController;

    {
        database.addUser("username", MainController.getSHA256("password"), "nickname", "slogan", "email", new Pair<>(1, "answer"));
        user = database.getUserByUsername("username");
        AppController.setLoggedInUser(user);
        profileController = new ProfileController(database);
    }

    @Test
    void changeUsername() {
        Assertions.assertEquals(profileController.changeUsername(null), ProfileMenuMessages.NULL_FIELD);
        Assertions.assertEquals(profileController.changeUsername("hi hello"), ProfileMenuMessages.INVALID_USERNAME);
        Assertions.assertEquals(profileController.changeUsername("username"), ProfileMenuMessages.DUPLICATE_USERNAME);
        Assertions.assertEquals(profileController.changeUsername("hello"), ProfileMenuMessages.SUCCESS);
        Assertions.assertNull(database.getUserByUsername("username"));
    }

    @Test
    void changeNickname() {
        Assertions.assertEquals(profileController.changeNickname(null), ProfileMenuMessages.NULL_FIELD);
        Assertions.assertEquals(profileController.changeNickname("hello"), ProfileMenuMessages.SUCCESS);
        Assertions.assertEquals(user.getNickname(), "hello");
    }

    @Test
    void checkChangePasswordErrors() {
        Assertions.assertEquals(profileController.checkChangePasswordErrors(null, "hello"), ProfileMenuMessages.NULL_FIELD);
        Assertions.assertEquals(profileController.checkChangePasswordErrors(null, null), ProfileMenuMessages.NULL_FIELD);
        Assertions.assertEquals(profileController.checkChangePasswordErrors("hello", null), ProfileMenuMessages.NULL_FIELD);
        Assertions.assertEquals(profileController.checkChangePasswordErrors("hello", "hello"), ProfileMenuMessages.INCORRECT_PASSWORD);
        Assertions.assertEquals(profileController.checkChangePasswordErrors("password", "password"), ProfileMenuMessages.NON_CAPITAL_PASSWORD);
    }

    @Test
    void changePassword() {
        Assertions.assertEquals(profileController.changePassword("password", null), ProfileMenuMessages.NULL_FIELD);
        Assertions.assertEquals(profileController.changePassword("password", "pass"), ProfileMenuMessages.INVALID_PASSWORD_CONFIRM);
        Assertions.assertEquals(profileController.changePassword("password", "password"), ProfileMenuMessages.SUCCESS);
    }

    @Test
    void changeEmail() {
        Assertions.assertEquals(profileController.changeEmail(null), ProfileMenuMessages.NULL_FIELD);
        Assertions.assertEquals(profileController.changeEmail("hello"), ProfileMenuMessages.INVALID_EMAIL_FORMAT);
        Assertions.assertEquals(profileController.changeEmail("a@b.c"), ProfileMenuMessages.SUCCESS);
        Assertions.assertEquals(user.getEmail(), "a@b.c");
    }

    @Test
    void changeSlogan() {
        Assertions.assertEquals(profileController.changeSlogan(null), ProfileMenuMessages.NULL_FIELD);
        Assertions.assertEquals(profileController.changeSlogan("hi"), ProfileMenuMessages.SUCCESS);
        Assertions.assertEquals(user.getSlogan(), "hi");
    }

    @Test
    void removeSlogan() {
        Assertions.assertEquals(profileController.removeSlogan(), ProfileMenuMessages.SUCCESS);
        Assertions.assertEquals(profileController.removeSlogan(), ProfileMenuMessages.EMPTY_SLOGAN);
    }

    @Test
    void showHighScore() {
        Assertions.assertEquals(profileController.showHighScore(), 0);
    }

    @Test
    void showRank() {
        Assertions.assertEquals(profileController.showRank(), 1);
    }

    @Test
    void showSlogan() {
        Assertions.assertEquals(profileController.showSlogan(), user.getSlogan());
    }

    @Test
    void showProfile() {
        Assertions.assertTrue(profileController.showProfile().contains(user.getUsername()));
        Assertions.assertTrue(profileController.showProfile().contains(user.getNickname()));
        Assertions.assertTrue(profileController.showProfile().contains(user.getEmail()));
    }
}