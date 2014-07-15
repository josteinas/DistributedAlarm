package apimethods;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import apimethods.exceptions.APIException;
import security.StrongPasswordDigester;
import server.User;
import database.EventsQueriesImpl;

public class ValidateUser extends ApiMethod {
	
	private static ValidateUser instance;
	
	public static ValidateUser getInstance(){
		return instance == null ?( instance = new ValidateUser() ): instance;
	}

	@Override
	public JSONObject doMethod(HttpServletRequest request) throws APIException{
		
		
		JSONObject result = new JSONObject();
		
		String userName = request.getParameter(ApiConstants.Parameters.USERNAME);
		String password = request.getParameter(ApiConstants.Parameters.PASSWORD);
		
		User user = EventsQueriesImpl.getInstance().getUser(userName);
		if(user == null){
			throw new APIException("Invalid username");
		}
		
		if(new StrongPasswordDigester().checkPassword(password, user.getPassword())){
			result.put(ApiConstants.Parameters.USERNAME, userName);
			result.put(ApiConstants.Parameters.VALIDATION_SUCCEEDED,true);
			//TODO return session key and store it somewhere
		}else{
			result.put(ApiConstants.Parameters.USERNAME, userName);
			result.put(ApiConstants.Parameters.VALIDATION_SUCCEEDED,false);
		}
		return result;
	}

	@Override
	public Set<String> getRequiredParameters() {
		Set<String> req = new HashSet<>();
		req.add(ApiConstants.Parameters.USERNAME);
		req.add(ApiConstants.Parameters.PASSWORD);
		return req;
	}

}
