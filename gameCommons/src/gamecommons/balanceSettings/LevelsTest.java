package gamecommons.balanceSettings;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;

import gamecommons.balanceSettings.Levels;
import gamecommons.balanceSettings.DataLevel;
import gamecommons.balanceSettings.DataItemLevel;


class MockedLevels extends Levels {
    protected DataLevel create() {
        DataLevel mocked = mock(DataLevel.class);
        when(mocked.read(12)).thenReturn(new DataItemLevel(12, 100, 10));
        return mocked;
    }
}

public class LevelsTest extends TestCase {
    public LevelsTest() {
        super();
    }

    protected Levels topic() {
        return new MockedLevels();
    }

    public final void test_level_12_should_need_100_xp() {
        assertEquals(topic().with(12).xpNeeded, 100);
    }
}

