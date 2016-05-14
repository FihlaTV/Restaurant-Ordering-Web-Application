package TheApp275Final.term.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

	private String errorPage;

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public MyAccessDeniedHandler() {
	}

	public MyAccessDeniedHandler(String errorPage) {
		this.errorPage = errorPage;
	}

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		String authorisedURLRedirect = "";

		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder
				.getContext().getAuthentication().getAuthorities();

		List<String> roles = new ArrayList<String>();

		for (GrantedAuthority a : authorities) {
			roles.add(a.getAuthority());
		}

		System.out.println("request.isUserInRole ==  ROLE_ADMIN == " + request.isUserInRole("ROLE_ADMIN"));
		System.out.println("request.isUserInRole ==  ROLE_USER == " + request.isUserInRole("ROLE_USER"));

		if (isAdmin(roles)) {
			authorisedURLRedirect = "/admin/getAllItems";
        } else if (isUser(roles)) {
        	authorisedURLRedirect = "/user/";
        } else {
        	authorisedURLRedirect = "/login?error";
        }

		System.out.println(accessDeniedException.getLocalizedMessage());

		// do some business logic, then redirect to errorPage url
		redirectStrategy.sendRedirect(request, response, authorisedURLRedirect);

	}

	private boolean isUser(List<String> roles) {
		if (roles.contains("ROLE_USER")) {
			return true;
		}
		return false;
	}

	private boolean isAdmin(List<String> roles) {
		if (roles.contains("ROLE_ADMIN")) {
			return true;
		}
		return false;
	}

}