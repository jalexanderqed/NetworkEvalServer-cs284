package main.java.ninja.jalexander;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Util {
    private final static String largeFile = "runescape.png";

    public static byte[] loadImageFile(){
        ClassLoader classLoader = Util.class.getClassLoader();
        File inFile = new File(classLoader.getResource(largeFile).getFile());
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

    public static boolean bytesEqual(byte[] a, byte[] b, int length){
        for(int i = 0; i < length; i++){
            if(a[i] != b[i]) return false;
        }
        return true;
    }
}
