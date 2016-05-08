package TheApp275Final.term.utility;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import TheApp275Final.term.dto.OrderTimes;
import TheApp275Final.term.model.Order;

public class TheAppUtility {

	public static LocalTime convertStringToLocalTime(String time){
		LocalTime localTime = LocalTime.parse(time);
		return localTime;
	}
	
	public static HashMap<Integer,ArrayList<OrderTimes>> convertListToMap(List<Order> orderList){
		HashMap<Integer,ArrayList<OrderTimes>> orderMap = new HashMap<Integer,ArrayList<OrderTimes>>();
		// iterate through your objects
		for(Order order : orderList){

		    // fetch the list for this object's id
		    //List<Order> temp = orderMap.get(order.getPipeline().getPipelineNo());
			OrderTimes orderTimes = new OrderTimes(order.getOrderStartTime().now().toLocalTime(),order.getOrderEndTime().now().toLocalTime());
			ArrayList<OrderTimes> tmp;
			if(orderMap.containsKey(order.getPipeline().getPipelineNo()))
			{
				tmp = orderMap.get(order.getPipeline().getPipelineNo());
				
			}
			else
			{
				tmp = new ArrayList<OrderTimes>();
			}
			tmp.add(orderTimes);
			orderMap.put(order.getPipeline().getPipelineNo(),tmp);
			
			
		}
		
		return orderMap;
	}
}
