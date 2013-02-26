package gameCommons.data;

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
	    ResultSet db_res_level = this.retrieve(0);
	    try {
		    while (db_res_level.next()) {
		        level = new DataItemLevel();
				level.xpNeeded = db_res_level.getInt("XpNeeded");
				level.karmaReward = db_res_level.getInt("Rewarded");
		    	
				cached.put(db_res_level.getInt("Level"), level);
		    }
	    } catch (SQLException e) {
	    	t.trace("SQLException: " + e.getMessage());
			t.trace("SQLState: " + e.getSQLState());
			t.trace("VendorError: " + e.getErrorCode());
		}
	}
	
	public ResultSet retrieve(int in_dbgroup) {
		return ds.query( "SELECT Level, XpNeeded, Rewarded FROM Levels ORDER BY Level", "read", null);
	}
}
