package TheApp275Final.term.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import TheApp275Final.term.dao.CustomerDao;
import TheApp275Final.term.dao.ICustomerDao;
import TheApp275Final.term.model.Customer;
import TheApp275Final.term.services.LoginServices;

@Controller
public class LoginController {
	
	@Autowired 
	 private HttpSession httpSession;
	
	@Autowired
	LoginServices loginServices;
	
	@RequestMapping(value="/login")
	public ModelAndView loginPage(HttpServletRequest request ,HttpServletResponse response) throws IOException {
		Object principal = request.getUserPrincipal();
		if(principal == null){
			return new ModelAndView("login");
		}else{
			response.sendRedirect("user/");
			return null;
		}
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response,HttpSession httpSession) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        //httpSession.invalidate();
        return "redirect:/login?logout";
    }
	
	@RequestMapping(value="/signup",method=RequestMethod.GET)
	public ModelAndView signUpPage(HttpServletRequest request ,HttpServletResponse response) throws IOException {
		return new ModelAndView("signup");
	}
	
	@RequestMapping(value="/signup",method=RequestMethod.POST)
	public void signUpForm(HttpServletRequest request ,HttpServletResponse response,
			 @RequestParam(value = "firstname", required = true) String firstname,
			 @RequestParam(value = "lastname", required = true) String lastname,
			 @RequestParam(value = "username", required = true) String username,
			 @RequestParam(value = "password", required = true) String password) throws IOException {
		
		//Default Redirect Page
		String redirectUrl = "login?signup";
		
		//Generate Random Access Code for New Customer
		String userAccessCode = UUID.randomUUID().toString().replaceAll("-", "");
		
		System.out.println("Customer Registration");
		System.out.println("Firstname ==> " + firstname);
		System.out.println("Lastname ==> " + lastname);
		System.out.println("Username ==> " + username);
		System.out.println("Password ==> " + password);
		System.out.println("userAccessCode ==> " + userAccessCode);
		
		//Create a New Customer Object
		Customer customer = new Customer(firstname, lastname, username, password,userAccessCode);
		
		int statusCode = loginServices.createCustomer(customer);
		
		if(statusCode == 0){
			redirectUrl = "signup?signuperror&firstname=" + customer.getFirstname() + "&lastname=" + customer.getLastname();
			redirectUrl += "&signuperrormsg=Username is already registered as part of another Account, Please use Another Username!!";
		}else if(statusCode == 1){
			redirectUrl = "login?signup";
		}else{
			redirectUrl = "signup?signuperror&firstname=" + customer.getFirstname() + "&lastname=" + customer.getLastname() + "&username=" + customer.getUsername();
			redirectUrl += "&signuperrormsg=There was an error Processing your request!!Please Try Again...";
		}
		
		response.sendRedirect(redirectUrl);
	}
	
	@RequestMapping(value="/activateaccount",method=RequestMethod.GET)
	public ModelAndView activateaccount(HttpServletRequest request ,HttpServletResponse response,
			@RequestParam(value = "token", required = false) String token,
			@RequestParam(value = "email", required = false) String email) throws IOException {
		
		System.out.println("Token ==> " + token);
		System.out.println("email ==> " + email);
		
		int statusCode;
		
		if(token != null && token != "" && email != null && email != ""){
			
			String redirectURL = "";
			statusCode = loginServices.activateAccount(token,email);
			
			if(statusCode == 0){
				redirectURL = "activateaccount?error&errormsg=Invalid Email Address or Expired Token Number..";
			}
			else{
				redirectURL = "activateaccount?activated";
			}
			response.sendRedirect(redirectURL);
			return null;
		}else{
			return new ModelAndView("activateaccount");
		}
	}
}
