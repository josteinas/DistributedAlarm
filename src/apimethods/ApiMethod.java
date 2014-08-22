package apimethods;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import apimethods.exceptions.APIException;

public abstract class ApiMethod {
	public abstract JSONObject doMethod(HttpServletRequest request) throws APIException;
	public abstract Set<String> getRequiredParameters();
	
	public  Set<String> verifyParameters(HttpServletRequest request){
		Set<String> resultSet = new HashSet<>();
		for(String key : getRequiredParameters()){
			String parameter = request.getParameter(key);
			if(parameter == null)
				resultSet.add(key);
		}
		return resultSet;
	}
	
	public static ApiMethod instanceFactory(String methodName) throws APIException{
		if(methodName.equalsIgnoreCase(CreateUser.class.getSimpleName()))
			return CreateUser.getInstance();
		if(methodName.equalsIgnoreCase(RestorePassword.class.getSimpleName()))
			return RestorePassword.getInstance();
		if(methodName.equalsIgnoreCase(ValidateUser.class.getSimpleName()))
			return ValidateUser.getInstance();
		if(methodName.equalsIgnoreCase(GetFollowedCategories.class.getSimpleName()))
			return GetFollowedCategories.getInstance();
		
		throw new APIException(String.format("No such method %s", methodName));
	}
	
	
}
