package main.java.ninja.jalexander;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class UDPServer extends Thread {
    private static final int retryCount = 20;

    public void run() {
        byte[] finishBytes = "messiii".getBytes();
        byte[] myByteArray = Util.loadImageFile();
        System.out.println("UDP Server Running with file of size " + myByteArray.length);

        int chunkSize = 60000;
        int chunkCount = (myByteArray.length / chunkSize) + 1;
        System.out.println("Chunk Count is: " + chunkCount);
        byte[][] bytesChunked = new byte[chunkCount][];

        for (int i = 0; i < chunkCount - 1; i++) {
            bytesChunked[i] = new byte[chunkSize];
            bytesChunked[i] = Arrays.copyOfRange(myByteArray, i * chunkSize, (i + 1) * chunkSize);
        }

        int lastChunkSize = myByteArray.length - ((chunkCount - 2) * chunkSize);
        bytesChunked[chunkCount - 1] = new byte[lastChunkSize];
        bytesChunked[chunkCount - 1] = Arrays.copyOfRange(myByteArray, (chunkCount - 1) * chunkSize, myByteArray.length);

        try (DatagramSocket serverSocket = new DatagramSocket(9876)) {
            while (true) {
                try {
                    byte[] receiveData = new byte[64000];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    serverSocket.receive(receivePacket);
                    byte[] receivePacketData = receivePacket.getData();
                    String waitString = new String(receivePacketData);
                    System.out.println("Wait String size: " + waitString.trim().length() + '\n');
                    int waitTime = Integer.parseInt(waitString.trim());

                    System.out.println("Received UDP request");
                    System.out.println("Wait interval is: " + waitTime);

                    InetAddress IPAddress = receivePacket.getAddress();
                    int port = receivePacket.getPort();

                    for (int i = 0; i < bytesChunked.length; i++) {
                        Util.wait(waitTime);
                        DatagramPacket sendPacket =
                                new DatagramPacket(bytesChunked[i], bytesChunked[i].length, IPAddress, port);
                        serverSocket.send(sendPacket);
                    }

                    long start = System.currentTimeMillis();
                    Util.wait(1);
                    for (int i = 0; i < retryCount; i++) {
                        DatagramPacket sendPacket =
                                new DatagramPacket(finishBytes, finishBytes.length, IPAddress, port);
                        serverSocket.send(sendPacket);
                        Util.wait(i * 6);
                    }
                    long end = System.currentTimeMillis();
                    System.out.println("Finished sending retries in " + (end - start));
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
