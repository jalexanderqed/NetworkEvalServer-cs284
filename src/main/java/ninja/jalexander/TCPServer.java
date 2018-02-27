
import java.io.*;
import java.net.*;

public class TCPServer extends Thread {

    private final static String fileToSend = "tcpCat.jpg";

    public void run() {

	File myFile = new File( fileToSend );
	byte[] mybytearray = new byte[(int) myFile.length()];

	FileInputStream fis = null;

	try {
	    fis = new FileInputStream(myFile);
	} catch (FileNotFoundException ex) {
	    // Do exception handling
	}
	BufferedInputStream bis = new BufferedInputStream(fis);
	try{
	bis.read(mybytearray, 0, mybytearray.length);
	} catch(IOException ex){
	    //whatever
	}


        while (true) {
            ServerSocket welcomeSocket = null;
            Socket connectionSocket = null;
            BufferedOutputStream outToClient = null;

            try {
                welcomeSocket = new ServerSocket(6789);
                connectionSocket = welcomeSocket.accept();
                outToClient = new BufferedOutputStream(connectionSocket.getOutputStream());
            } catch (IOException ex) {
                // Do exception handling
            }

            if (outToClient != null) {
		
                try {
                    outToClient.write(mybytearray, 0, mybytearray.length);
                    outToClient.flush();
                    outToClient.close();
                    connectionSocket.close();

                } catch (IOException ex) {
                    // Do exception handling
                }
            }
        }
    }
}
