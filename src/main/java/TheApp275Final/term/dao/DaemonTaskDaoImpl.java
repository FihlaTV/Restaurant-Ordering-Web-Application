package TheApp275Final.term.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import TheApp275Final.term.model.Customer;
import TheApp275Final.term.model.Order;
import TheApp275Final.term.services.EmailService;


@Repository
@Transactional
public class DaemonTaskDaoImpl implements DaemonTaskDao{
	
	@Autowired
	private Session session;
	
	@Autowired
	EmailService emailService;

	public DaemonTaskDaoImpl() {
		super();
	}

	@Override
	@Transactional
	public void updateStatusToP() {
		session.clear();
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		//Send the Confirmation Email to the Customer.
		try{
			//Get List of Orders and Send Confirmation Mails to all of them
			String selectQuery = "Select * FROM ORDERS"
					+ " WHERE STATUS='N' "
				+ "AND (DATE_SUB(NOW(), INTERVAL 7 HOUR) BETWEEN ORDER_START_TIME AND ORDER_END_TIME ) "
				+ "AND (DATE(PICKUP_TIME) = DATE(DATE_SUB(NOW(), INTERVAL 7 HOUR))) AND STATUS NOT IN ('C');";
			SQLQuery selectquery = session.createSQLQuery(selectQuery);
			selectquery.addEntity(Order.class);
			List<Order> orders = selectquery.list();
			System.out.println("orders.length == " + orders.size());
			for(Order order : orders){
				Customer customer = order.getCustomer();
				emailService.sendOrderPreperationStartedMail(customer, order);
				System.out.println("Update Mail sent to Order - " + order.getOrderId()  + " and Customer - " + customer.getUsername());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		session.clear();
		Transaction tx = session.beginTransaction();
		System.out.println("Updating status to Preparing");
		/*String sql = "UPDATE ORDERS SET STATUS='P'"
				+ " WHERE STATUS='N' "
				+ "AND (DATE_SUB(NOW(), INTERVAL 7 HOUR) BETWEEN ORDER_START_TIME AND ORDER_END_TIME ) "
				+ "AND (DATE(PICKUP_TIME) = DATE(DATE_SUB(NOW(), INTERVAL 7 HOUR))) AND STATUS NOT IN ('C');";*/
		String sql = "UPDATE ORDERS SET STATUS='P'"
				+ " WHERE STATUS='N' "
				+ "AND (DATE_SUB(NOW(), INTERVAL 7 HOUR) BETWEEN ORDER_START_TIME AND ORDER_END_TIME ) "
				+ "AND (DATE(PICKUP_TIME) = DATE(DATE_SUB(NOW(), INTERVAL 7 HOUR))) AND STATUS NOT IN ('C');";
		//System.out.println(sql);
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(Order.class);		
		query.executeUpdate();
		tx.commit();
		session.flush();
		System.out.println("Updated status to Preparing!!!");
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

	@Override
	@Transactional
	public void updateStatusToR() {
		session.clear();
	System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		//Send the Confirmation Email to the Customer.
		try{
			//Get List of Orders and Send Confirmation Mails to all of them
			String selectQuery =  "Select * FROM ORDERS"
				+ " WHERE STATUS IN ('N','P') "
				+ "AND (DATE_SUB(NOW(), INTERVAL 7 HOUR) BETWEEN ORDER_END_TIME AND PICKUP_TIME) "
				+ "AND (DATE(PICKUP_TIME) = DATE(DATE_SUB(NOW(), INTERVAL 7 HOUR))) AND STATUS NOT IN ('C');";
			SQLQuery selectquery = session.createSQLQuery(selectQuery);
			selectquery.addEntity(Order.class);
			List<Order> orders = selectquery.list();
			for(Order order : orders){
				Customer customer = order.getCustomer();
				emailService.sendOrderReadyForPickupMail(customer, order);
				System.out.println("Ready to Pickup Mail sent to Order - " + order.getOrderId()  + " and Customer - " + customer.getUsername());
			}
		}catch(Exception e){
			e.printStackTrace();
		}
			
		session.clear();
		Transaction tx = session.beginTransaction();
		System.out.println("Updating status to  Ready for Pickup");
		String sql = "UPDATE ORDERS "
				+ "SET STATUS='R' "
				+ "WHERE STATUS IN ('N','P') "
				+ "AND (DATE_SUB(NOW(), INTERVAL 7 HOUR) BETWEEN ORDER_END_TIME AND PICKUP_TIME) "
				+ "AND (DATE(PICKUP_TIME) = DATE(DATE_SUB(NOW(), INTERVAL 7 HOUR))) AND STATUS NOT IN ('C');";
		//System.out.println(sql);
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(Order.class);
		query.executeUpdate();
		tx.commit();
		System.out.println("Updated status to Ready for Pickup");
		session.flush();
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

	@Override
	@Transactional
	public void updateStatusToF() {
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		session.clear();
		Transaction tx = session.beginTransaction();
		System.out.println("Updating status to Fulfilled");
		String sql = "UPDATE ORDERS "
				+ "SET STATUS='R' "
				+ "WHERE STATUS IN ('N','P') "
				+ "AND (DATE_SUB(NOW(), INTERVAL 7 HOUR) BETWEEN ORDER_END_TIME AND PICKUP_TIME) "
				+ "AND (DATE(PICKUP_TIME) = DATE(DATE_SUB(NOW(), INTERVAL 7 HOUR))) AND STATUS NOT IN ('C');";
		//System.out.println(sql);
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(Order.class);
		query.executeUpdate();
		tx.commit();
		System.out.println("Updated status to Fulfilled");
		session.flush();
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	}

}
