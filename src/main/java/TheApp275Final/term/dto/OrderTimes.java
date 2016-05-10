package TheApp275Final.term.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class OrderTimes {
	
	@Override
	public String toString() {
		return "OrderTimes [orderStartTime=" + orderStartTime + ", OrderEndTime=" + OrderEndTime + "]";
	}

	private LocalTime orderStartTime;
	public LocalTime getOrderStartTime() {
		return orderStartTime;
	}

	public void setOrderStartTime(LocalTime orderStartTime) {
		this.orderStartTime = orderStartTime;
	}

	public LocalTime getOrderEndTime() {
		return OrderEndTime;
	}

	public void setOrderEndTime(LocalTime orderEndTime) {
		OrderEndTime = orderEndTime;
	}

	private LocalTime OrderEndTime;
	
	public OrderTimes(LocalTime orderStartTime, LocalTime OrderEndTime)
	{
		this.orderStartTime = orderStartTime;
		this.OrderEndTime = OrderEndTime;
	}
	
}
