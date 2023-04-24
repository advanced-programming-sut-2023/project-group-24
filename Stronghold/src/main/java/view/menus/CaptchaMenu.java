package view.menus;

import controller.captchacontrollers.Captcha;
import view.enums.commands.Commands;

public class CaptchaMenu {

    public static boolean runCaptcha() {
        Captcha captcha;
        while (true) {
            captcha = Captcha.generateRandomCaptcha();
            String code = captcha.getCode();
            String asciiArt = captcha.getAsciiArt().getArtAsString();
            System.out.println(asciiArt);
            String userInput = GetInputFromUser.getUserInput();
            if (Commands.getMatcher(userInput, Commands.CHANGE_CODE) == null)
                return code.equals(userInput);
        }
    }
}
