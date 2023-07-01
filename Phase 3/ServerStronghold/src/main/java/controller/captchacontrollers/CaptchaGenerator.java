package controller.captchacontrollers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class CaptchaGenerator {
    private static Font font = new Font("Verdana", Font.ITALIC | Font.BOLD, 28);

    public static String generateRandomCaptcha(int width, int height, String address) {
        StringBuilder output = new StringBuilder();
        int digits = (int) (Math.random() * 5 + 4);
        for (int i = 0; i < digits; i++) {
            int digit = (int) (Math.random() * 10);
            output.append(digit);
        }
        String captchaStr = output.toString();
        if (pngCaptcha(captchaStr, width, height, address)) return captchaStr;
        else return captchaStr + "*";
    }

    private static boolean pngCaptcha(String randomStr, int width, int height, String file) {
        char[] strs = randomStr.toCharArray();
        try (OutputStream out = new FileOutputStream(file)) {
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = (Graphics2D) bi.getGraphics();
            int len = strs.length;
            setuUpGraphic(width, height, graphics);
            int h = height - ((height - font.getSize()) >> 1), w = width / len, size = w - font.getSize() + 1;

            for (int i = 0; i < len; i++) putLetter((width - (len - i) * w) + size, strs[i], graphics, h);
            for (int i = 0; i < 50; i++) putNoise(width, height, graphics);

            ImageIO.write(bi, "png", out);
            out.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static void setuUpGraphic(int width, int height, Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setFont(font);
    }

    private static void putNoise(int width, int height, Graphics2D g) {
        Color color;
        color = color();
        g.setColor(color);
        g.drawOval(num(width), num(height), 5 + num(10), 5 + num(10));
    }

    private static void putLetter(int x, char str, Graphics2D g, int h) {
        AlphaComposite ac3;
        Color color;
        ac3 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f);
        g.setComposite(ac3);
        color = new Color(20 + num(110), 30 + num(110), 30 + num(110));
        g.setColor(color);
        g.drawString(str + "", x, h - 4);
    }

    private static Color color() {
        int r = 150 + num(250 - 150);
        int g = 150 + num(250 - 150);
        int b = 150 + num(250 - 150);
        return new Color(r, g, b);
    }

    private static int num(int num) {
        return (new Random()).nextInt(num);
    }
}
