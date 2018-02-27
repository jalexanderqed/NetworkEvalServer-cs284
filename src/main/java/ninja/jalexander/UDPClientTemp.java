//package main.java.ninja.jalexander;

import java.io.*;
import java.net.*;
//import javafx.scene.image.ImageView;

class UDPClient {
    public static void main(String args[]) throws Exception {
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[100000];
        String sentence = "Gimme dem kitties";
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
        clientSocket.send(sendPacket);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);

	byte[] buff = receivePacket.getData();
	System.out.println(buff);
	/*
	final Bitmap new_img = BitmapFactory.decodeByteArray(buff, 0,
							     buff.length);
	runOnUiThread(new Runnable() {
		@Override
		public void run() {
		    ImageView image = (ImageView) findViewById(R.id.test_image);
		    image.setImageBitmap(new_img);
		}
	    });
	*/
        clientSocket.close();
    }
}
