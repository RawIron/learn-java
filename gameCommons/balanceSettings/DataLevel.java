package gameCommons.balanceSettings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import gameCommons.system.Trace;


public class DataLevel {
	protected DataStore ds;
	protected Trace t;
	public HashMap<Integer,DataItemLevel> cached = new HashMap<Integer,DataItemLevel>();
	
	public DataLevel(DataStore ds, Trace t) {
		this.ds = ds;
		this.t = t;
        refresh();
    }
    
    protected void refresh() {
	    DataItemLevel level = null;
	    ResultSet db_res = this.retrieve(0);
	    try {
		    while (db_res.next()) {
		        level = new DataItemLevel();
				level.xpNeeded = db_res.getInt("XpNeeded");
				level.rewarded = db_res.getInt("Rewarded");
		    	
				cached.put(db_res.getInt("Level"), level);
		    }
	    } catch (SQLException e) {
	    	t.trace("SQLException: " + e.getMessage());
			t.trace("SQLState: " + e.getSQLState());
			t.trace("VendorError: " + e.getErrorCode());
		}
	}
	
	protected ResultSet retrieve(int dbgroup) {
        String db_sql = "SELECT Level, XpNeeded, Rewarded FROM Levels ORDER BY Level";
		return ds.query(db_sql, "read", null);
	}
}
