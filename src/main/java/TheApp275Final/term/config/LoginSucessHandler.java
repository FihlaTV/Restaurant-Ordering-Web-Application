package TheApp275Final.term.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import TheApp275Final.term.model.Customer;
import TheApp275Final.term.services.CustomerService;
 
@Component
public class LoginSucessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	@Autowired
	private CustomerService customerService;
	
	private Customer customer;
 
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
 
    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
    	
    	//Get Principal User from Authentication Object
    	Object principal = authentication.getPrincipal();
    	String username = ((UserDetails)principal).getUsername();
    	
    	//Update the Session to Store The Customer Object
    	HttpSession session = request.getSession();
    	//session.invalidate();
    	session.setAttribute("username",username);
    	session = request.getSession(true);
    	if(username != null && username != ""){
    		customer = customerService.getCustomer(username);
    		session.setAttribute("customer", customer);
    	}
    	session.setMaxInactiveInterval(99999);
    	
        String targetUrl = findRedirectURLForUserRole(authentication);
 
        if (response.isCommitted()) {
            System.out.println("Can't redirect");
            return;
        }
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
 
    /*
     * This method extracts the roles of currently logged-in user and returns
     * appropriate URL according to his/her role.
     */
    protected String findRedirectURLForUserRole(Authentication authentication) {
        String url = "";
 
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
 
        List<String> roles = new ArrayList<String>();
 
        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }
 
        if (isDba(roles)) {
            url = "/db";
        } else if (isAdmin(roles)) {
            url = "/admin";
        } else if (isUser(roles)) {
            url = "/user/";
        } else {
            url = "/accessDenied";
        }
        System.out.println("determineTargetUrl ==> " + url);
        return url;
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
 
    private boolean isDba(List<String> roles) {
        if (roles.contains("ROLE_DBA")) {
            return true;
        }
        return false;
    }
 
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
 
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
    
	private String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("getPrincipal" + principal.toString());
		
		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
 
}