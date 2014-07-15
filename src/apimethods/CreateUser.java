package apimethods;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import apimethods.exceptions.APIException;
import database.EventsQueriesImpl;
import security.StrongPasswordDigester;
import server.User;


public class CreateUser extends ApiMethod{
	private static CreateUser instance;
	public static CreateUser getInstance(){
		return instance == null ?( instance = new CreateUser() ): instance;
	}
	
	@Override
	public JSONObject doMethod(HttpServletRequest request) throws APIException{
		JSONObject result = new JSONObject();
		
		String userName = request.getParameter(ApiConstants.Parameters.USERNAME);
		String password = request.getParameter(ApiConstants.Parameters.PASSWORD);
		String imgUrl = request.getParameter(ApiConstants.Parameters.IMAGE_URL);
		String email = request.getParameter(ApiConstants.Parameters.EMAIL);
		
		password = new StrongPasswordDigester().digestPassword(password);
		User user = new User(userName, password, imgUrl, email);
		
		if(EventsQueriesImpl.getInstance().addUser(user)){
			result.append(ApiConstants.Parameters.USERNAME, userName);
		}else{
			throw new APIException("Unable to create user",  String.format("Unable to create user %s", user.toString()));
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
