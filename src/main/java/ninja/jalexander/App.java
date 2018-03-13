package main.java.ninja.jalexander;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

import static spark.Spark.*;

/**
 * Hello world!
 */
public class App {
    private static final String outputFile = Paths.get(System.getProperty("user.home"), "output.txt").toString();

    public static void main(String[] args) {
        TCPServer tcpServer = new TCPServer();
        tcpServer.start();
        UDPServer udpServer = new UDPServer();
        udpServer.start();
        PingServer pingServer = new PingServer();
        pingServer.start();

        port(8080);

        get("/hello", (req, res) -> "Hello World");

        post("/data", (req, res) -> {
            String body = req.body();

            try (FileWriter fw = new FileWriter(outputFile, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.println(body);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
                res.status(500);
            }

            res.status(201);
            return "";

        });
    }
}
