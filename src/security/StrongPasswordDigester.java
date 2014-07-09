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
	public String checkPassword(String inputPassword, String digestedPassword) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
