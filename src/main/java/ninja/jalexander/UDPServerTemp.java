//package main.java.ninja.jalexander;

import java.io.*;
import java.net.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;

class UDPServer {
    public static void main(String args[]) throws Exception {
	//create buffer to send
	BufferedImage img = ImageIO.read(new File("test.jpg"));
	ByteArrayOutputStream baos = new ByteArrayOutputStream();        
	ImageIO.write(img, "jpg", baos);
	baos.flush();
	byte[] buffer = baos.toByteArray();

        DatagramSocket serverSocket = new DatagramSocket(9876);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData());
            System.out.println("RECEIVED: " + sentence);
	    if (sentence.contains("Gimme dem kitties")) {
		System.out.println("Giving kitties");
		InetAddress IPAddress = receivePacket.getAddress();
		int port = receivePacket.getPort();	
		DatagramPacket sendPacket = 
		    new DatagramPacket(buffer, buffer.length, IPAddress, port);
		serverSocket.send(sendPacket);
		
	    }
        }
    }
}
