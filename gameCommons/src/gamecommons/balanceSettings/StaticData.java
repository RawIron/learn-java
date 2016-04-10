package gamecommons.balanceSettings;

import java.sql.ResultSet;
import java.util.HashMap;

import gamecommons.system.Trace;
import gamecommons.system.DataStore;


class StaticDataCache {}

class HashMapCache extends StaticDataCache {
	public HashMap<Integer,DataItemLevel> cached = new HashMap<Integer,DataItemLevel>();
}


public abstract class StaticData {
	protected StaticDataCache cached;
	protected DataStore ds;
	protected Trace t;

	public StaticData(StaticDataCache cached, DataStore ds, Trace t) {
        this.cached = cached;
		this.ds = ds;
		this.t = t;
        refresh();
    }

    protected abstract void refresh();
    protected abstract ResultSet retrieve();
}
