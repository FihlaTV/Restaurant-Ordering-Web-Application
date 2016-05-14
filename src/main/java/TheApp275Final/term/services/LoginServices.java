package TheApp275Final.term.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import TheApp275Final.term.dao.ICustomerDao;
import TheApp275Final.term.model.Customer;

@Service
@Transactional(isolation=Isolation.REPEATABLE_READ,transactionManager="transactionManager")
public class LoginServices {
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private ICustomerDao customerDao;

	@Transactional(propagation=Propagation.REQUIRED)
	public int createCustomer(Customer customer) {

		int statusCode;

		try {
			if (customerDao.saveCustomer(customer) == 0) {
				statusCode = 0;
			} else {
				emailService.sendCustomerRegistrationConfirmationMail(customer);
				statusCode = 1;
			}
		} catch (Exception e) {
			System.out.println("Error::LoginController::signUpForm::" + e.getMessage());
			e.printStackTrace();
			statusCode = 2;
		}
		
		return statusCode;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public int activateAccount(String token, String email) {
		int statusCode = 0;
		Customer customer = customerDao.getCustomer(email);
		if(customer.getUserAccessCode().equals(token)){
			customer.setEnabled(true);
			customer.setUserAccessCode("");
			customerDao.updateCustomer(customer);
			statusCode = 1;
		}
		return statusCode;
	}
}
