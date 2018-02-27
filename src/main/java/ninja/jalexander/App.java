package main.java.ninja.jalexander;

import java.io.*;
import java.net.*;


/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        TCPServer tcpServer = new TCPServer();
        tcpServer.start();
        UDPServer udpServer = new UDPServer();
        udpServer.start();
    }
}
