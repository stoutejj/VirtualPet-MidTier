package edu.clarkson.ee368;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.GoogleApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/LoginFilter")
public class LoginFilter implements Filter {
	private FilterConfig fcon = null;
	private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email";
	private static final String AUTHORIZE_URL = "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=";
	String clientId = "781334686465-1njdomccsf4h3fvucd92a5nrpr7igjc2.apps.googleusercontent.com";
	String clientSecret = "TTsnlhrInze8LnnftySkyaBN";
    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		fcon = null;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (fcon == null) 
        	return;
		HttpServletResponse httpres = (HttpServletResponse) response;
		HttpServletRequest httpreq = (HttpServletRequest) request;
		HttpSession session = httpreq.getSession(true);
		if (httpreq.getHeader("Authorization")!=null) {
			chain.doFilter(request, response);
		} else {
			OAuthService service = new ServiceBuilder()
        	.provider(GoogleApi.class)
        	.apiKey(clientId)
        	.apiSecret(clientSecret)
        	.scope(SCOPE)
        	.callback("http://localhost:9080/VirtualPets/LoginCallBack")
        	.build();
			
			Token requestToken = service.getRequestToken();
			session.setAttribute("token", requestToken);
			httpres.sendRedirect(AUTHORIZE_URL + requestToken.getToken());
			
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		fcon = fConfig;
	}

}
