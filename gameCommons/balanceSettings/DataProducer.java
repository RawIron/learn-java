package gameCommons.balanceSettings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class DataProducer {
	protected DataStore ds;
	public HashMap<String,DataItemProducer> cached = new HashMap<String,DataItemProducer>();

	public DataProducer(DataStore in_ds) {
		this.ds = ds;
    }
		
    public void refresh() {
	    
	    DataItemProducer producer = null;
	    ResultSet db_res_producer = retrieve();
	    try {
		    while (db_res_producer.next()) {
		        producer = new DataItemProducer();
				producer.produce = db_res_producer.getString("Produce");
				producer.repeatable = db_res_producer.getInt("Repeatable");
				producer.xpValue = db_res_producer.getInt("XpValue");
				producer.productionTime = db_res_producer.getInt("ProductionTime");
	    	
				cached.put(db_res_producer.getString("Name"), producer);
		    }
	    } catch (SQLException e) {}
	}

    protected ResultSet retrieve() {
	    String db_sql_read_Producer =
	    	" SELECT Name, Repeatable, XpValue "
	    	+ ", ProductionTime "
	        + " FROM Producers ";
	    return ds.query(db_sql_read_Producer, "read", null);
    }
}
