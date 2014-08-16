package apimethods;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class ProtectedApiMethod extends ApiMethod{
	public boolean allowAction(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session == null)
			return false;
		return true;
	}
}
