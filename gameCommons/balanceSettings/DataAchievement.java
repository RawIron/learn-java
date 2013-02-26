package gameCommons.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import gameCommons.balanceSettings.DataItemAchievement;
import gameCommons.balanceSettings.DataStore;


public class DataAchievement {
	protected DataStore ds;
	public HashMap<String,DataItemAchievement> cached = new HashMap<String,DataItemAchievement>();
	public HashMap<String,DataItemAchievement> cachedByAward = new HashMap<String,DataItemAchievement>();
	
	public DataAchievement(DataStore ds) {
        this.ds = ds;
        refresh();
    }

    public void refresh() {
	    DataItemAchievement achievement = null;
        ResultSet db_res = retrieve(ds);
	    try {
		    while (db_res.next()) {
		        achievement = new DataItemAchievement();
		        achievement.name = db_res.getString("Name");
		        achievement.unlockAwardName = db_res.getString("AwardName");
		        achievement.threshold = db_res.getInt("Threshold");

				cached.put(db_res.getString("Name"), achievement);
				cachedByAward.put(db_res.getString("AwardName"), achievement);
		    }
	    } catch (SQLException e) {}
	}

    protected ResultSet retrieve(ds) {
	    String db_sql_read = 
	    	" SELECT Name, Threshold, AwardName "
	        + " FROM Achievement ";
	    ResultSet db_res = ds.query(db_sql_read, "read", null);
        return db_res;
    }
}
