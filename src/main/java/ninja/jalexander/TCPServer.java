package main.java.ninja.jalexander;

import java.io.*;
import java.net.*;

public class TCPServer extends Thread {
    public void run() {
        byte[] myByteArray = Util.loadImageFile();
        System.out.println("TCP Server Running with file of size " + myByteArray.length);

        try (ServerSocket welcomeSocket = new ServerSocket(6789)) {
            while (true) {
                try (Socket connectionSocket = welcomeSocket.accept()) {
                    System.out.println("Received TCP request");
                    BufferedOutputStream outToClient = new BufferedOutputStream(connectionSocket.getOutputStream());

                    if (outToClient != null) {
                        outToClient.write(myByteArray, 0, myByteArray.length);
                        outToClient.flush();
                        outToClient.close();
                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
