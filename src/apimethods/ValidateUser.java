package apimethods;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import security.StrongPasswordDigester;
import server.User;
import database.EventsQueriesImpl;

public class ValidateUser extends ApiMethod {
	
	private static ValidateUser instance;
	
	public static ValidateUser getInstance(){
		return instance == null ?( instance = new ValidateUser() ): instance;
	}

	@Override
	public JSONObject doMethod(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		
		String userName = request.getParameter(ApiConstants.Parameters.USERNAME);
		String password = request.getParameter(ApiConstants.Parameters.PASSWORD);
		
		User user = EventsQueriesImpl.getInstance().getUser(userName);
		
		if(user != null && new StrongPasswordDigester().checkPassword(password, user.getPassword())){
			result.append(ApiConstants.Parameters.USERNAME, userName);
			result.append(ApiConstants.Parameters.SUCCESS, true);
		}else{
			result.append(ApiConstants.Parameters.SUCCESS, false);
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
