package gameCommons.balanceSettings;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;

import gameCommons.balanceSettings.Levels;
import gameCommons.balanceSettings.DataLevel;
import gameCommons.balanceSettings.DataItemLevel;


public class LevelsTest extends TestCase {
    public LevelsTest() {
        super();
    }

    public final void test_should_need_100_xp() {
        DataLevel mock = mock(DataLevel.class);
        verify(mock).cached.get(12);
        when(mock.cached.get(12)).thenReturn(new DataItemLevel(12, 100, 10));
        DataItemLevel level = mock.cached.get(12);
        assertEquals(level.level, 12);
    }
}
