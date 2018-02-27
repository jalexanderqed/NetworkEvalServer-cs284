package main.java.ninja.jalexander;

import java.io.*;
import java.net.*;

public class TCPServer extends Thread {
    private final static String fileToSend = "runescape.png";

    public void run() {
        ClassLoader classLoader = getClass().getClassLoader();
        File inFile = new File(classLoader.getResource(fileToSend).getFile());
        System.out.println("TCP Server Running with file of size " + inFile.length());
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


        try (ServerSocket welcomeSocket = new ServerSocket(6789)) {
            while (true) {
                Socket connectionSocket = welcomeSocket.accept();
                BufferedOutputStream outToClient = new BufferedOutputStream(connectionSocket.getOutputStream());

                if (outToClient != null) {
                    outToClient.write(myByteArray, 0, myByteArray.length);
                    outToClient.flush();
                    outToClient.close();
                }
                connectionSocket.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
