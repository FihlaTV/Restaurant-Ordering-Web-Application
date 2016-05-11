package TheApp275Final.term.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import TheApp275Final.term.dao.ICustomerDao;
import TheApp275Final.term.model.Customer;
import TheApp275Final.term.model.Order;

@Service
@Transactional(isolation=Isolation.REPEATABLE_READ,transactionManager="transactionManager")
public class CustomerService {
	
	@Autowired
	private ICustomerDao customerDao;
	
	@Transactional(propagation=Propagation.REQUIRED)
	public Customer getCustomer(String email) {
		Customer customer = customerDao.getCustomer(email);
		return customer;
	}
	
	@Transactional
	public List<Order> getListOfOrder(long l){
		List<Order> orders = customerDao.getListOfOrder(l);
		return orders;
	}
	
}
