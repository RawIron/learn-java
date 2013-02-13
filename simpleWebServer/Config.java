/*
 *
 */
 
import java.io.*;
import java.net.*;
import java.util.*;


class Config {
    /* server configuration is in properties
     */
    protected static Properties props = new Properties();

    static void loadProps() throws IOException {
        File f = new File
                (System.getProperty("java.home")+File.separator+
                    "lib"+File.separator+"www-server.properties");
        if (f.exists()) {
            InputStream is =new BufferedInputStream(new
                           FileInputStream(f));
            props.load(is);
            is.close();
            String r = props.getProperty("root");
            if (r != null) {
                root = new File(r);
                if (!root.exists()) {
                    throw new Error(root + " doesn't exist as server root");
                }
            }
            r = props.getProperty("timeout");
            if (r != null) {
                timeout = Integer.parseInt(r);
            }
            r = props.getProperty("workers");
            if (r != null) {
                workers = Integer.parseInt(r);
            }
            r = props.getProperty("log");
            if (r != null) {
                p("opening log file: " + r);
                log = new PrintStream(new BufferedOutputStream(
                                      new FileOutputStream(r)));
            }
        }

        /* if no properties were specified, choose defaults */
        if (root == null) {
            root = new File(System.getProperty("user.dir"));
        }
        if (timeout <= 1000) {
            timeout = 5000;
        }
        if (workers < 25) {
            workers = 5;
        }
        if (log == null) {
            p("logging to stdout");
            log = System.out;
        }
    }

    static void printProps() {
        p("root="+root);
        p("timeout="+timeout);
        p("workers="+workers);
    }
}

