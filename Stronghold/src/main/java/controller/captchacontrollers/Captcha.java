package controller.captchacontrollers;

public class Captcha {
    private String code;
    private final AsciiArt asciiArt;

    private Captcha() {
        code = null;
        asciiArt = new AsciiArt(-1);
    }

    public AsciiArt getAsciiArt() {
        return asciiArt;
    }

    public String getCode() {
        return code;
    }

    private void setCode(String code) {
        this.code = code;
    }

    public static Captcha generateRandomCaptcha() {
        int numberOfDigits = (int) (Math.random() * 5 + 4);
        Captcha captcha = new Captcha();
        StringBuilder codeBuilder = new StringBuilder();

        for (int i = 0; i < numberOfDigits; i++) {
            int digit = (int) (Math.random() * 10);
            captcha.getAsciiArt().append(digit);
            codeBuilder.append(digit);
        }
        captcha.setCode(codeBuilder.toString());
        captcha.asciiArt.addNoise();
        return captcha;
    }
}
