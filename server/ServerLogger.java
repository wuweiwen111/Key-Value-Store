package server;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerLogger {
    private static ServerLogger instance = null;

    private ServerLogger() {
        // Private constructor to enforce singleton pattern
    }

    // Singleton pattern to ensure only one instance of the logger
    public static ServerLogger getInstance() {
        if (instance == null) {
            instance = new ServerLogger();
        }
        return instance;
    }

    public void log(String message) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                .format(new Date());
        System.out.println("[" + timeStamp + "] [Server] " + message);
    }

    public void logError(String message) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
                .format(new Date());
        System.err.println("[" + timeStamp + "] [Server] ERROR: " + message);
    }
}

