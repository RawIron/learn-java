package gamecommons.balanceSettings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import gamecommons.system.Trace;
import gamecommons.system.DataStore;


public class DataProducer {
	protected DataStore ds;
	protected Trace t;
	public HashMap<String,DataItemProducer> cached = new HashMap<String,DataItemProducer>();

	public DataProducer() {}
	public DataProducer(DataItemProducer producer, Trace t) {
        cache(producer);
        this.t = t;
    }
	public DataProducer(DataStore ds, Trace t) {
		this.ds = ds;
        this.t = t;
    }

    public DataItemProducer read(String key) {
        return cached.get(key);
    }

    public void refresh() {
	    DataItemProducer producer = null;
	    ResultSet db_res_producer = retrieve();
	    try {
		    while (db_res_producer.next()) {
		        producer = new DataItemProducer();
				producer.name = db_res_producer.getString("Name");
				producer.produce = db_res_producer.getString("Produce");
				producer.xpValue = db_res_producer.getInt("XpValue");
				producer.productionTime = db_res_producer.getInt("ProductionTime");

                cache(producer);
		    }
	    } catch (SQLException e) {}
	}

    protected void cache(DataItemProducer producer) {
        cached.put(producer.name, producer);
    }

    protected ResultSet retrieve() {
	    String db_sql_read_Producer =
	    	" SELECT Name, Repeatable, XpValue "
	    	+ ", ProductionTime "
	        + " FROM Producers ";
	    return ds.query(db_sql_read_Producer, "read", null);
    }
}
