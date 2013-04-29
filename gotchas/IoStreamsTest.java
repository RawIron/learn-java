
package gotchas;

import junit.framework.TestCase;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;


public class IoStreamsTest extends TestCase {

    private HashMap<String,String> languages = new HashMap<String,String>();

    private void topic() {
        File file = new File("utf8.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {}

        String line;
        String[] words;
        try {
            while ((line = reader.readLine()) != null) {
                words = line.split(":");
                languages.put(words[0].trim(),words[1].trim());
            }
        } catch (IOException e) {}
    }

    public final void test_readUnicodeFile() {
        topic();
        assertEquals(115, languages.size());
    }

    public final void test_twoDifferentLanguagesAreCorrect() {
        topic();
        assertEquals("我能吞下玻璃而不伤身体。", languages.get("Chinese"));
        assertEquals("나는 유리를 먹을 수 있어요. 그래도 아프지 않아요", languages.get("Korean"));
    }
}
