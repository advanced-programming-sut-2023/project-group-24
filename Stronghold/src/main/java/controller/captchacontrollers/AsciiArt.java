package controller.captchacontrollers;

import utils.enums.CaptchaNumbers;

import java.util.ArrayList;
import java.util.Scanner;

public class AsciiArt {
    private final char[] noiseChars = {'#', '.', '*', '@', '+', 'O'};

    private final ArrayList<StringBuilder> art = new ArrayList<>();

    AsciiArt(int digit) {
        String art = getArtFromDigit(digit);
        Scanner scanner = new Scanner(art);
        while (scanner.hasNextLine()) {
            this.art.add(new StringBuilder(scanner.nextLine()));
        }
    }

    private ArrayList<StringBuilder> getArt() {
        return art;
    }

    private char getRandomNoiseChar() {
        return noiseChars[(int) (Math.random() * noiseChars.length)];
    }

    public String getArtAsString() {
        StringBuilder builder = new StringBuilder();
        for (StringBuilder stringBuilder : art) {
            builder.append(stringBuilder).append('\n');
        }
        return builder.toString();
    }

    public void append(int digit) {
        append(new AsciiArt(digit));
    }

    public void addNoise() {
        for (int i = 0; i < art.size(); i++) {
            char[] line = art.get(i).toString().toCharArray();
            int numberOfRandomChars = (int) ((Math.random() * 0.1 + 0.1) * line.length);
            for (int j = 0; j < numberOfRandomChars; j++) {
                int index = (int) (Math.random() * line.length);
                line[index] = getRandomNoiseChar();
            }
            art.set(i, new StringBuilder(String.valueOf(line)));
        }
    }

    private String getArtFromDigit(int digit) {
        return switch (digit) {
            case 0 -> CaptchaNumbers.ZERO.getAsciiArt();
            case 1 -> CaptchaNumbers.ONE.getAsciiArt();
            case 2 -> CaptchaNumbers.TWO.getAsciiArt();
            case 3 -> CaptchaNumbers.THREE.getAsciiArt();
            case 4 -> CaptchaNumbers.FOUR.getAsciiArt();
            case 5 -> CaptchaNumbers.FIVE.getAsciiArt();
            case 6 -> CaptchaNumbers.SIX.getAsciiArt();
            case 7 -> CaptchaNumbers.SEVEN.getAsciiArt();
            case 8 -> CaptchaNumbers.EIGHT.getAsciiArt();
            case 9 -> CaptchaNumbers.NINE.getAsciiArt();
            default -> CaptchaNumbers.EMPTY.getAsciiArt();
        };
    }

    private void append(AsciiArt asciiArt) {
        assert this.art.size() == asciiArt.art.size();
        for (int i = 0; i < asciiArt.getArt().size(); i++) {
            this.art.get(i).append(asciiArt.art.get(i));
        }
    }
}
