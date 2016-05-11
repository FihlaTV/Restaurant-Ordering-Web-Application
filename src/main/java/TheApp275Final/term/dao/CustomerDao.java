package TheApp275Final.term.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import TheApp275Final.term.model.Customer;
import TheApp275Final.term.model.Item;
import TheApp275Final.term.model.Order;

@Repository
@Transactional
public class CustomerDao implements ICustomerDao {

	Transaction transaction;

	@Autowired
	Session session;

	@Override
	@Transactional(propagation = Propagation.NESTED)
	public int saveCustomer(Customer customer) {
		int returnVal = 0;

		// Check for Existing Customers
		Customer custExists = getCustomer(customer.getUsername());
		System.out.println(custExists);

		// If not Existing Customer then create one,
		if (custExists == null) {
			session.clear();
			// transaction = session.getTransaction();
			try {
				// transaction.begin();
				session.save(customer);
				session.flush();
				// transaction.commit();
				returnVal = 1;
				System.out.println("Customer Created Sucessfully!!");
			} catch (Exception e) {
				// transaction.rollback();
				System.out.println("Error::CustomerDao::saveCustomer::" + e.getMessage());
				e.printStackTrace();
				throw e;
			} finally {
				// session.close();
			}
		}
		return returnVal;
	}

	@Override
	public Customer getCustomer(String username) {

		// Check for Existing Customers
		SQLQuery query = session.createSQLQuery("Select * from users where username = ? ");
		query.addEntity(Customer.class);
		Customer custExists = (Customer) query.setParameter(0, username).uniqueResult();
		// session.close();
		return custExists;

	}

	@Override
	@Transactional(propagation = Propagation.NESTED)
	public Customer updateCustomer(Customer customer) {
		session.clear();
		session.update(customer);
		session.flush();
		// session.close();
		return null;
	}

	@Override
	public boolean deleteProfile(int id) {
		return false;
	}

	@Override
	public List<Order> getListOfOrder(long id) {
		Transaction tx = null;
		try {
			session.clear();
			//tx = session.beginTransaction();
			String sql = "SELECT * FROM ORDERS where ORDER_USER_ID= :order_user_id";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Order.class);
			query.setParameter("order_user_id", id);
			List OrderList = query.list();
			//sessiontx.commit();
			session.flush();
			return OrderList;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
		}
		return null;
	}

}
