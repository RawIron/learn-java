package gamecommons.balanceSettings;

import gamecommons.balanceSettings.DataProducer;
import gamecommons.balanceSettings.DataItemProducer;


/*
Producers producer = new Producers();
producer("IronMiner").xpNeeded
producer."IronMiner".xpNeeded
producer.with("IronMiner").xpNeeded
*/
public class Producers {
    protected DataProducer settings = null;

	public Producers() {
        this.settings = create();
    }

    public DataItemProducer with(String key) {
        return settings.read(key);
    }

    protected DataProducer create() {
        return new DataProducer();
    }
}
