package TheApp275Final.term.services;

import java.util.Date;

import org.springframework.stereotype.Service;

import TheApp275Final.term.dto.OrderTimes;

@Service
public interface OrderSchedulingService {

	public void testInputdata();
	public boolean checkPickUpTime(String pickUpTime);
	public OrderTimes findEmptyPipeline(Date pickupDate,String pickupTime, int mins);
}
