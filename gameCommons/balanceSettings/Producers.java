package gameCommons.balanceSettings;

import gameCommons.balanceSettings.DataProducer;
import gameCommons.balanceSettings.DataItemProducer;


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

    public DataItemProducer with(int key) {
        return settings.read(key);
    }

    protected DataProducer create() {
        return new DataProducer();
    }
}
