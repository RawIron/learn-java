/*
 * 
 */
 
import java.io.*;
import java.net.*;
import java.util.*;


class Logger {
    protected static void p(String message) {
        System.out.println(message);
    }

    protected static void log(String message) {
        synchronized (log) {
            log.println(message);
            log.flush();
        }
    }

    static PrintStream log = null;
}

