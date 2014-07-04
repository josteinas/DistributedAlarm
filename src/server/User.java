package server;

public class User {
	
	private String username;
	private String password;
	private String imgURL;
	
	public User(String username, String password, String imgURL) {
		this.username = username;
		this.password = password;
		this.imgURL = imgURL;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password
				+ ", imgURL=" + imgURL + "]";
	}
	
	
}
