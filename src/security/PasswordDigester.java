package security;

public interface PasswordDigester {
	public String digestPassword(String password);
	public String checkPassword(String inputPassword, String digestedPassword);
}
