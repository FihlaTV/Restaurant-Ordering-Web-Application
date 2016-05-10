package TheApp275Final.term.dao;

import java.time.LocalDateTime;
import java.util.List;


import TheApp275Final.term.model.Order;
import TheApp275Final.term.model.OrderItems;

public interface OrderDao {
	
	public List<Order> getOrderReport(String startTime, String endTime, String sortBy);
	public List<OrderItems> getPopularityReport(String startTime, String endTime);

}
