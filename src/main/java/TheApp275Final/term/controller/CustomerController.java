package TheApp275Final.term.controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import TheApp275Final.term.model.Customer;
import TheApp275Final.term.services.CustomerService;

@Controller
@RequestMapping(value = "/user/*")
public class CustomerController {
	
	@Autowired
	CustomerService customerService;

	@Autowired
	private HttpSession httpSession;

/*	@RequestMapping(value = "")
	public ModelAndView defaultPageNoSlash(HttpServletResponse response) throws IOException {
		System.out.println("Session in Home" + httpSession.toString());
		return new ModelAndView("userhome");
	}*/

	@RequestMapping(value = "/")
	public ModelAndView defaultUserHomePage(HttpServletResponse response) throws IOException {
		Enumeration sessionVariables = httpSession.getAttributeNames();
		while (sessionVariables.hasMoreElements()) {
			String param = (String) sessionVariables.nextElement();
			System.out.println("Session Variables " + param + " and Value == " + httpSession.getAttribute(param));
		}
		System.out.println("Session in Home GET PRINCIPLE" + getPrincipal());
		Customer customer = customerService.getCustomer(getPrincipal());
		return new ModelAndView("userhome").addObject("customer",customer);
	}
	
	@RequestMapping(value = "/neworder")
	public ModelAndView newOrderPage(HttpServletResponse response) throws IOException {
		Enumeration sessionVariables = httpSession.getAttributeNames();
		while (sessionVariables.hasMoreElements()) {
			String param = (String) sessionVariables.nextElement();
			System.out.println("Session Variables " + param + " and Value == " + httpSession.getAttribute(param));
		}
		System.out.println("Session in Home GET PRINCIPLE" + getPrincipal());
		System.out.println("Session in Home" + httpSession.toString());
		return new ModelAndView("userhome");
	}


	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("getPrincipal" + principal.toString());

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

}
