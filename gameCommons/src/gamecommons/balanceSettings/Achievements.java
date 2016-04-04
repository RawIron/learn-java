package gamecommons.balanceSettings;

import gamecommons.balanceSettings.DataAchievement;
import gamecommons.balanceSettings.DataItemAchievement;


/*
Achievements achievement = new Achievements();
achievement("FirstBattle").xpReward
achievement."FirstBattle".xpReward
achievement.with("FirstBattle").xpReward
*/
public class Achievements {
    protected DataAchievement settings = null;

	public Achievements() {
        this.settings = create();
    }

    public DataItemAchievement with(String key) {
        return settings.read(key);
    }

    protected DataAchievement create() {
        return new DataAchievement();
    }
}
