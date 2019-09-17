package xyz.zeping.server;

import xyz.zeping.server.hander.SocketHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatServer {

    private final static int DEFAULT_PORT = 9999;

    public static final Map<Integer, Writer> socketMap = new HashMap<>();

    public void start() {
        try {
            ServerSocket server = new ServerSocket(DEFAULT_PORT);
            System.out.println("Server start at: " + DEFAULT_PORT);
            Socket socket;
            while (true) {
                socket = server.accept();
                System.out.printf("[%d] connect server\n", socket.getPort());
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream()
                        )
                );
                socketMap.put(socket.getPort(), writer);
                new Thread(new SocketHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.start();
    }
}
