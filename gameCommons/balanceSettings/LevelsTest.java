package gameCommons.balanceSettings;

import junit.framework.TestCase;
import static org.mockito.Mockito.*;

import gameCommons.balanceSettings.Levels;
import gameCommons.balanceSettings.DataLevel;
import gameCommons.balanceSettings.DataItemLevel;


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

    public final void test_should_need_100_xp() {
        assertEquals(topic().with(12).xpNeeded, 100);
    }
}


class LevelsTestMain {
    public static void main(String[] args) {
        LevelsTest t = new LevelsTest();
        Levels l = t.topic();
        System.out.println(l.with(12).xpNeeded);
    }
}
