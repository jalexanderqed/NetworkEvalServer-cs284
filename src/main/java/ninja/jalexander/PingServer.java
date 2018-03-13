package main.java.ninja.jalexander;

import java.io.*;
import java.net.*;

public class PingServer extends Thread {
    public void run() {
        try (DatagramSocket serverSocket = new DatagramSocket(5678)) {
            while (true) {
                byte[] receiveData = new byte[10];
                byte[] sendData = new byte[1];
                String ret = "b";
                sendData = ret.getBytes();
                while (true) {
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    serverSocket.receive(receivePacket);

                    System.out.println("Received ping request");

                    DatagramPacket sendPacket =
                            new DatagramPacket(sendData, sendData.length, receivePacket.getAddress(), receivePacket.getPort());
                    serverSocket.send(sendPacket);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
