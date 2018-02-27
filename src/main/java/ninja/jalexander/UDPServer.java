package main.java.ninja.jalexander;

import java.io.*;
import java.net.*;

public class UDPServer extends Thread {
    public void run() {
        byte[] myByteArray = Util.loadImageFile();
        System.out.println("UDP Server Running with file of size " + myByteArray.length);

        try(DatagramSocket serverSocket = new DatagramSocket(9876)){
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());
                System.out.println("RECEIVED: " + sentence);
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                String capitalizedSentence = sentence.toUpperCase();
                sendData = capitalizedSentence.getBytes();
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
