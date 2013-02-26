package gameCommons.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;


public abstract class DbManager {}

public interface DataStore {
    public boolean addPooledConnection(String dataServerKey, DbManager connectPool);
    public boolean closeAll();

    public HashMap<String,Connection> retrieve();
    public int execute(String statement, String Region, String key);
    public ResultSet query(String statement, String Region, String key);
}

