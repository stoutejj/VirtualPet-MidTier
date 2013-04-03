package edu.clarkson.ee368;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;


/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SCOPE = "https://docs.google.com/feeds/";
	private static final String AUTHORIZE_URL = "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=";
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String clientId = "781334686465-1njdomccsf4h3fvucd92a5nrpr7igjc2.apps.googleusercontent.com";
		String clientSecret = "TTsnlhrInze8LnnftySkyaBN";
		OAuthService service = new ServiceBuilder()
        	.provider(GoogleApi.class)
        	.apiKey(clientId)
        	.apiSecret(clientSecret)
        	.scope(SCOPE)
        	.build();
		
		Token requestToken = service.getRequestToken();
		response.sendRedirect(AUTHORIZE_URL + requestToken.getToken());	
	}

}
