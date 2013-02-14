/*
 * 
 */
 
package simpleWebServer;

import java.io.*;
import java.net.*;
import java.util.*;


abstract class Logger {
    public abstract void log(String message);
}


class StreamLogger extends Logger {

    PrintStream log = null;

    public StreamLogger(String logName) {
        try {
            this.log = new PrintStream(new BufferedOutputStream(
                              new FileOutputStream(logName)));
        } catch (FileNotFoundException e) {
        }
    }

    public void log(String message) {
        synchronized (log) {
            log.println(message);
            log.flush();
        }
    }
}


class SimpleLogger extends Logger {
    public void log(String message) {
        System.out.println(message);
    }
}

