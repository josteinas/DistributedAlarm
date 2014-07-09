package security;

public interface PasswordDigester {
	public String digestPassword(String password);
	public boolean checkPassword(String inputPassword, String digestedPassword);
}
