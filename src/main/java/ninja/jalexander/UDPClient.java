package main.java.ninja.jalexander;

import java.io.*;
import java.net.*;

class UDPClient {
    private static final int retryCount = 1;

    public static void main(String[] args) {
        try(DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress IPAddress = InetAddress.getByName("cs.jalexander.ninja");
            //InetAddress IPAddress = InetAddress.getByName("localhost");
            String sentence = "Gimme dem kitties";
            byte[] sendData = sentence.getBytes();

            for (int i = 0; i < retryCount; i++) {
                DatagramPacket sendPacket = new DatagramPacket(sentence.getBytes(), sendData.length, IPAddress, 9876);
                clientSocket.send(sendPacket);
            }

            boolean receiving = true;
            int packetsReceived = 0;
            int bytesReceived = 0;

            boolean started = false;
            long startTime = 0;
            long endTime = 0;

            while (receiving) {
                byte[] receiveData = new byte[64000];

                if(!started){
                    started = true;
                    startTime = System.nanoTime();
                }

                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);

                packetsReceived++;
                bytesReceived += receivePacket.getLength();

                if (receivePacket.getLength() == sendData.length) {
                    byte[] received = receivePacket.getData();
                    if (Util.bytesEqual(received, sendData, sendData.length)) {
                        receiving = false;
                        endTime = System.nanoTime();
                    }
                }
            }

            long nanoDiff = endTime - startTime;
            double bRate = ((double)bytesReceived) / (nanoDiff * 1e-9);
            double pRate = ((double)packetsReceived) / (nanoDiff * 1e-9);

            System.out.println("Packets Received: " + packetsReceived);
            System.out.println("Bytes Received: " + bytesReceived);
            System.out.println("Byte Rate: " + (Math.round(10 * bRate / 1e6) / 10.0) + "MB/s");
            System.out.println("Packet Rate: " + Math.round(pRate) + "p/s");
        } catch(IOException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
