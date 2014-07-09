package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class EventsDBConnector {

	private Connection conn = null;
	private String url;
	private String user;
	private String password;

	public EventsDBConnector(Properties properties) throws SQLException {
		url = properties.getProperty("url");
		user = properties.getProperty("user");
		password = properties.getProperty("password");
		//Temp fix for unloaded driver
		try { 
			com.mysql.jdbc.Driver d = new com.mysql.jdbc.Driver();
			System.out.println("Driver: " + d);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
		conn = DriverManager.getConnection(url, user, password);
		System.out.println("Database connection established");
	}

	public ResultSet customQuery(String sql) throws SQLException {
		Statement statement = conn.createStatement();
		return statement.executeQuery(sql);
	}

	public boolean customModification(String sql) throws SQLException {
		Statement statement = conn.createStatement();
		System.out.println(sql);
		return statement.execute(sql);
	}

}
