package TheApp275Final.term.dao;


import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import TheApp275Final.term.model.Order;
import TheApp275Final.term.model.Pipeline;

@Repository
public interface OrderSchedulingDao {

	public boolean saveOrder(Order order,Pipeline pipe);
	public List<Order> getListOfOrders(Date pickupDate);
}
