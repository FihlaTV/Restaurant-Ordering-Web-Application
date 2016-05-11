package TheApp275Final.term.services;

import java.util.List;

import org.springframework.stereotype.Service;

import TheApp275Final.term.model.Order;
import TheApp275Final.term.model.OrderItems;

@Service
public interface IOrderService {
	public float getProcessingTime(Order order);
	public List<Order> getOrderReport(String startTime, String endTime, String sortBy);
	public List<OrderItems> getPopularityReport(String startTime, String endTime);
	public void resetOrders();
	public void cancelOrder(int orderId);
}
