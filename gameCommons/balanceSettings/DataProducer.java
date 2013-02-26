package gameCommons.data;

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
	    String db_sql_read_Producer =
	    	" SELECT Name, Repeatable, XpValue "
	    	+ ", ProductionTime "
	        + " FROM Producers ";
	    
	    DataItemProducer producer = null;
	    ResultSet db_res_producer = ds.query(db_sql_read_Producer, "read", null);
	    try {
		    while (db_res_producer.next()) {
		        producer = new DataItemProducer();
				producer.produce = db_res_producer.getString("Produce");
				producer.repeatable = db_res_producer.getInt("Repeatable");
				producer.xpHarvestValue = db_res_producer.getInt("XpValue");
				producer.productionHours = db_res_producer.getInt("ProductionTime");
	    	
				cached.put(db_res_producer.getString("Name"), producer);
		    }
	    } catch (SQLException e) {}
	}
}
