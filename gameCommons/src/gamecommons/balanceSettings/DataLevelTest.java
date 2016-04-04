package gamecommons.balanceSettings;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;

import gamecommons.balanceSettings.DataLevel;
import gamecommons.balanceSettings.DataItemLevel;



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

