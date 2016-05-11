package TheApp275Final.term.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import TheApp275Final.term.model.Order;
import TheApp275Final.term.model.Pipeline;

@Repository
@Transactional
public class OrderSchedulingDaoImpl implements OrderSchedulingDao {
	@Autowired
	private Session session;

	
	public OrderSchedulingDaoImpl() {
		super();
	}

	@Override
	@Transactional
	public boolean saveOrder(Order order, Pipeline pipe) {
		session.beginTransaction();
		session.save(pipe);
		order.setPipeline(pipe);
		session.save(order);
		session.getTransaction().commit();
		return true;
	}

	@Override
	public List<Order> getListOfOrders(Date pickupDate) {
		Transaction tx = null;
		try {
			session.clear();
			tx = session.beginTransaction();
			String sql = "SELECT * FROM ORDERS where date(pickup_time)= :pickup_date";
			SQLQuery query = session.createSQLQuery(sql);
			query.addEntity(Order.class);
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			query.setParameter("pickup_date", formatter.format(pickupDate));
			List Order = query.list();
			tx.commit();
			session.flush();
			return Order;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} 
		return null;
	}

}
