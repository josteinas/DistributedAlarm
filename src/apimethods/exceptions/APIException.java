package apimethods.exceptions;

public class APIException extends Exception{
	private String toUser;
	private String toLog;
	

	public APIException(String toUser, String toLog) {
		super();
		this.toUser = toUser;
		this.toLog = toLog;
	}
	
	public APIException(String toUser) {
		super();
		this.toUser = toUser;
		this.toLog = toUser;
	}
	
	public String getToUser() {
		return toUser;
	}
	
	public String getToLog() {
		return toLog;
	}
	
}
