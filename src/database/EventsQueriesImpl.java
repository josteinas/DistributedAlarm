package database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import server.Category;
import server.Happening;
import server.User;

public class EventsQueriesImpl implements EventsQueries {
	
	private Properties dbProperties;
	private EventsDBConnector connector;
	
	private HashMap<String, User> users = new HashMap<String,User>();
	private HashMap<Long, Category> categories = new HashMap<Long,Category>();
	
	
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
	public boolean addUser(User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		String picture = user.getImgURL();
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
	public boolean removeUser(User user) {
		String username = user.getUsername();
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
	public boolean addCategory(Category category) {
		String name = category.getName();
		float longitude = category.getLongitude();
		float latitude = category.getLatitude();
		String creator = category.getCreator().getUsername();
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
	public boolean removeCategory(Category category) {
		long id = category.getId();
		try {
			connector.customModification("delete from Category where category_id = "+id+";");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addHappening(Happening happening) {
		long starttime = happening.getStarttime();
		long endtime = happening.getEndtime();
		long categoryId = happening.getCategory().getId();
		boolean privateHappening = happening.isPrivate();
		User creator = happening.getCreator();
		try {
			connector.customModification("insert into Happening (starttime,endtime,category_id, private, creator) values ('"+starttime+"', '"+endtime+"', '"+ categoryId+ "', '"+privateHappening+"', '"+creator.getUsername()+"');");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeHappening(Happening happening) {
		long happeningId = happening.getId();
		long categoryId = happening.getCategory().getId();
		try {
			connector.customModification("delete from Happening where category_id = "+categoryId+" and happening_id = " + happeningId + " ;");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addFriends(User first, User second) {
		String username1 = first.getUsername();
		String username2 = second.getUsername();
		try {
			connector.customModification("insert into Friends (user1,user2) values ('"+username1+"', '"+username2+"');");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeFriends(User first, User second) {
		String username1 = first.getUsername();
		String username2 = second.getUsername();
		try {
			connector.customModification("delete from Friends where (user1 = "+username1+" and user2 = " + username2 + ") or (user1 = "+username2+" and user2 = " + username1 + ") ;");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateUser(User user) {
		String username = user.getUsername();
		try {
			connector.customModification("UPDATE User SET password='"+user.getPassword()+"', picture='"+user.getImgURL()+"' WHERE username='"+username+"' ;");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateCategory(Category category) {
		long id = category.getId();
		try {
			connector.customModification("UPDATE Category SET name='"+category.getName()+"', lat ='"+category.getLatitude()+"', lon ='"+category.getLongitude()+"', private ='"+category.isPrivate()+"' WHERE category_id ='"+id+"' ;");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateHappening(Happening happening) {
		long id = happening.getId();
		try {
			connector.customModification("UPDATE Category SET starttime='"+happening.getStarttime()+"', endtime ='"+happening.getEndtime()+"', private ='"+happening.isPrivate()+"' WHERE happening_id ='"+id+"' ;");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ArrayList<User> getUsers() {
		ArrayList<User> users = new ArrayList<User>();
		this.users.clear();
		try {
			ResultSet rs = connector.customQuery("select * from User");
			while (rs.next()) {
				User user = new User(rs.getString("username"), rs.getString("password"), rs.getString("picture"));
				users.add(user);
				this.users.put(user.getUsername(), user);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public ArrayList<Category> getCategories() {
		ArrayList<Category> categories = new ArrayList<Category>();
		//temporary solution to outdated user-hashmap, in case creator only exists in db
		getUsers();
		this.categories.clear();
		try {
			ResultSet rs = connector.customQuery("select * from Category");
			while (rs.next()) {
				User creator = users.get(rs.getString("creator"));
				Category category = new Category(rs.getString("name"), rs.getFloat("lat"), rs.getFloat("lon"),creator , rs.getBoolean("private"), rs.getLong("category_id"));
				categories.add(category);
				this.categories.put(category.getId(), category);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categories;
	}

	@Override
	public ArrayList<Happening> getHappenings() {
		ArrayList<Happening> happenings = new ArrayList<Happening>();
		//temporary solution to outdated user-hashmap, in case creator only exists in db
		getCategories();
		try {
			ResultSet rs = connector.customQuery("select * from Category");
			while (rs.next()) {
				User creator = users.get(rs.getString("creator"));
				Happening happening = new Happening(categories.get(rs.getString("creator")), rs.getLong("starttime"), rs.getLong("endtime"), creator, rs.getBoolean("private"), rs.getLong("happening_id"));
				happenings.add(happening);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return happenings;
	}
}
