/*
 * simple, multi-threaded HTTP server
 */

package simpleWebServer;
 
import java.io.*;
import java.net.*;
import java.util.*;

import static simpleWebServer.HttpConstants.*;
import simpleWebServer.WorkerPool;


class HttpRequestWorkerPool extends WorkerPool {

    public HttpRequestWorkerPool(Config settings) {
        super(settings);
    }

    protected Worker createWorker(WorkerPool pool, Config settings) {
        Worker w = new HttpRequestWorker(pool, settings);
        return w;
    }
}


class HttpRequestWorker extends Worker {

    static final int BUFFER_SIZE = 2048;
    static final int EOF = -1;
    static final byte[] EOL = {(byte)'\r', (byte)'\n' };

    byte[] requestBuffer;
    int index;
    int nread;

    public HttpRequestWorker(WorkerPool coworkers, Config config) {
        super(coworkers, config);
        requestBuffer = new byte[BUFFER_SIZE];
    }


    protected void handleClient() throws IOException {

        /* we will only block in read for this many milliseconds
         * before we fail with java.io.InterruptedIOException,
         * at which point we will abandon the connection.
         */
        currentClient.setSoTimeout(settings.timeout);
        currentClient.setTcpNoDelay(true);

        resetBuffer();

        int httpMethod = HTTP_BAD_METHOD;
        File targ = null;
        try {
            InputStream is = new BufferedInputStream(currentClient.getInputStream());
            int totalBytesRead = 0, bytesRead = 0;

            boolean firstLineComplete = false;
            while(!firstLineComplete) {
                while (totalBytesRead < BUFFER_SIZE) {
                    bytesRead = is.read(requestBuffer, totalBytesRead, BUFFER_SIZE - totalBytesRead);
                    if (bytesRead == EOF) {
                        String message = "Line too long";
                        throw new Exception();
                    }
                    firstLineComplete = isLineComplete(totalBytesRead, totalBytesRead + bytesRead);
                    totalBytesRead += bytesRead;
                }
            }

            nread = totalBytesRead;
            index = 0;
            httpMethod = extractMethod(requestBuffer);
            if (httpMethod == HTTP_GET) {
                String fname = extractFilename(requestBuffer);
                targ = openFile(fname);
            }
        } catch (Exception e) {
            /* sent error to client */
        }

        StaticContentReverse proxy = new StaticContentReverse(settings.logger);
        proxy.deliverContent(currentClient, httpMethod, targ);
    }


    protected boolean isLineComplete(int beginSearchAt, int endSearchAt) {
        int i = beginSearchAt;
        for (; i < endSearchAt; ++i) {
            if (requestBuffer[i] == (byte)'\n' || requestBuffer[i] == (byte)'\r') {
                return true;
            }
        }
        return false;
    }

    protected void resetBuffer() {
        /* zero out the buffer from last time */
        for (int i = 0; i < BUFFER_SIZE; i++) {
            requestBuffer[i] = 0;
        }
    }

    protected int extractMethod(byte[] buf) {
        int method = HTTP_BAD_METHOD;

        if (buf[0] == (byte)'G' &&
            buf[1] == (byte)'E' &&
            buf[2] == (byte)'T' &&
            buf[3] == (byte)' ') {
            method = HTTP_GET;
            index = 4;
        } else if (buf[0] == (byte)'H' &&
                   buf[1] == (byte)'E' &&
                   buf[2] == (byte)'A' &&
                   buf[3] == (byte)'D' &&
                   buf[4] == (byte)' ') {
            method = HTTP_HEAD;
            index = 5;
        }
        return method;
    }

    protected String extractFilename(byte[] buf) {
        /* find the file name, from:
         * GET /foo/bar.html HTTP/1.0
         * extract "/foo/bar.html"
         */
        int fnameBegin = index;
        int fnameLen = 0;
        for (int i = index; i < nread; i++) {
            if (buf[i] == (byte)' ') {
                fnameLen = i - fnameBegin;
                break;
            }
        }

        String fname = (new String(buf, 0, fnameBegin,
                  fnameLen)).replace('/', File.separatorChar);
        if (fname.startsWith(File.separator)) {
            fname = fname.substring(1);
        }

        return fname;
    }

    protected File openFile(String fname) {
        File targ = new File(settings.root, fname);
        if (targ.isDirectory()) {
            File ind = new File(targ, "index.html");
            if (ind.exists()) {
                targ = ind;
            }
        }
        return targ;
    }
}


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

