package main.java.ninja.jalexander;

import java.io.*;
import java.net.*;


class UDPClient {
    public static void main(String[] args) {
        System.out.println("Running client");
        runDiagnostic(true);
    }

    private final static String serverIP = "13.57.60.32";
    private final static int serverPort = 9876;
    private static final int retryCount = 1;

    public static void runDiagnostic(boolean isWifi) {
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress IPAddress = InetAddress.getByName(serverIP);
            String sentence = "Gimme dem kitties";
            int waitTime = 5;
            String waitString = "" + waitTime;
            byte[] sendData = waitString.getBytes();
            double receivedPerc = 0.0;
            while (receivedPerc < .5) {

                for (int i = 0; i < retryCount; i++) {
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, serverPort);
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

                    if (!started) {
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
                double bRate = ((double) bytesReceived) / (nanoDiff * 1e-9);
                double pRate = ((double) packetsReceived) / (nanoDiff * 1e-9);
                bRate /= 1e6;
                receivedPerc = packetsReceived / 101.0; //hardcoded packet num
                if (receivedPerc < .5) {
                    waitTime = waitTime + 10;
                }
            }
            System.out.println("Final wait time: " + waitTime);
            System.out.println("Final received percentage: " + receivedPerc);
            if (isWifi) {
                //netData.wifiUdpDeltaTime = nanoDiff;
                //netData.wifiUdpPacketsReceived = packetsReceived;
                //netData.wifiUdpPacketRate = pRate;
                //netData.wifiUdpBytesReceived = bytesReceived;
                //netData.wifiUdpByteRate = bRate;
            } else {
                //netData.dataUdpDeltaTime = nanoDiff;
                //netData.dataUdpPacketsReceived = packetsReceived;
                //netData.dataUdpPacketRate = pRate;
                //netData.dataUdpBytesReceived = bytesReceived;
                //netData.dataUdpByteRate = bRate;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
