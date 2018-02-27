package main.java.ninja.jalexander;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class UDPServer extends Thread {
    public void run() {

        byte[] myByteArray = Util.loadImageFile();
        System.out.println("UDP Server Running with file of size " + myByteArray.length);

        int chunkSize = 60000;
        int chunkCount = (myByteArray.length / chunkSize) + 1;
	//System.out.println("Chunk Count is: " + chunkCount);
	byte[][] bytesChunked = new byte[chunkCount][];
	
	for (int i = 0; i < chunkCount - 1; i++) {
	    bytesChunked[i] = new byte[chunkSize];
	    bytesChunked[i] = Arrays.copyOfRange(myByteArray, i * chunkSize, (i + 1) * chunkSize);
	    //System.out.println("Other one: " + bytesChunked[i].length);
	}
	//do last one
	int lastChunkSize = myByteArray.length - ((chunkCount - 2) * chunkSize);
	bytesChunked[chunkCount - 1] = new byte[lastChunkSize];
	bytesChunked[chunkCount - 1] = Arrays.copyOfRange(myByteArray, (chunkCount - 1) * chunkSize, myByteArray.length);
	//System.out.println("Last one: " + bytesChunked[chunkCount -1].length);
	/*

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
	    }*/
    }
}
