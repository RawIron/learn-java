package gamecommons.balanceSettings;


public class DataItemLevel {
	public int level;
	public int xpNeeded;
	public int rewarded;

    public DataItemLevel() {}

    public DataItemLevel(int level, int xpNeeded, int rewarded) {
        this.level = level;
        this.xpNeeded = xpNeeded;
        this.rewarded = rewarded;
    }
}
