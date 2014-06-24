package database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

public class EventsQueriesImpl implements EventsQueries {
	
	private Properties dbProperties;
	private EventsDBConnector connector;
	
	public EventsQueriesImpl() {
		dbProperties = readProperties();
		try {
			connector = new EventsDBConnector(dbProperties);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private Properties readProperties() {
		Properties properties = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("database.properties");
			
			properties.load(input);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return properties;
	}

	@Override
	public boolean addUser(String username, String password, String picture) {
		try {
			connector.customModification("insert into User values ('"+username+"', '"+password+"', '"+ picture+ "');");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public boolean removeUser(String username) {
		try {
			connector.customModification("delete from User where username = "+username+";");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addCategory(String name, float latitude, float longitude, String creator) {
		try {
			connector.customModification("insert into Category (name, lat, lon, creator) values ('"+name+"', '"+latitude+"', '"+ longitude+ "','"+creator+"');");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeCategory(String name) {
		try {
			connector.customModification("delete from Category where name = "+name+";");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addHappening(String category_name, Timestamp startdate,
			Timestamp enddate) {
		try {
			connector.customModification("insert into Happening (starttime,endtime,category_name) values ('"+startdate+"', '"+enddate+"', '"+ category_name+ "');");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeHappening(String category_name, Timestamp startdate,
			Timestamp enddate) {
		try {
			connector.customModification("delete from Happening where category_name = "+category_name+" and starttime = "+DateConverter.toMySQLDatetime(startdate)+" and endtime = "+DateConverter.toMySQLDatetime(enddate)+";");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public int getCategoryId(String name, float latitude, float longitude,
			String creator) {
		try {
			ResultSet rs = connector.customQuery("select category_id from Category where name = "+name+" and lat = "+latitude+" and lon = "+longitude+" and creator = "+creator+";");
			rs.first();
			System.out.println(rs.getInt(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
