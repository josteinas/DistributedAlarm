package database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	
	private static EventsQueries instance;
	public static EventsQueries getInstance(){
		return instance == null ?( instance = new EventsQueriesImpl() ): instance;
	}
	
	
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
			System.out.println(this.getClass().getClassLoader().getResourceAsStream("database.properties"));
			input = this.getClass().getClassLoader().getResourceAsStream("database.properties");
			
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
		String email = user.getEmail();
		
		Connection conn = connector.getConnection();
		try {
			java.sql.PreparedStatement pstmt = conn.prepareStatement("insert into User (username, password, picture, email) values (?,?,?,?)");
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, picture);
			pstmt.setString(4, email);
			pstmt.execute();
			return true;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean removeUser(User user) {
		String username = user.getUsername();
		
		Connection conn = connector.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("delete from User where username = (?);");
			pstmt.setString(1, username);
			pstmt.execute();
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
		
		Connection conn = connector.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into Category (name, lat, lon, creator) values (?,?,?,?);");
			pstmt.setString(1, name);
			pstmt.setFloat(2, latitude);
			pstmt.setFloat(3, longitude);
			pstmt.setString(4, creator);
			pstmt.execute();
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
		Connection conn = connector.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("delete from Category where category_id = (?);");
			pstmt.setLong(1, id);
			pstmt.execute();
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
		Connection conn = connector.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into Happening (starttime,endtime,category_id, private, creator) values (?,?,?,?,?);");
			pstmt.setLong(1, starttime);
			pstmt.setLong(2, endtime);
			pstmt.setLong(3, categoryId);
			pstmt.setBoolean(4, privateHappening);
			pstmt.setString(5, creator.getUsername());
			pstmt.execute();
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
		Connection conn = connector.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("delete from Happening where category_id = (?) and happening_id = (?) ;");
			pstmt.setLong(1, categoryId);
			pstmt.setLong(2, happeningId);
			pstmt.execute();
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
		Connection conn = connector.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into Friends (user1,user2) values (?,?);");
			pstmt.setString(1, username1);
			pstmt.setString(2, username2);
			pstmt.execute();
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
		Connection conn = connector.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("delete from Friends where (user1 = (?) and user2 = (?)) or (user1 = (?) and user2 = (?)) ;");
			pstmt.setString(1, username1);
			pstmt.setString(2, username2);
			pstmt.setString(3, username2);
			pstmt.setString(4, username1);
			pstmt.execute();
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
		Connection conn = connector.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("UPDATE User SET password=(?), picture=(?), email=(?) WHERE username=(?) ;");
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getImgURL());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, username);
			pstmt.execute();
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
		Connection conn = connector.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("UPDATE Category SET name=(?), lat =(?), lon =(?), private =(?) WHERE category_id =(?) ;");
			pstmt.setString(1, category.getName());
			pstmt.setFloat(2, category.getLatitude());
			pstmt.setFloat(3, category.getLongitude());
			pstmt.setBoolean(4, category.isPrivate());
			pstmt.setLong(5, id);
			pstmt.execute();
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
		Connection conn = connector.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("UPDATE Category SET starttime=(?), endtime =(?), private =(?) WHERE happening_id =(?);");
			pstmt.setLong(1, happening.getStarttime());
			pstmt.setLong(2, happening.getEndtime());
			pstmt.setBoolean(3, happening.isPrivate());
			pstmt.setLong(4, id);
			pstmt.execute();
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
				User user = new User(rs.getString("username"), rs.getString("password"), rs.getString("picture"), rs.getString("email"));
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

	@Override
	public boolean addFollowed(User user, Category category) {
		
		Connection conn = connector.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("insert into Follows (username, category_id) values (?,?);");
			pstmt.setString(1, user.getUsername());
			pstmt.setLong(2, category.getId());
			pstmt.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeFollowed(User user, Category category) {
		String username = user.getUsername();
		long categoryId = category.getId();
		
		Connection conn = connector.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("delete from Follows where category_id = (?) and username =(?);");
			pstmt.setLong(1, categoryId);
			pstmt.setString(2, username);
			pstmt.execute();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ArrayList<User> getFriends(User user) {
		String username = user.getUsername();
		ArrayList<User> friends = new ArrayList<User>();
		
		try {
			ResultSet rs = connector.customQuery("select * from Friends where (user1 = '"+username+"') or (user2 = '"+username+"');");
			while (rs.next()) {
				if(rs.getString("user1").equals(username)) {
					friends.add(users.get(rs.getString("user2")));
				} else {
					friends.add(users.get(rs.getString("user1")));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return friends;
	}

	@Override
	public ArrayList<Category> getFollowedCategories(User user) {
		String username = user.getUsername();
		ArrayList<Category> followed = new ArrayList<Category>();
		
		try {
			ResultSet rs = connector.customQuery("select category_id from Follows where (username = '"+username+"');");
			while (rs.next()) {
				followed.add(categories.get(rs.getLong("category_id")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return followed;
	}


	@Override
	public User getUser(String username) {
		//TODO query one specific user instead of all??
		getUsers();
		return users.get(username);
	}
}
