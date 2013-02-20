/*
 *
 */

package simpleWebServer;
 
import java.io.*;
import java.net.*;
import java.util.*;

import static simpleWebServer.HttpConstants.*;
import simpleWebServer.FileExtensionToContentTypeMapper;
import simpleWebServer.WorkerPool;


class StaticContentReverse {

    static final int BUF_SIZE = 2048;
    static final byte[] EOL = {(byte)'\r', (byte)'\n' };

    Logger logger = null;
    Socket client = null;
    FileExtensionToContentTypeMapper mapper = new FileExtensionToContentTypeMapper();

    public StaticContentReverse(Logger logger) {
        this.logger = logger;
    }

    void deliverContent(Socket client, int httpMethod, File targ) {
        String hostAddress = client.getInetAddress().getHostAddress();

        try {
        PrintStream ps = new PrintStream(client.getOutputStream());
        boolean OK = printHeaders(targ, ps, hostAddress);
        if (httpMethod == HTTP_GET) {
            if (OK) {
                sendFile(targ, ps);
            } else {
                send404(ps);
            }
        } else if (httpMethod == HTTP_BAD_METHOD) {
            send405(ps);
        }
        } catch (IOException e) {}
    }

    boolean printHeaders(File targ, PrintStream ps, String hostAddress)
        throws IOException
    {
        boolean ret = false;
        int rCode = 0;

        if (!targ.exists()) {
            rCode = HTTP_NOT_FOUND;
            ps.print("HTTP/1.0 " + HTTP_NOT_FOUND + " not found");
            ps.write(EOL);
            ret = false;
        }  else {
            rCode = HTTP_OK;
            ps.print("HTTP/1.0 " + HTTP_OK+" OK");
            ps.write(EOL);
            ret = true;
        }
        logger.log("From " + hostAddress + ": GET " +
            targ.getAbsolutePath() + "-->" + rCode);

        ps.print("Server: Simple java");
        ps.write(EOL);
        ps.print("Date: " + (new Date()));
        ps.write(EOL);

        if (ret) {
            if (!targ.isDirectory()) {
                ps.print("Content-length: "+targ.length());
                ps.write(EOL);
                ps.print("Last Modified: " + (new
                              Date(targ.lastModified())));
                ps.write(EOL);
                String name = targ.getName();
                int ind = name.lastIndexOf('.');
                String ct = null;
                if (ind > 0) {
                    ct = mapper.extensionsToContent.get(name.substring(ind));
                }
                if (ct == null) {
                    ct = "unknown/unknown";
                }
                ps.print("Content-type: " + ct);
                ps.write(EOL);
            } else {
                ps.print("Content-type: text/html");
                ps.write(EOL);
            }
        }
        return ret;
    }

    void send404(PrintStream ps) throws IOException {
        ps.write(EOL);
        ps.println("Not Found\n\n" +
                   "The requested resource was not found.\n");
        ps.flush();
        client.close();
    }

    void send405(PrintStream ps) throws IOException {
        ps.write(EOL);
        ps.println("HTTP/1.0 " + HTTP_BAD_METHOD +
                   " unsupported method type: ");
        ps.flush();
        client.close();
    }

    void sendFile(File targ, PrintStream ps) throws IOException {
        InputStream is = null;
        ps.write(EOL);
        if (targ.isDirectory()) {
            listDirectory(targ, ps);
            return;
        } else {
            is = new FileInputStream(targ.getAbsolutePath());
        }

        byte[] buf = new byte[BUF_SIZE];
        try {
            int n;
            while ((n = is.read(buf)) > 0) {
                ps.write(buf, 0, n);
            }
        } finally {
            is.close();
        }
    }

    void listDirectory(File dir, PrintStream ps) throws IOException {
        ps.println("<TITLE>Directory listing</TITLE><P>\n");
        ps.println("<A HREF=\"..\">Parent Directory</A><BR>\n");
        String[] list = dir.list();
        for (int i = 0; list != null && i < list.length; i++) {
            File f = new File(dir, list[i]);
            if (f.isDirectory()) {
                ps.println("<A HREF=\""+list[i]+"/\">"+list[i]+"/</A><BR>");
            } else {
                ps.println("<A HREF=\""+list[i]+"\">"+list[i]+"</A><BR");
            }
        }
        ps.println("<P><HR><BR><I>" + (new Date()) + "</I>");
    }
}

