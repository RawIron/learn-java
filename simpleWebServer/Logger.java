/*
 * 
 */
 
package simpleWebServer;

import java.io.PrintStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.logging.*;


abstract class Logger {
    String lastMessageLogged = "";
    int totalMessagesLogged = 0;

    public abstract void log(String message);
}


class StreamLogger extends Logger {

    PrintStream log = null;

    public StreamLogger(String logName) {
        try {
            this.log = new PrintStream(
                            new BufferedOutputStream(
                            new FileOutputStream(logName)));
        } catch (FileNotFoundException e) {
        }
    }

    public void log(String message) {
        synchronized (log) {
            log.println(message);
            log.flush();
        }
        lastMessageLogged = message;
        ++totalMessagesLogged;
    }
}


class SimpleLogger extends Logger {
    public void log(String message) {
        System.out.println(message);
        lastMessageLogged = message;
        ++totalMessagesLogged;
    }
}


class ConsoleLogger extends Logger {
    ConsoleHandler consoleHandler = null;
    public ConsoleLogger() {
        consoleHandler = new ConsoleHandler();
    }

    public void log(String message) {
        LogRecord r = new LogRecord(Level.INFO, message);
        consoleHandler.publish(r);
    }
}

