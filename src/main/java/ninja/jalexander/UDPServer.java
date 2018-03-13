package main.java.ninja.jalexander;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class UDPServer extends Thread {
    private static final int retryCount = 43;

    public void run() {
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
                    System.out.println("Received UDP request");

                    InetAddress IPAddress = receivePacket.getAddress();
                    int port = receivePacket.getPort();

                    for (int i = 0; i < bytesChunked.length; i++) {
                        Util.wait(30);
                        DatagramPacket sendPacket =
                                new DatagramPacket(bytesChunked[i], bytesChunked[i].length, IPAddress, port);
                        serverSocket.send(sendPacket);
                    }

                    Util.wait(1);

                    for (int i = 0; i < retryCount; i++) {
                        DatagramPacket sendPacket =
                                new DatagramPacket(receivePacketData, receivePacket.getLength(), IPAddress, port);
                        serverSocket.send(sendPacket);
                        if (i % 5 == 2) Util.wait(i * 3);
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
