/*
 * 
 */
 
import java.io.*;
import java.net.*;
import java.util.*;


class Logger {
    /* print to stdout */
    protected static void p(String s) {
        System.out.println(s);
    }

    /* print to the log file */
    protected static void log(String s) {
        synchronized (log) {
            log.println(s);
            log.flush();
        }
    }

    static PrintStream log = null;
}

