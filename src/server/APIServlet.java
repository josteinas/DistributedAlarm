package server;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import apimethods.ApiConstants;
import apimethods.ApiMethod;
import apimethods.CreateUser;
import apimethods.ValidateUser;
import apimethods.exceptions.APIException;

/**
 * Servlet implementation class APIServlet
 */
@WebServlet("/APIServlet")
public class APIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public APIServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * hei jostein
	 * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect("http://www.vg.no");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		JSONObject result;
		try {
			String methodName = resolveMethodName(request.getRequestURL()
					.toString());
			ApiMethod method = ApiMethod.instanceFactory(methodName);
			result = method.doMethod(request);
			result.append(ApiConstants.Parameters.SUCCESS, true);
		} catch (APIException e) {
			result = new JSONObject();
			result.append(ApiConstants.Parameters.SUCCESS, false);
			System.out.println(e.getToUser());
			e.printStackTrace();
		}
		response.getWriter().write(result.toString());
	}

	private String resolveMethodName(String url) {
		String[] splitUrl = url.split("/");
		return splitUrl[splitUrl.length - 1];
	}

}
