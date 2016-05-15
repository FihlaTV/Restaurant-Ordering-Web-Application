package TheApp275Final.term.aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import TheApp275Final.term.model.Customer;
import TheApp275Final.term.model.Order;
import TheApp275Final.term.services.EmailService;

@Component
@Aspect
public class ConfirmationEmailAspect {
	
	@Autowired
	private HttpSession httpSession;
	
	@Autowired
	EmailService emailService;

	@Pointcut(" execution (* TheApp275Final.term.controller.CustomerController.submitOrder(..))")//(" execution (* TheApp275Final.term.config.LoginSucessHandler.findRedirectURLForUserRole(..))")//.LoginSucessHandler.handle(..))")
	public void submitOrderPointCut() {}

	@Around("submitOrderPointCut()")
	public Object sendOrderConfimationMail(ProceedingJoinPoint pjp) throws Throwable {
				
		System.out.println("pjp" + pjp.toLongString() + pjp.getArgs().toString());
		Object retVal = null;
		try {
			retVal = pjp.proceed();
			
			try{
				// Get the order from the Session
				Customer customer = (Customer) httpSession.getAttribute("customer");

				// Get the order from the Session
				Order order = (Order) httpSession.getAttribute("OrderAspect");
				
				emailService.sendOrderConfirmationMail(customer, order);
				
				System.out.println("Aspect Sending Confirmation Mail to the User");
				
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				httpSession.setAttribute("OrderAspect",null);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retVal;

	}

}