package gameCommons.balanceSettings;

import gameCommons.balanceSettings.DataLevel;
import gameCommons.balanceSettings.DataItemLevel;


/*
Levels level = new Levels();
level(5).xpNeeded
level.5.xpNeeded 
level.with(5).xpNeeded
*/
public class Levels {
    protected DataLevel settings = null;

	public Levels() {
        this.settings = create();
    }

    public DataItemLevel with(int key) {
        return settings.read(key);
    }

    protected DataLevel create() {
        return new DataLevel();
    }
}
