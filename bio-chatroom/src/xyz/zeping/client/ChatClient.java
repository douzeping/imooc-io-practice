package xyz.zeping.client;

import xyz.zeping.client.handler.ReaderHandler;
import xyz.zeping.client.handler.WriterHandler;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class ChatClient {

    private final static int DEFAULT_PORT = 9999;

    private final static String DEFAULT_HOST = "127.0.0.1";

    public void start() {
        Socket socket;
        BufferedReader reader;
        BufferedWriter writer;

        try {
            socket = new Socket(DEFAULT_HOST, DEFAULT_PORT);
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );
            new Thread(new ReaderHandler(reader)).start();
            new Thread(new WriterHandler(writer)).start();
        } catch (ConnectException e) {
            System.out.println("connect server fail please check the server or your network");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.start();
    }
}
