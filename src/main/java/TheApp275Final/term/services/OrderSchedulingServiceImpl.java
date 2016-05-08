package TheApp275Final.term.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import TheApp275Final.term.dao.OrderSchedulingDao;
import TheApp275Final.term.dto.OrderTimes;
import TheApp275Final.term.model.Order;
import TheApp275Final.term.model.Pipeline;
import TheApp275Final.term.model.Pipeline1;
import TheApp275Final.term.utility.TheAppUtility;

@Service
public class OrderSchedulingServiceImpl implements OrderSchedulingService {

	@Value("${business.start.hour}")
	private String businessStartTime;

	@Value("${business.end.hour}")
	private String businessEndTime;

	@Autowired
	private OrderSchedulingDao orderSchedulingDao;

	@Override
	@Transactional
	public void testInputdata() {
		Order order1 = new Order();
		Pipeline pipe = new Pipeline1();
		order1.setStatus('N');
		order1.setOrderEndTime(LocalDateTime.now());
		order1.setOrderStartTime(LocalDateTime.of(2016, 05, 07, 9, 9, 9, 00));
		order1.setPickUpTime(LocalDateTime.now());
		// order1.setPipeline(pipe);
		order1.setTotalProcTime(60);
		orderSchedulingDao.saveOrder(order1, pipe);

	}

	@Override
	public boolean checkPickUpTime(String pickUpTime) {
		LocalTime time = TheAppUtility.convertStringToLocalTime(pickUpTime);
		LocalTime startTime = TheAppUtility.convertStringToLocalTime(businessStartTime);
		LocalTime endTime = TheAppUtility.convertStringToLocalTime(businessEndTime);
		if (time.isAfter(startTime) && time.isBefore(endTime)) {
			System.out.println("perfect it is");
			return true;
		}
		System.out.println("imperfect it is");
		return false;
	}

	@Override
	public OrderTimes findEmptyPipeline(Date pickupDate, String pickupTime, int mins) {
		List<Order> orderList = orderSchedulingDao.getListOfOrders(pickupDate);
		Collections.sort(orderList, new Comparator<Order>() {
			public int compare(Order m1, Order m2) {
				return m1.getOrderStartTime().compareTo(m2.getOrderStartTime());
			}
		});
		for (Iterator iterator = orderList.iterator(); iterator.hasNext();) {
			Order order = (Order) iterator.next();
			System.out.print("Pipeline ID: " + order.getPipeline().getPipelineId());
			System.out.print("  Order Id: " + order.getOrderId());
			System.out.println("  Order Start Time: " + order.getOrderStartTime());
		}
		LocalTime upperLimitTime = TheAppUtility.convertStringToLocalTime(pickupTime);
		upperLimitTime = upperLimitTime.minusMinutes(mins);
		LocalTime lowerLimitTime = TheAppUtility.convertStringToLocalTime(pickupTime);
		lowerLimitTime = lowerLimitTime.minusMinutes(mins);
		lowerLimitTime = lowerLimitTime.minusMinutes(60);
		HashMap<Integer, ArrayList<OrderTimes>> orderTimesMap = TheAppUtility.convertListToMap(orderList);
		OrderTimes orderStartEndTime = null;
		for (int pipeno : orderTimesMap.keySet()) {
			ArrayList<OrderTimes> pipeList = orderTimesMap.get(pipeno);
			
			for (int i = 1; i < pipeList.size(); i++) {
				LocalTime order1EndTime=pipeList.get(i-1).getOrderEndTime();
				LocalTime order2StartTime=pipeList.get(i).getOrderStartTime();
				if(lowerLimitTime.isAfter(order1EndTime)){
					if(lowerLimitTime.plusMinutes(mins).isBefore(order2StartTime)){
						orderStartEndTime = new OrderTimes(lowerLimitTime, lowerLimitTime.plusMinutes(mins));
					}	
			    }
				else if(lowerLimitTime.isBefore(order2StartTime)){
					if(order1EndTime.plusMinutes(mins).isBefore(order2StartTime)){     
						orderStartEndTime = new OrderTimes(order1EndTime, lowerLimitTime.plusMinutes(mins));
					}
				}
				else if(upperLimitTime.isBefore(order2StartTime)){
					if(upperLimitTime.plusMinutes(mins).isBefore(order2StartTime)){
						orderStartEndTime = new OrderTimes(upperLimitTime, upperLimitTime.minusMinutes(mins));
					}
				}
			}
		}
		return orderStartEndTime;

	}

}

/*public FindIfEmpty(Pipeline p1,LocalTime lowerLimitTime, LocalTime upperLimitTime )

{
	return P1
			
	// return null
}
}


public  for todasuggestAnEarliestSloty(Pipeline p1,LocalTime now(), LocalTime 09pm )

public suggestAnEarliestslotforsomeday(Pipeline p1,LocalTime 6,am LocalTime 9pm )

*/