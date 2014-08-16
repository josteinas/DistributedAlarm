package security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import apimethods.ApiConstants;

public class SessionManager {
	
	public static HttpSession getSession(HttpServletRequest request, String username){
		HttpSession session = request.getSession();
		session.setAttribute(ApiConstants.Parameters.USERNAME, username);
		return session;
	}
}
