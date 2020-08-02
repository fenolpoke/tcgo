package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connector {
	public static Connector instance;
	
	static Connection connection;	
	int userId;
	
	private Connector() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/TCGO","root","");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Connector getInstance(){
		if(instance == null) instance = new Connector();
		return instance;
	}
	
	public ResultSet executeQuery(String s) throws SQLException{
		return connection.createStatement(1004,1008).executeQuery(s);
	}
	public void executeUpdate(String s) throws SQLException{
		connection.createStatement(1004,1008).executeUpdate(s);
	}
	public PreparedStatement getPreparedStatement(String statement){
		try {
			return connection.prepareStatement(statement);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public int getUserId(){return userId;}
	public void setUserId(int userId){this.userId = userId;}
}
