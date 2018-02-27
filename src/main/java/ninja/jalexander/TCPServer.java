package main.java.ninja.jalexander;

import java.io.*;
import java.net.*;

public class TCPServer {

    private final static String fileToSend = "runescape.png";

    public static void main(String args[]) {

        File myFile = new File(fileToSend);
        byte[] myByteArray = new byte[(int) myFile.length()];

        try (
                FileInputStream fis = new FileInputStream(myFile);
                BufferedInputStream bis = new BufferedInputStream(fis);
        ) {
            bis.read(myByteArray, 0, myByteArray.length);
        } catch (IOException ex) {
            System.err.println("Could not read image file");
            System.err.println(ex.getMessage());
        }


        try (ServerSocket welcomeSocket = new ServerSocket(6789)) {
            while (true) {
                Socket connectionSocket = null;
                BufferedOutputStream outToClient = null;

                connectionSocket = welcomeSocket.accept();
                outToClient = new BufferedOutputStream(connectionSocket.getOutputStream());

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
