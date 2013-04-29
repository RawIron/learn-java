package gotchas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


class TakeSmallFileAndRepeatToLarge {
    File file = null;
    BufferedReader reader = null;
    FileWriter writer = null;
    String writeThis = "";
    File outFile = null;

    public TakeSmallFileAndRepeatToLarge () {
        writeThis = "just a string to test the repeat" + System.getProperty("line.separator");
        outFile = new File("repeat.out");
    }
    public TakeSmallFileAndRepeatToLarge (String fileName) {
        outFile = new File(fileName + ".out");
        try {
            String line = "";
            file = new File(fileName);
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                writeThis += line;
            }
        } catch (Exception e) {}
    }

    public void repeat(int times) {
        try {
            writer = new FileWriter(outFile);
            for (int i=0; i<times; ++i) {
                writer.write(writeThis);
                writer.flush();
            }
        } catch (IOException e) {}
    }
}


class Runner {
    public static void main(String[] args) {
        TakeSmallFileAndRepeatToLarge creator = new TakeSmallFileAndRepeatToLarge();
        creator.repeat(10000);
    }
}
