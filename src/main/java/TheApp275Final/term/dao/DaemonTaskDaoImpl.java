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
		System.out.println("Updating status to p");
		String sql = "UPDATE ORDERS SET STATUS='P' WHERE STATUS='N'"
				+ " AND DATE_SUB(NOW(), INTERVAL 7 HOUR) BETWEEN ORDER_START_TIME AND ORDER_END_TIME";
		//System.out.println(sql);
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(Order.class);		
		query.executeUpdate();
		tx.commit();
		session.flush();
		System.out.println("Updated status to P");
	}

	@Override
	@Transactional
	public void updateStatusToR() {
		session.clear();
		Transaction tx = session.beginTransaction();
		System.out.println("Updating status to r");
		String sql = "UPDATE ORDERS SET STATUS='R' WHERE STATUS IN ('N','P')"
				+ " AND DATE_SUB(NOW(), INTERVAL 7 HOUR) BETWEEN ORDER_END_TIME AND PICKUP_TIME";
		//System.out.println(sql);
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(Order.class);
		query.executeUpdate();
		tx.commit();
		System.out.println("Updated status to R");
		session.flush();
	}

	@Override
	@Transactional
	public void updateStatusToF() {
		session.clear();
		Transaction tx = session.beginTransaction();
		System.out.println("Updating status to f");
		String sql = "UPDATE ORDERS SET STATUS='F' WHERE STATUS IN ('N','P','R') "
				+ "AND DATE_SUB(NOW(), INTERVAL 7 HOUR) > PICKUP_TIME";
		//System.out.println(sql);
		SQLQuery query = session.createSQLQuery(sql);
		query.addEntity(Order.class);
		query.executeUpdate();
		tx.commit();
		System.out.println("Updated status to F");
		session.flush();
	}

}
