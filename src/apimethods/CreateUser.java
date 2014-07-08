package apimethods;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import database.EventsQueriesImpl;
import server.User;


public class CreateUser extends ApiMethod{
	private static CreateUser instance;
	public static CreateUser getInstance(){
		return instance == null ?( instance = new CreateUser() ): instance;
	}
	
	@Override
	public JSONObject doMethod(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		
		String userName = request.getParameter(ApiConstants.Parameters.USERNAME);
		String password = request.getParameter(ApiConstants.Parameters.PASSWORD);
		String imgUrl = request.getParameter(ApiConstants.Parameters.IMAGE_URL);
		
		if(EventsQueriesImpl.getInstance().addUser(new User(userName, password, imgUrl))){
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
