package xyz.zeping.server.hander;

import xyz.zeping.server.ChatServer;

import java.io.*;
import java.net.Socket;

public class SocketHandler implements Runnable {

    private final Socket socket;

    private final static String QUIT = "quit";

    public SocketHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            String msg;
            while ((msg = reader.readLine()) != null) {
                System.out.printf("[%d]:%s\n", socket.getPort(), msg);
                sendToAll(socket, msg);
                if (QUIT.equals(msg)) {
                    ChatServer.socketMap.remove(socket.getPort());
                    socket.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendToAll(Socket socket, String msg) throws IOException {
        ChatServer.socketMap.forEach((key, writer) -> {
            if (key.equals(socket.getPort())) {
                return;
            }
            try {
                writer.write("[" + socket.getPort() + "]:" + msg + "\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

}
