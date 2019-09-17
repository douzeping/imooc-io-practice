package xyz.zeping.client.handler;

import java.io.BufferedReader;
import java.io.IOException;

public class ReaderHandler implements Runnable {

    private final BufferedReader reader;

    public ReaderHandler(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        String msg;
        try {
            while ((msg = reader.readLine()) != null) {
                System.out.println(msg);
            }
            System.out.println("close");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
