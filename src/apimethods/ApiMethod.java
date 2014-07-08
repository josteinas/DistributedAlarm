package apimethods;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

public abstract class ApiMethod {
	public abstract JSONObject doMethod(HttpServletRequest request);
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
	
	
}
