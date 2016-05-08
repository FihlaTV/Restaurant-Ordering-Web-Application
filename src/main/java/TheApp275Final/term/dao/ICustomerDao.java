package TheApp275Final.term.dao;

import TheApp275Final.term.model.Customer;

public interface ICustomerDao {
	
	public int saveCustomer(Customer profile);

	public boolean deleteProfile(int id);

	Customer getCustomer(String username);

	Customer updateCustomer(Customer customer);

}
