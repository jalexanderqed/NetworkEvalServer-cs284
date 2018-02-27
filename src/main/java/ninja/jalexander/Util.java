package main.java.ninja.jalexander;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class Util {
    private final static String largeFile = "runescape.png";

    public static byte[] loadImageFile() {
        File inFile = getFileFor(largeFile);
        byte[] myByteArray = new byte[(int) inFile.length()];

        try (
                FileInputStream fis = new FileInputStream(inFile);
                BufferedInputStream bis = new BufferedInputStream(fis);
        ) {
            bis.read(myByteArray, 0, myByteArray.length);
        } catch (IOException ex) {
            System.err.println("Could not read image file");
            System.err.println(ex.getMessage());
        }

        return myByteArray;
    }

    public static File getFileFor(String file) {
        ClassLoader classLoader = Util.class.getClassLoader();
        return new File(classLoader.getResource(file).getFile());
    }

    public static boolean bytesEqual(byte[] a, byte[] b, int length) {
        for (int i = 0; i < length; i++) {
            if (a[i] != b[i]) return false;
        }
        return true;
    }

    public static void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }
}
