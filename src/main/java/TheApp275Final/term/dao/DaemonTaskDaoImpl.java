package TheApp275Final.term.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import TheApp275Final.term.model.Order;


@Repository
@Transactional
public class DaemonTaskDaoImpl implements DaemonTaskDao{
	
	@Autowired
	private Session session;


	public DaemonTaskDaoImpl() {
		super();
	}

	@Override
	@Transactional
	public void updateStatusToP() {
		session.clear();
		Transaction tx = session.beginTransaction();
		System.out.println("Updating status to Preparing");
		String sql = "UPDATE ORDERS SET STATUS='P'"
				+ " WHERE STATUS='N' "
				+ "AND (DATE_SUB(NOW(), INTERVAL 7 HOUR) BETWEEN ORDER_START_TIME AND ORDER_END_TIME ) "
				+ "AND (DATE(PICKUP_TIME) = DATE(DATE_SUB(NOW(), INTERVAL 7 HOUR)));";
		//System.out.println(sql);
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(Order.class);		
		query.executeUpdate();
		tx.commit();
		session.flush();
		System.out.println("Updated status to Preparing!!!");
	}

	@Override
	@Transactional
	public void updateStatusToR() {
		session.clear();
		Transaction tx = session.beginTransaction();
		System.out.println("Updating status to  Ready for Pickup");
		String sql = "UPDATE ORDERS "
				+ "SET STATUS='R' "
				+ "WHERE STATUS IN ('N','P') "
				+ "AND (DATE_SUB(NOW(), INTERVAL 7 HOUR) BETWEEN ORDER_END_TIME AND PICKUP_TIME) "
				+ "AND (DATE(PICKUP_TIME) = DATE(DATE_SUB(NOW(), INTERVAL 7 HOUR)));";
		//System.out.println(sql);
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(Order.class);
		query.executeUpdate();
		tx.commit();
		System.out.println("Updated status to Ready for Pickup");
		session.flush();
	}

	@Override
	@Transactional
	public void updateStatusToF() {
		session.clear();
		Transaction tx = session.beginTransaction();
		System.out.println("Updating status to Fulfilled");
		String sql = "UPDATE ORDERS "
				+ "SET STATUS='R' "
				+ "WHERE STATUS IN ('N','P') "
				+ "AND (DATE_SUB(NOW(), INTERVAL 7 HOUR) BETWEEN ORDER_END_TIME AND PICKUP_TIME) "
				+ "AND (DATE(PICKUP_TIME) = DATE(DATE_SUB(NOW(), INTERVAL 7 HOUR)));";
		//System.out.println(sql);
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(Order.class);
		query.executeUpdate();
		tx.commit();
		System.out.println("Updated status to Fulfilled");
		session.flush();
	}

}
