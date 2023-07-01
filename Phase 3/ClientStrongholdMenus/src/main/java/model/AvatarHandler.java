package model;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class AvatarHandler {
    public int getAvatarNumber(File path, File target) {
        try {
            for (int i = 1; i < Objects.requireNonNull(path.listFiles()).length; i++) {
                if (FileUtils.contentEquals(new File(path.getAbsolutePath() + "/" + i + ".png"), target))
                    return i;
            }
        } catch (IOException ignored) {
            System.out.println("Error in get avatar number");
        }
        return -1;
    }
}
