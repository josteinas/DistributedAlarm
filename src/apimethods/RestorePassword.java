package apimethods;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import apimethods.exceptions.APIException;
import security.StrongPasswordDigester;
import server.User;
import util.DAUtils;
import database.EventsQueries;
import database.EventsQueriesImpl;

public class RestorePassword extends ApiMethod {
	private static RestorePassword instance;
	public static RestorePassword getInstance(){
		return instance == null ?( instance = new RestorePassword() ): instance;
	}

	@Override
	public JSONObject doMethod(HttpServletRequest request) throws APIException{
		JSONObject result = new JSONObject();
		EventsQueries eq = EventsQueriesImpl.getInstance();
		
		String userName = request.getParameter(ApiConstants.Parameters.USERNAME);
		
		User user = eq.getUser(userName);
		if(user == null){
			throw new APIException("Could not find user");
		}
		String password = DAUtils.randString(16);
		
		String passwordHash = new StrongPasswordDigester().digestPassword(password);
		user.setPassword(passwordHash);
		//TODO send this to the user. 
		if(eq.updateUser(user)){
			result.append(ApiConstants.Parameters.USERNAME, userName);
		}else{
			throw new APIException("Unable to update user: " + user);
		}
		return result;
	}

	@Override
	public Set<String> getRequiredParameters() {
		Set<String> req = new HashSet<>();
		req.add(ApiConstants.Parameters.USERNAME);
		return req;
	}

}
