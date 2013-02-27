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
        DataLevel mocked = mock(DataLevel.class);
        when(mocked.read(12)).thenReturn(new DataItemLevel(12, 100, 10));
        return mocked;
    }

    public final void test_should_not_be_null() {
        assertNotNull(topic().read(12));
    }
    public final void test_should_need_100_xp() {
        assertEquals(topic().read(12).xpNeeded, 100);
    }
}

