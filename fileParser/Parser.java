
package fileParser;

import java.io.*;


interface P4Reader {
    public StringBuilder read();
}

class LineByLineReader implements P4Reader {
    StringBuilder contents = new StringBuilder();
    File file = null;
    BufferedReader reader = null;

    public LineByLineReader(String fileName) {
        file = new File(fileName);
    }

    public StringBuilder read() {
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = null;

            while ((text = reader.readLine()) != null) {
            contents
                .append(text)
                .append(System.getProperty(
                    "line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return contents;
    }

}


public class Parser {
    public void parse() {
        P4Reader reader = new LineByLineReader("index.html");
        StringBuilder contents = reader.read();
        System.out.println(contents.toString());
    }
}


class Run {
    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.parse();
    }

}
