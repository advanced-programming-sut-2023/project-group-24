package view.menus;

import controller.AppController;
import controller.MainController;
import controller.RegisterMenuController;
import utils.enums.MenusName;
import view.enums.commands.Commands;
import view.enums.messages.RegisterMenuMessages;

import java.util.regex.Matcher;

public class RegisterMenu {

    private final RegisterMenuController registerMenuController;

    public RegisterMenu(RegisterMenuController registerMenuController) {
        this.registerMenuController = registerMenuController;
    }

    public void run() {
        String command;
        Matcher matcher;
        while (AppController.getCurrentMenu() == MenusName.REGISTER_MENU) {
            command = GetInputFromUser.getUserInput();
            if ((matcher = Commands.getMatcher(command, Commands.CREATE_USER)) != null)
                checkRegisterErrors(matcher);
            else if (Commands.getMatcher(command, Commands.EXIT) != null)
                enterLoginMenu();
            else if (Commands.getMatcher(command, Commands.SHOW_CURRENT_MENU) != null)
                System.out.println("Register menu");
            else System.out.println("Invalid command!");
        }
    }

    private void enterLoginMenu() {
        AppController.setCurrentMenu(MenusName.LOGIN_MENU);
        System.out.println("Your are in login menu now!");
    }

    private void checkRegisterErrors(Matcher matcher) {
        String username = MainController.removeDoubleQuotation(matcher.group("username"));
        String password = MainController.removeDoubleQuotation(matcher.group("password"));
        String passwordConfirm = MainController.removeDoubleQuotation(matcher.group("passwordConfirm"));
        String nickname = MainController.removeDoubleQuotation(matcher.group("nickname"));
        String email = MainController.removeDoubleQuotation(matcher.group("email"));
        String slogan = MainController.removeDoubleQuotation(matcher.group("slogan"));
        int recoveryQuestion;
        String recoveryAnswer;
        RegisterMenuMessages message = registerMenuController.checkErrorsForRegister(
                username, password,
                passwordConfirm, nickname,
                email, slogan);
        switch (message) {
            case SUCCESS -> {
                if (slogan != null) slogan = checkAndSetSlogan(slogan);
                password = checkAndSetPassword(password);
                if (password == null) return;
                String getRecovery = getRecoveryQuestion();
                if (getRecovery != null) {
                    recoveryQuestion = Integer.parseInt(String.valueOf(getRecovery.charAt(0)));
                    recoveryAnswer = getRecovery.substring(1);
                    registerUser(username, password, nickname, email, slogan, recoveryQuestion, recoveryAnswer);
                }
            }
            case NULL_USERNAME -> System.out.println("Please enter your username!");
            case NULL_PASSWORD -> System.out.println("Please enter your password!");
            case NULL_NICKNAME -> System.out.println("Please enter your nickname!");
            case NULL_EMAIL -> System.out.println("Please enter your email!");
            case NULL_SLOGAN -> System.out.println("Please enter your slogan!");
            case INVALID_USERNAME -> System.out.println("Username format is invalid!");
            case DUPLICATE_USERNAME -> {
                System.out.println("Username is already used!");
                String newUsername = registerMenuController.makeNewUsername(username);
                System.out.println("Recommended username: " + newUsername);
            }
            case SHORT_PASSWORD -> System.out.println("The new password is too short!");
            case NON_CAPITAL_PASSWORD -> System.out.println("The new password must contain uppercase characters!");
            case NON_SMALL_PASSWORD -> System.out.println("The new password must contain lowercase characters!");
            case NON_NUMBER_PASSWORD -> System.out.println("The new password must contain numbers!");
            case NON_SPECIFIC_PASSWORD -> System.out.println("The new password must contain specific characters!");
            case INCORRECT_PASSWORD_CONFIRM -> System.out.println("Your password confirmation is not correct!");
            case DUPLICATE_EMAIL -> System.out.println("Your email is already used!");
            case INVALID_EMAIL -> System.out.println("Your email format is invalid!");
        }
    }

    public void registerUser(String username, String password,
                             String nickname, String email,
                             String slogan, int recoveryQuestionNumber,
                             String recoveryAnswer) {
        RegisterMenuMessages message = registerMenuController.registerUser(username, password,
                nickname, email, slogan,
                recoveryQuestionNumber, recoveryAnswer);
        switch (message) {
            case SUCCESS -> {
                System.out.println("User registered successfully!\nNow your are in login menu!");
                AppController.setCurrentMenu(MenusName.LOGIN_MENU);
            }
            case INCORRECT_CAPTCHA -> System.out.println("You entered the captcha code incorrectly!");
        }
    }

    private String checkAndSetSlogan(String slogan) {
        if (!slogan.equals("random")) return slogan;
        String randomSlogan = registerMenuController.makeRandomSlogan();
        System.out.println("Your slogan is \"" + randomSlogan + "\"");
        return randomSlogan;
    }

    private String checkAndSetPassword(String password) {
        if (!password.equals("random")) return password;
        String randomPassword = registerMenuController.makeRandomPassword();
        System.out.println("Your random password is: " + randomPassword);
        System.out.print("Please re-enter your password here: ");
        String passwordConfirm = GetInputFromUser.getUserInput();
        RegisterMenuMessages message = registerMenuController.checkPasswordErrors(randomPassword, passwordConfirm);
        switch (message) {
            case SUCCESS:
                return randomPassword;
            case INCORRECT_PASSWORD_CONFIRM:
                System.out.println("Your password conformation is not correct!");
                break;
        }
        return null;
    }

    private String getRecoveryQuestion() {
        System.out.println(
                """
                        Pick your security question:
                        1. What is my father's name?
                        2. What was my first pet's name?
                        3. What is my mother's last name?""");
        Matcher matcher;
        while (true) {
            String command = GetInputFromUser.getUserInput();
            if ((matcher = Commands.getMatcher(command, Commands.QUESTION_PICK)) != null) break;
            else System.out.println("Invalid command!");
        }
        String recoveryQuestion = MainController.removeDoubleQuotation(matcher.group("questionNumber"));
        String answer = MainController.removeDoubleQuotation(matcher.group("answer"));
        String answerConfirm = MainController.removeDoubleQuotation(matcher.group("answerConfirm"));
        RegisterMenuMessages message = registerMenuController.checkErrorsForSecurityQuestion(recoveryQuestion, answer, answerConfirm);
        switch (message) {
            case SUCCESS:
                return recoveryQuestion + answer;
            case INCORRECT_ANSWER_CONFIRMATION:
                System.out.println("Your answer confirmation is not correct!");
                break;
            case INVALID_NUMBER:
                System.out.println("Your number should between 1 and 3!");
                break;
        }
        return null;
    }
}
