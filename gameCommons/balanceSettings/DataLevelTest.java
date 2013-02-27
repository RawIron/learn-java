package gameCommons.balanceSettings;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;

import gameCommons.balanceSettings.DataLevel;
import gameCommons.balanceSettings.DataItemLevel;



public class DataLevelTest extends TestCase {
    public DataLevelTest() {
        super();
    }

    protected DataLevel topic() {
        return new DataLevel();
    }

    public final void test_should_be_null() {
        assertNull(topic().read(12));
    }
}

