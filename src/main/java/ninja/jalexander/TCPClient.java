package main.java.ninja.jalexander;

import java.io.*;
import java.io.ByteArrayOutputStream;
import java.net.*;
import java.nio.file.Paths;

class TCPClient {

    private final static String serverIP = "cs.jalexander.ninja";
    private final static int serverPort = 6789;
    private final static String fileOutput = Paths.get(System.getProperty("user.home"), "Desktop", "run.png").toString();

    public static void main(String args[]) {
        byte[] inBuff = new byte[64000];
        int bytesRead;
        int bytesReceived = 0;

        boolean started = false;
        long startTime = 0;
        long endTime = 0;

        try (
                Socket clientSocket = new Socket(serverIP, serverPort);
                InputStream in = clientSocket.getInputStream();
        ) {
            do {
                if(!started){
                    started = true;
                    startTime = System.nanoTime();
                }

                bytesRead = in.read(inBuff, 0, inBuff.length);
                bytesReceived += bytesRead;
            } while (bytesRead != -1);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }

        if(started) endTime = System.nanoTime();

        long nanoDiff = endTime - startTime;
        double bRate = ((double)bytesReceived) / (nanoDiff * 1e-9);

        System.out.println("Bytes Received: " + bytesReceived);
        System.out.println("Byte Rate: " + (Math.round(10 * bRate / 1e6) / 10.0) + "MB/s");
    }
}
