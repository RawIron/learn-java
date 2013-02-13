
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class javaDB {
  static Connection conn;

  public static void main(String[] args) {
    //String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	//String connectionURL = "jdbc:derby:myDatabase;create=true";
	String driver = "org.apache.derby.jdbc.ClientDriver";
	String connectionURL = "jdbc:derby://localhost/game";
    String createString = "CREATE TABLE Player (NAME VARCHAR(32) NOT NULL, ADDRESS VARCHAR(50) NOT NULL)";
    try {
      Class.forName(driver);
    } catch (java.lang.ClassNotFoundException e) {
      e.printStackTrace();
    }
    try {
      conn = DriverManager.getConnection(connectionURL);
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(createString);

      PreparedStatement psInsert = conn.prepareStatement("insert into Player values (?,?)");

      psInsert.setString(1, args[0]);
      psInsert.setString(2, args[1]);

      psInsert.executeUpdate();

      Statement stmt2 = conn.createStatement();
      ResultSet rs = stmt2.executeQuery("select * from Player");
      int num = 0;
      while (rs.next()) {
        System.out.println(++num + ": Name: " + rs.getString(1) + "\n Address" + rs.getString(2));
      }
      rs.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}