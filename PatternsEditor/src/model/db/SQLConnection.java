package model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class SQLConnection {
	
	private static SQLConnection sqlConnection = new SQLConnection();
	private String url = "jdbc:sqlite:"+System.getProperty("user.dir")+"/patternEditor.db";  
	
	
	private SQLConnection(){
	}
	
	public void initDatabase(){
		Connection connection = null;
		try {            
            connection = DriverManager.getConnection(url);
            String dropTable = "DROP TABLE pattern";
    		String patternTable = "CREATE TABLE IF NOT EXISTS pattern (\n"
                    + "	name TEXT PRIMARY KEY,\n"
                    + " diagramId INTEGER,\n"
                    + " description TEXT,\n"
                    + " xml TEXT,\n"
                    + " FOREIGN KEY(diagramId) REFERENCES diagram(id)"
                    + ");";
    		String adapterTable = "CREATE TABLE IF NOT EXISTS adapter (\n"
    				+ " id INTEGER PRIMARY KEY,\n"
    				+ " mainPattern TEXT,\n"
    				+ " secondaryPattern TEXT,\n"
    	            + " FOREIGN KEY(mainPattern) REFERENCES pattern(name),\n"
    	            + " FOREIGN KEY(secondaryPattern) REFERENCES pattern(name)"
    	            + ");";
    		String versionTable = "CREATE TABLE IF NOT EXISTS version (\n"
    				+ " id INTEGER PRIMARY KEY,\n"
    				+ " mainPattern TEXT,\n"
    				+ " secondaryPattern TEXT,\n"
    	            + " FOREIGN KEY(mainPattern) REFERENCES pattern(name),\n"
    	            + " FOREIGN KEY(secondaryPattern) REFERENCES pattern(name)"
    	            + ");";
    		
    		Statement stmt;
    		try {
    			stmt = connection.createStatement();
    			//stmt.execute("DROP TABLE version");
    			//stmt.execute("DROP TABLE adapter");
    			stmt.execute(patternTable);
    			stmt.execute(adapterTable);
    			stmt.execute(versionTable);
    		} catch (SQLException e) {
    			System.out.println(e.getMessage());
    		}
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
        } finally{
        	if (connection != null){
        		try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}        	
        }
	}
	
	public static SQLConnection getInstance(){
		return sqlConnection;
	}
	
	public Connection getConnection() { 
		Connection connection = null;
		try {       
            connection = DriverManager.getConnection(url);
        } catch (SQLException e1) {
            System.out.println(e1.getMessage());
        }
        return connection;
    }
}
