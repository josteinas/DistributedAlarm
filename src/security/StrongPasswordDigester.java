package security;

import org.jasypt.util.password.StrongPasswordEncryptor;


public class StrongPasswordDigester implements PasswordDigester {
	
	private StrongPasswordEncryptor digester;
	
	public StrongPasswordDigester() {
		digester = new StrongPasswordEncryptor();
	}
	
	public String digestPassword(String password) {
		return digester.encryptPassword(password);
	}

	@Override
	public boolean checkPassword(String inputPassword, String digestedPassword) {
		return digester.checkPassword(inputPassword, digestedPassword);
	}
	
	

}
