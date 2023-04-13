package controller.captchacontrollers;

import view.enums.CaptchaNumbers;

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
        switch (digit) {
            case 0:
                return CaptchaNumbers.ZERO.getAsciiArt();
            case 1:
                return CaptchaNumbers.ONE.getAsciiArt();
            case 2:
                return CaptchaNumbers.TWO.getAsciiArt();
            case 3:
                return CaptchaNumbers.THREE.getAsciiArt();
            case 4:
                return CaptchaNumbers.FOUR.getAsciiArt();
            case 5:
                return CaptchaNumbers.FIVE.getAsciiArt();
            case 6:
                return CaptchaNumbers.SIX.getAsciiArt();
            case 7:
                return CaptchaNumbers.SEVEN.getAsciiArt();
            case 8:
                return CaptchaNumbers.EIGHT.getAsciiArt();
            case 9:
                return CaptchaNumbers.NINE.getAsciiArt();
            default:
                return CaptchaNumbers.EMPTY.getAsciiArt();
        }
    }

    private void append(AsciiArt asciiArt) {
        assert this.art.size() == asciiArt.art.size();
        for (int i = 0; i < asciiArt.getArt().size(); i++) {
            this.art.get(i).append(asciiArt.art.get(i));
        }
    }
}
