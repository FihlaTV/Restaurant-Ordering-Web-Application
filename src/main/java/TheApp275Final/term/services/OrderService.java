package TheApp275Final.term.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import TheApp275Final.term.dao.OrderDao;
import TheApp275Final.term.model.Order;
import TheApp275Final.term.model.OrderItems;

@Service("orderService")
@Transactional(isolation=Isolation.REPEATABLE_READ,transactionManager="transactionManager")
public class OrderService implements IOrderService{
	
	@Autowired
	private OrderDao orderDao;
	
	@Override
	@Transactional
	public float getProcessingTime(Order order) {
		float orderProcessingTime = 0;
		try{
			if(order != null){
				List<OrderItems> orderItems = order.getOrderItems();
				for(OrderItems orderItem : orderItems){
					orderProcessingTime += (orderItem.getPreparationTime()*orderItem.getQuantity());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return orderProcessingTime;
	}
	
	public List<Order> getOrderReport(String startTime, String endTime, String sortBy){
		return orderDao.getOrderReport(startTime, endTime, sortBy);
	}
	
	public List<OrderItems> getPopularityReport(String startTime, String endTime) {
		return orderDao.getPopularityReport(startTime, endTime);
	}

}
