package gameCommons.balanceSettings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class DataProduct {
	protected DataStore ds;
	public HashMap<String,DataItemProduct> cached = new HashMap<String,DataItemProduct>();
	
	public DataProduct(DataStore ds) {
		this.ds = ds;
	    refresh();	
    }

    public void refresh() {
	    ResultSet db_res = retrieve();
	    DataItemProduct product = null;
	    try {
		    while (db_res.next()) {
		        product = new DataItemProduct();
				product.karmaValue = db_res.getInt("CoinsValue");
				product.goldValue = db_res.getInt("PremiumValue");			
	    	
				cached.put(db_res.getString("Name"), product);
		    }
	    } catch (SQLException e) {}
	}

    protected ResultSet retrieve() {
	    String db_sql = 
	    	" SELECT Name, CoinsValue, PremiumValue "
	        + " FROM Products ";
	    return ds.query(db_sql, "read", null);
    }
}
