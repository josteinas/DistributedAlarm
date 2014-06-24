package database;

import java.sql.Timestamp;

public interface EventsQueries {
	
	public boolean addUser(String username, String password, String picture);
	public boolean removeUser(String username);
	public boolean addCategory(String name, float latitude, float longitude, String creator);
	public boolean removeCategory(String name);
	public boolean addHappening(String category_name, Timestamp startdate, Timestamp enddate);
	public boolean removeHappening(String category_name, Timestamp startdate, Timestamp enddate);
	public int getCategoryId(String name, float latitude, float longitude, String creator);
	
}
