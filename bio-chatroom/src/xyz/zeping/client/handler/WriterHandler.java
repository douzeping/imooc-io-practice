package xyz.zeping.client.handler;

import java.io.*;

public class WriterHandler implements Runnable {
    private final Writer writer;

    private static final String QUIT = "quit";

    public WriterHandler(BufferedWriter writer) {
        this.writer = writer;
    }

    @Override
    public void run() {
        BufferedReader consoleReader = new BufferedReader(
                new InputStreamReader(System.in)
        );

        while (true) {
            String input;
            try {
                if ((input = consoleReader.readLine()) != null) {
                    writer.write(input + "\n");
                    writer.flush();
                    if (QUIT.equals(input)) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
