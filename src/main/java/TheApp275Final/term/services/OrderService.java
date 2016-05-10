package TheApp275Final.term.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import TheApp275Final.term.model.Order;
import TheApp275Final.term.model.OrderItems;

@Service
@Transactional(isolation=Isolation.REPEATABLE_READ,transactionManager="transactionManager")
public class OrderService {
	
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
	

}
