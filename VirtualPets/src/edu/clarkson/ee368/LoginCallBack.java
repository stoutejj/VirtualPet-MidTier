package edu.clarkson.ee368;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 * Servlet implementation class LoginCallBack
 */
@WebServlet("/LoginCallBack")
public class LoginCallBack extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String clientId = "781334686465-1njdomccsf4h3fvucd92a5nrpr7igjc2.apps.googleusercontent.com";
	String clientSecret = "TTsnlhrInze8LnnftySkyaBN";
	private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginCallBack() {
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
		OAuthService service = new ServiceBuilder()
        	.provider(GoogleApi.class)
        	.apiKey(clientId)
        	.apiSecret(clientSecret)
        	.scope(SCOPE)
        	.build();
		Token requestToken = (Token)request.getSession(false).getAttribute("token");

		Verifier v = new Verifier(request.getParameter("oauth_verifier"));
		Token accessToken = service.getAccessToken(requestToken, v);
		OAuthRequest oauthRequest = new OAuthRequest(Verb.POST, "http://localhost:9080/VirtualPets/DummyURL");
		service.signRequest(accessToken, oauthRequest); 
		Response oauthResponse = oauthRequest.send();
		response.getOutputStream().println(oauthResponse.getBody());
	}

}
