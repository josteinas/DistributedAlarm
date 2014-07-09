package database;

import java.util.ArrayList;

import server.Category;
import server.Happening;
import server.User;

public interface EventsQueries {
	
	public boolean addUser(User user);
	public boolean removeUser(User user);
	
	public boolean addCategory(Category category);
	public boolean removeCategory(Category category);
	
	public boolean addHappening(Happening happening);
	public boolean removeHappening(Happening happening);
	
	public boolean addFriends(User first, User second);
	public boolean removeFriends(User first, User second);
	
	public boolean addFollowed(User user, Category category);
	public boolean removeFollowed(User user, Category category);
	
	public boolean updateUser(User user);
	public boolean updateCategory(Category category);
	public boolean updateHappening(Happening happening);
	
	public ArrayList<User> getUsers();
	public ArrayList<Category> getCategories();
	public ArrayList<Happening> getHappenings();
	public ArrayList<User> getFriends(User user);
	public ArrayList<Category> getFollowedCategories(User user);
	
	public User getUser(String username);
}
