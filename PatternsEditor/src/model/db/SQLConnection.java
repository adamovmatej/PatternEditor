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
            System.out.println(url);
            
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
            String dropTable = "DROP TABLE pattern";
    		String patternTable = "CREATE TABLE IF NOT EXISTS pattern (\n"
                    + "	name TEXT PRIMARY KEY,\n"
                    + " diagramId INTEGER,\n"
                    + " description TEXT,\n"
                    + " FOREIGN KEY(diagramId) REFERENCES diagram(id)"
                    + ");";
    		String diagramTable = "CREATE TABLE IF NOT EXISTS diagram (\n"
                    + "	id INTEGER PRIMARY KEY,\n"
    				+ " patternId INTEGER,\n"
                    + " FOREIGN KEY(patternId) REFERENCES pattern(id)"
                    + ");";
    		String adapterTable = "CREATE TABLE IF NOT EXISTS adapter (\n"
    				+ " id INTEGER PRIMARY KEY,\n"
    				+ " mainPatternId INTEGER,\n"
    				+ " secondaryPatternId INTEGER,\n"
    	            + " FOREIGN KEY(mainPatternId) REFERENCES pattern(id),\n"
    	            + " FOREIGN KEY(secondaryPatternId) REFERENCES pattern(id)"
    	            + ");";
    		String versionTable = "CREATE TABLE IF NOT EXISTS version (\n"
    				+ " id INTEGER PRIMARY KEY,\n"
    				+ " mainPatternId INTEGER,\n"
    				+ " secondaryPatternId INTEGER,\n"
    	            + " FOREIGN KEY(mainPatternId) REFERENCES pattern(id),\n"
    	            + " FOREIGN KEY(secondaryPatternId) REFERENCES pattern(id)"
    	            + ");";
    		
    		Statement stmt;
    		try {
    			stmt = connection.createStatement();
    			//stmt.execute(dropTable);
    			stmt.execute(patternTable);
    			stmt.execute(diagramTable);
    			stmt.execute(adapterTable);
    			stmt.execute(versionTable);
    		} catch (SQLException e) {
    			System.out.println(e.getMessage());
    		}
            System.out.println("Tables created.");
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
