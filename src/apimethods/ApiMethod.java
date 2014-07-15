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
	
	public static ApiMethod instanceFactory(String methodName){
		if(methodName.equals(CreateUser.class.getSimpleName()))
			return CreateUser.getInstance();
		if(methodName.equals(RestorePassword.class.getSimpleName()))
			return RestorePassword.getInstance();
		if(methodName.equals(ValidateUser.class.getSimpleName()))
			return ValidateUser.getInstance();
		return null;
	}
	
	
}
