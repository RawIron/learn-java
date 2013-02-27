package gameCommons.balanceSettings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import gameCommons.system.Trace;
import gameCommons.balanceSettings.DataStore;
import gameCommons.balanceSettings.DataItemLevel;


public class DataLevel {
	protected DataStore ds;
	protected Trace t;
	protected HashMap<Integer,DataItemLevel> cached = new HashMap<Integer,DataItemLevel>();

	public DataLevel(DataStore ds, Trace t) {
		this.ds = ds;
		this.t = t;
        refresh();
    }

	public DataLevel(DataItemLevel level, Trace t) {
        cache(level);
    }

    public DataItemLevel read(int key) {
        return cached.get(key);
    }
    
    protected void refresh() {
	    DataItemLevel level = null;
	    ResultSet db_res = this.retrieve(0);
	    try {
		    while (db_res.next()) {
		        level = new DataItemLevel();
				level.level = db_res.getInt("Level");
				level.xpNeeded = db_res.getInt("XpNeeded");
				level.rewarded = db_res.getInt("Rewarded");
		    	
				cache(level);
		    }
	    } catch (SQLException e) {
	    	t.trace("SQLException: " + e.getMessage());
			t.trace("SQLState: " + e.getSQLState());
			t.trace("VendorError: " + e.getErrorCode());
		}
	}

    protected void cache(DataItemLevel level) {
        cached.put(level.level, level);
    }
	
	protected ResultSet retrieve(int dbgroup) {
        String db_sql = "SELECT Level, XpNeeded, Rewarded FROM Levels ORDER BY Level";
		return ds.query(db_sql, "read", null);
	}
}
