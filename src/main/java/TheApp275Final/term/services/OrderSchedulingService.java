package TheApp275Final.term.services;

import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import TheApp275Final.term.dto.OrderTimes;
import TheApp275Final.term.model.Order;

@Service
public interface OrderSchedulingService {

	public boolean saveOrder(Order order);
	public boolean checkPickUpTime(String pickUpTime);
	public boolean checkPickUpDate(Date pickUpDate);
	public HashMap<Integer, OrderTimes> getEarliestTimeSlots(Date pickupDate,int mins);
	public HashMap<Integer,OrderTimes> checkFeasibiltyOfPickUpTIme(Date pickupDate,String pickupTime,int mins);
}
