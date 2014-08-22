package apimethods;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import server.Category;
import server.User;
import apimethods.exceptions.APIException;
import database.EventsQueriesImpl;

public class GetFollowedCategories extends ProtectedApiMethod {

	private static GetFollowedCategories instance;

	public static GetFollowedCategories getInstance() {
		return instance == null ? (instance = new GetFollowedCategories())
				: instance;
	}

	@Override
	public JSONObject doMethod(HttpServletRequest request) throws APIException {
		JSONObject result = new JSONObject();

		String userName = request
				.getParameter(ApiConstants.Parameters.USERNAME);
		
		ArrayList<Category> followed = EventsQueriesImpl.getInstance().getFollowedCategories(new User(userName, userName, userName, userName));

		if (followed != null) {
			result.put(ApiConstants.Parameters.RESULT, followed);
		} else {
			throw new APIException("Unable to retrieve followed categories", String.format(
					"Unable to retrieve followed categories for %s", userName));
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
