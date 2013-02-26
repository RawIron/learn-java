package gameCommons.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class DataProduct {
	protected DataStore ds;
	public HashMap<String,DataItemProduct> cached = new HashMap<String,DataItemProduct>();
	
	public DataProduct(DataStore ds) {
		this.ds = ds;
		
	    String db_sql_read_Product = 
	    	" SELECT Name, CoinsValue, PremiumValue "
	        + " FROM Products ";
	    
	    DataItemProduct product = null;
	    ResultSet db_res_product = ds.query(db_sql_read_Product, "read", null);
	    try {
		    while (db_res_product.next()) {
		        product = new DataItemProduct();
				product.karmaValue = db_res_product.getInt("CoinsValue");
				product.goldValue = db_res_product.getInt("PremiumValue");			
	    	
				cached.put(db_res_product.getString("Name"), product);
		    }
	    } catch (SQLException e) {}
	}
}
