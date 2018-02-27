package main.java.ninja.jalexander;

import java.io.*;
import java.net.*;

class UDPClient {
    private static final int retryCount = 20;

    public static void main(String[] args) {
        try(DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress IPAddress = InetAddress.getByName("localhost");
            String sentence = "Gimme dem kitties";
            byte[] sendData = sentence.getBytes();

            for (int i = 0; i < retryCount; i++) {
                DatagramPacket sendPacket = new DatagramPacket(sentence.getBytes(), sendData.length, IPAddress, 9876);
                clientSocket.send(sendPacket);
            }

            boolean receiving = true;
            while (receiving) {
                byte[] receiveData = new byte[64000];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                if (receivePacket.getLength() == sendData.length) {
                    byte[] received = receivePacket.getData();
                    if (Util.bytesEqual(received, sendData, sendData.length)) {
                        receiving = true;
                    }
                }
            }
        } catch(IOException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
