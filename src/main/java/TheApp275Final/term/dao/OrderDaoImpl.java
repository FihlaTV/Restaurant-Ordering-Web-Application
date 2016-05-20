package TheApp275Final.term.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import TheApp275Final.term.model.Order;
import TheApp275Final.term.model.OrderItems;


@Repository
@Transactional
public class OrderDaoImpl implements OrderDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	Session session;
	
	@Override
	public List<Order> getOrderReport(String startTime, String endTime, String sortBy){
		String query = "select * from ORDERS where ORDER_PLACEMENT_TIME between '"+startTime+"' and '"+endTime+"' and status != 'C' order by "+sortBy+"";
		@SuppressWarnings("unchecked")
		List<Order> orders = sessionFactory.getCurrentSession().createSQLQuery(query).addEntity(Order.class).list();
		return orders;
	}
	
	@Override
	public List<OrderItems> getPopularityReport(String startTime, String endTime) {
		
		String query = "select oi.* from ORDERS_ITEMS oi inner join ORDERS o where o.ORDER_ID=oi.ORDER_ID " 
						+ "and o.ORDER_PLACEMENT_TIME between '"+startTime+"' and '"+endTime+"' and o.status != 'C'"
					    + "order by oi.category, oi.item_name";
		@SuppressWarnings("unchecked")
		List<OrderItems> result = sessionFactory.getCurrentSession().createSQLQuery(query).addEntity(OrderItems.class).list();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void resetOrders() {
		String query = "select * from ORDERS";
		List<Order> orders = sessionFactory.getCurrentSession().createSQLQuery(query).addEntity(Order.class).list();
		for(Order order:orders){
			sessionFactory.getCurrentSession().delete(order);;
		}
	}
	
	@Override
	public void cancelOrder(int orderId) {
		session.clear();
		String query = "update ORDERS set status = 'C' where ORDER_ID = " + orderId;
		int temp = session.createSQLQuery(query).executeUpdate();
		session.flush();
	}

}
