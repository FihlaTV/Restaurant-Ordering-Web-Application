package TheApp275Final.term.services;

import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import TheApp275Final.term.dto.OrderTimes;

@Service
public interface OrderSchedulingService {

	public void testInputdata();
	public boolean checkPickUpTime(String pickUpTime);
	public HashMap<Integer, OrderTimes> getEarliestTimeSlots(Date pickupDate,String pickupTime, int mins);
	public HashMap<Integer,OrderTimes> checkFeasibiltyOfPickUpTIme(Date pickupDate,String pickupTime,int mins);
}
