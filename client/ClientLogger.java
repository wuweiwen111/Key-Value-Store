package client;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClientLogger class for logging client events with timestamps.
 */
public class ClientLogger {
    private static ClientLogger instance;
    private PrintWriter writer;

    private ClientLogger() {
        try {
            FileWriter fw = new FileWriter("client.log", true);
            writer = new PrintWriter(fw, true);
        } catch (IOException e) {
            System.err.println("Could not initialize logger: " + e.getMessage());
        }
    }

    public static synchronized ClientLogger getInstance() {
        if (instance == null) {
            instance = new ClientLogger();
        }
        return instance;
    }

    public void log(String message) {
        String timestamp = getTimestamp();
        writer.println("[" + timestamp + "] [Client] " + message);
    }

    public void logError(String message) {
        String timestamp = getTimestamp();
        writer.println("[" + timestamp + "] [Client] ERROR: " + message);
    }

    private String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(new Date());
    }
}
