package TheApp275Final.term.services;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import TheApp275Final.term.dao.OrderSchedulingDao;
import TheApp275Final.term.dto.OrderTimes;
import TheApp275Final.term.model.Order;
import TheApp275Final.term.model.Pipeline;
import TheApp275Final.term.model.Pipeline1;
import TheApp275Final.term.model.Pipeline2;
import TheApp275Final.term.model.Pipeline3;
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
		for (int i = 0; i < 10;i=i+2) {
			Order order1 = new Order();
			Pipeline pipe = new Pipeline1();
			order1.setStatus('N');
			order1.setOrderEndTime(LocalDateTime.now().plusMinutes(i * 10 + 10));
			order1.setOrderStartTime(LocalDateTime.now().plusMinutes(i * 10));
			order1.setPickUpTime(LocalDateTime.now().plusMinutes(i * 10 + 10)); //
			order1.setPipeline(pipe);
			order1.setTotalProcTime(10);
			orderSchedulingDao.saveOrder(order1, pipe);
		}
		for (int i = 0; i < 10;i=i+2) {
			Order order1 = new Order();
			Pipeline pipe = new Pipeline2();
			order1.setStatus('N');
			order1.setOrderEndTime(LocalDateTime.now().plusMinutes(i * 30 + 30));
			order1.setOrderStartTime(LocalDateTime.now().plusMinutes(i * 30));
			order1.setPickUpTime(LocalDateTime.now().plusMinutes(i * 30 + 30)); //
			order1.setPipeline(pipe);
			order1.setTotalProcTime(30);
			orderSchedulingDao.saveOrder(order1, pipe);
		}

		for (int i = 0; i < 10;i=i+2) {
			Order order1 = new Order();
			Pipeline pipe = new Pipeline3();
			order1.setStatus('N');
			order1.setOrderEndTime(LocalDateTime.now().plusMinutes(i * 20 + 20));
			order1.setOrderStartTime(LocalDateTime.now().plusMinutes(i * 20));
			order1.setPickUpTime(LocalDateTime.now().plusMinutes(i * 20 + 20)); //
			order1.setPipeline(pipe);
			order1.setTotalProcTime(20);
			orderSchedulingDao.saveOrder(order1, pipe);
		}

		/*
		 * Order order1 = new Order(); Pipeline pipe = new Pipeline1();
		 * order1.setStatus('N');
		 * order1.setOrderEndTime(LocalDateTime.of(2016,5, 8, 05, 00));
		 * order1.setOrderStartTime(LocalDateTime.of(2016,5, 8, 04, 20));
		 * order1.setPickUpTime(LocalDateTime.of(2016,5, 8, 06, 00)); //
		 * order1.setPipeline(pipe); order1.setTotalProcTime(40);
		 * orderSchedulingDao.saveOrder(order1, pipe);
		 */
		/*Order order2 = new Order();
		Pipeline pipe1 = new Pipeline2();
		order2.setStatus('N');
		order2.setOrderEndTime(LocalDateTime.of(2016, 5, 8, 05, 00));
		order2.setOrderStartTime(LocalDateTime.of(2016, 5, 8, 04, 20));
		order2.setPickUpTime(LocalDateTime.of(2016, 5, 8, 06, 00));
		// order1.setPipeline(pipe);
		order2.setTotalProcTime(40);
		orderSchedulingDao.saveOrder(order2, pipe1);
		Order order3 = new Order();
		Pipeline pipe2 = new Pipeline3();
		order3.setStatus('N');
		order3.setOrderEndTime(LocalDateTime.of(2016, 5, 8, 05, 00));
		order3.setOrderStartTime(LocalDateTime.of(2016, 5, 8, 04, 20));
		order3.setPickUpTime(LocalDateTime.of(2016, 5, 8, 06, 00));
		// order1.setPipeline(pipe);
		order3.setTotalProcTime(40);
		orderSchedulingDao.saveOrder(order3, pipe2);*/
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

	public HashMap<Integer, OrderTimes> getFeasibleOrderTimes(HashMap<Integer, ArrayList<OrderTimes>> orderTimesMap,
			LocalTime upperLimitTime, LocalTime lowerLimitTime, int mins) {
		HashMap<Integer, OrderTimes> orderStartEndTimeMap = new HashMap<>();
		OrderTimes orderStartEndTime = null;
		for (int pipeno : orderTimesMap.keySet()) {
			ArrayList<OrderTimes> pipeList = orderTimesMap.get(pipeno);
			Collections.sort(pipeList, new Comparator<OrderTimes>() {
				public int compare(OrderTimes m1, OrderTimes m2) {
					return m1.getOrderStartTime().compareTo(m2.getOrderStartTime());
				}
			});
			System.out.println("New pipe no" + pipeno + pipeList.get(0).getOrderStartTime() + " "
					+ pipeList.get(0).getOrderEndTime());
			for (int i = 1; i < pipeList.size(); i++) {
				System.out.println("New pipe no" + pipeno + pipeList.get(i).getOrderStartTime() + " "
						+ pipeList.get(i).getOrderEndTime());
				LocalTime order1EndTime = pipeList.get(i - 1).getOrderEndTime();
				LocalTime order2StartTime = pipeList.get(i).getOrderStartTime();
				if (lowerLimitTime.isAfter(order1EndTime)) {
					if (lowerLimitTime.plusMinutes(mins).isBefore(order2StartTime)) {
						orderStartEndTime = new OrderTimes(lowerLimitTime, lowerLimitTime.plusMinutes(mins));
						orderStartEndTimeMap.put(pipeno, orderStartEndTime);
						System.out.println("in loop1");
						break;
					}
				} else if (lowerLimitTime.isBefore(order2StartTime)) {
					if (order1EndTime.plusMinutes(mins).isBefore(order2StartTime)) {
						orderStartEndTime = new OrderTimes(order1EndTime, order1EndTime.plusMinutes(mins));
						orderStartEndTimeMap.put(pipeno, orderStartEndTime);
						System.out.println("in loop2");
						break;
					}
				} else if (upperLimitTime.isBefore(order2StartTime)) {
					if (upperLimitTime.plusMinutes(mins).isBefore(order2StartTime)) {
						orderStartEndTime = new OrderTimes(upperLimitTime, upperLimitTime.minusMinutes(mins));
						orderStartEndTimeMap.put(pipeno, orderStartEndTime);
						System.out.println("in loop3");
						break;
					}
				}
			}
		}

		return orderStartEndTimeMap;
	}

	@Override
	public HashMap<Integer, OrderTimes> getEarliestTimeSlots(Date pickupDate, String pickupTime, int mins) {
		HashMap<Integer, OrderTimes> orderStartEndTimeMap = new HashMap<>();
		LocalTime startTime = TheAppUtility.convertStringToLocalTime(businessStartTime);
		LocalTime endTime = TheAppUtility.convertStringToLocalTime(businessEndTime);
		List<Order> orderList = orderSchedulingDao.getListOfOrders(pickupDate);
		Collections.sort(orderList, new Comparator<Order>() {
			public int compare(Order m1, Order m2) {
				return m1.getOrderStartTime().compareTo(m2.getOrderStartTime());
			}
		});
		HashMap<Integer, ArrayList<OrderTimes>> orderTimesMap = TheAppUtility.convertListToMap(orderList);
		OrderTimes orderStartEndTime = null;
		int emptyPipelineNumber = checkEmptyPipelines(orderTimesMap);
		System.out.println("emptyPipelineNumber " + emptyPipelineNumber);
		if (emptyPipelineNumber != 0) {
			if (checkPickUpTime(LocalTime.now().plusMinutes(mins).toString())) {
				orderStartEndTime = new OrderTimes(LocalTime.now(), LocalTime.now().plusMinutes(mins));
				orderStartEndTimeMap.put(emptyPipelineNumber, orderStartEndTime);
				System.out.println(orderStartEndTimeMap.get(emptyPipelineNumber).toString());
				return orderStartEndTimeMap;
			} else {
				if (LocalTime.now().plusMinutes(mins).isBefore(startTime)) {
					orderStartEndTime = new OrderTimes(startTime.minusMinutes(mins), startTime);
					orderStartEndTimeMap.put(emptyPipelineNumber, orderStartEndTime);
					System.out.println(orderStartEndTimeMap.get(emptyPipelineNumber).toString());
					return orderStartEndTimeMap;
				} else {
					System.out.println("returning nothing");
					return null;
				}
			}
		} else {
			LocalTime upperLimit = endTime;
			LocalTime lowerLimit = (LocalTime.now().isBefore(startTime)) ? startTime.minusMinutes(60).minusMinutes(mins)
					: LocalTime.now();
			addBoundaryConditions(orderTimesMap, lowerLimit, upperLimit);
			HashMap<Integer, OrderTimes> mapFinal = getFeasibleOrderTimes(orderTimesMap, upperLimit, lowerLimit, mins);
			for (int key : mapFinal.keySet()) {
				System.out.println(key + "=======" + mapFinal.get(key).toString());
			}

		}
		return null;
	}

	private void addBoundaryConditions(HashMap<Integer, ArrayList<OrderTimes>> orderTimesMap, LocalTime lowerLimit,
			LocalTime upperLimit) {
		for (Map.Entry<Integer, ArrayList<OrderTimes>> pipeline : orderTimesMap.entrySet()) {
			if (lowerLimit.isBefore(pipeline.getValue().get(0).getOrderStartTime())) {
				pipeline.getValue().add(new OrderTimes(lowerLimit, lowerLimit));
			}
			pipeline.getValue().add(new OrderTimes(upperLimit, upperLimit));
			System.out.println("pipeline size after adding boundarie conditions" + pipeline.getValue().size());
			System.out.println("lowerLimit " + lowerLimit);
			System.out.println("upperLimit " + upperLimit);
		}

	}

	private int checkEmptyPipelines(HashMap<Integer, ArrayList<OrderTimes>> orderTimesMap) {
		for (Map.Entry<Integer, ArrayList<OrderTimes>> pipeline : orderTimesMap.entrySet()) {
			if (pipeline.getValue().size() == 0) {
				return pipeline.getKey();
			}
		}
		return 0;
	}

	@Override
	public HashMap<Integer, OrderTimes> checkFeasibiltyOfPickUpTIme(Date pickupDate, String pickupTime, int mins) {
		System.out.println("Feasibilty of pickuptime called");
		HashMap<Integer, OrderTimes> orderStartEndTimeMap = new HashMap<>();
		OrderTimes orderStartEndTime = null;
		LocalTime startTime = TheAppUtility.convertStringToLocalTime(businessStartTime);
		LocalTime endTime = TheAppUtility.convertStringToLocalTime(businessEndTime);
		LocalTime pickupTimeObj = TheAppUtility.convertStringToLocalTime(pickupTime);
		List<Order> orderList = orderSchedulingDao.getListOfOrders(pickupDate);
		HashMap<Integer, ArrayList<OrderTimes>> orderTimesMap = TheAppUtility.convertListToMap(orderList);
		Collections.sort(orderList, new Comparator<Order>() {
			public int compare(Order m1, Order m2) {
				return m1.getOrderStartTime().compareTo(m2.getOrderStartTime());
			}
		});
		if (checkPickUpTime(pickupTime)) {
			if (LocalTime.now().plusMinutes(mins).isBefore(pickupTimeObj)
					|| LocalTime.now().plusMinutes(mins).equals(pickupTimeObj)) {
				int emptyPipelineNumber = checkEmptyPipelines(orderTimesMap);
				if (emptyPipelineNumber != 0) {
					orderStartEndTime = new OrderTimes(pickupTimeObj.minusMinutes(mins), pickupTimeObj);
					orderStartEndTimeMap.put(emptyPipelineNumber, orderStartEndTime);
					System.out.println("Pipe Number "+emptyPipelineNumber+" "+orderStartEndTimeMap.get(emptyPipelineNumber).toString());
					return orderStartEndTimeMap;
				} else {
					LocalTime upperLimitTime = pickupTimeObj.minusMinutes(mins);
					LocalTime lowerLimitTime = pickupTimeObj.minusMinutes(60).minusMinutes(mins);
					addBoundaryConditions(orderTimesMap, upperLimitTime);
					orderStartEndTimeMap = getFeasibleOrderTimes(orderTimesMap, upperLimitTime, lowerLimitTime, mins);
					for (int key : orderStartEndTimeMap.keySet()) {
						System.out.println(key + "=======" + orderStartEndTimeMap.get(key).toString());

						return orderStartEndTimeMap;
					}
				}
			} else {
				System.out.println("pickup time is too early to process the order");
				return null;
			}
		} else {
			System.out.println("pickup time is not in range of business hours");
			return null;
		}
		return null;
	}

	private void addBoundaryConditions(HashMap<Integer, ArrayList<OrderTimes>> orderTimesMap,
			LocalTime upperLimitTime) {
		for (Map.Entry<Integer, ArrayList<OrderTimes>> pipeline : orderTimesMap.entrySet()) {
			pipeline.getValue().add(new OrderTimes(upperLimitTime, upperLimitTime));
			System.out.println("pipeline size after adding boundarie conditions" + pipeline.getValue().size());
			System.out.println("upperLimitTime " + upperLimitTime);
		}

	}
}
