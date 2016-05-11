package TheApp275Final.term.dao;

import java.util.List;

import TheApp275Final.term.model.Customer;
import TheApp275Final.term.model.Order;

public interface ICustomerDao {
	
	public int saveCustomer(Customer profile);

	public boolean deleteProfile(int id);

	Customer getCustomer(String username);

	Customer updateCustomer(Customer customer);

	public List<Order> getListOfOrder(int id);

}
