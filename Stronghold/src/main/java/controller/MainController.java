package controller;

import view.enums.commands.Commands;
import view.enums.messages.CommonMessages;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainController {

    public static boolean isUsernameValid(String username) {
        return Commands.getMatcher(username, Commands.VALID_USERNAME) != null;
    }

    public static boolean isEmailValid(String email) {
        return Commands.getMatcher(email, Commands.VALID_EMAIL) != null;
    }

    public static CommonMessages whatIsPasswordProblem(String password) {
        if (Commands.getMatcher(password, Commands.PASSWORD_SIZE) == null)
            return CommonMessages.SHORT_PASSWORD;
        else if (Commands.getMatcher(password, Commands.PASSWORD_CAPITAL) == null)
            return CommonMessages.NON_CAPITAL_PASSWORD;
        else if (Commands.getMatcher(password, Commands.PASSWORD_SMALL_CHAR) == null)
            return CommonMessages.NON_SMALL_PASSWORD;
        else if (Commands.getMatcher(password, Commands.PASSWORD_NUMBER) == null)
            return CommonMessages.NON_NUMBER_PASSWORD;
        else return CommonMessages.OK;
    }

    public static String getSHA256(String input) {
        try {
            return toHexString(getSHA(input));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 64) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    public static String turnSpaceToUnderline(String string) {
        return string.replaceAll(" ", "_");
    }
}
