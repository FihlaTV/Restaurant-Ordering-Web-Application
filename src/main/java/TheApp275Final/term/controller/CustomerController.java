package TheApp275Final.term.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import TheApp275Final.term.dto.OrderTimes;
import TheApp275Final.term.model.Customer;
import TheApp275Final.term.model.Item;
import TheApp275Final.term.model.Order;
import TheApp275Final.term.model.OrderItems;
import TheApp275Final.term.model.Pipeline;
import TheApp275Final.term.model.Pipeline1;
import TheApp275Final.term.model.Pipeline2;
import TheApp275Final.term.model.Pipeline3;
import TheApp275Final.term.services.CustomerService;
import TheApp275Final.term.services.ItemService;
import TheApp275Final.term.services.OrderSchedulingService;
import TheApp275Final.term.services.OrderService;
import TheApp275Final.term.utility.TheAppUtility;

@Controller
@RequestMapping(value = "/user/*")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@Autowired
	private HttpSession httpSession;

	@Autowired
	ItemService itemService;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderSchedulingService orderSchedulingService;

	@RequestMapping(value = "/")
	public ModelAndView defaultUserHomePage(HttpServletResponse response) throws IOException {
		Enumeration sessionVariables = httpSession.getAttributeNames();
		while (sessionVariables.hasMoreElements()) {
			String param = (String) sessionVariables.nextElement();
			System.out.println("Session Variables " + param + " and Value == " + httpSession.getAttribute(param));
		}
		System.out.println("Session in Home GET PRINCIPLE" + getPrincipal());
		Customer customer = customerService.getCustomer(getPrincipal());
		return new ModelAndView("userhome").addObject("customer", customer);
	}

	@RequestMapping(value = "/initializeOrder")
	public void newOrderPage(HttpServletResponse response) throws IOException {

		// Create An Order and Attach To Session
		if (httpSession.getAttribute("Order") == null) {
			Order order = new Order();
			List<OrderItems> orderItems = new ArrayList<>();
			order.setOrderItems(orderItems);
			httpSession.setAttribute("Order", order);
		}

		// Respond with 200 Message
		response.setStatus(200);
		response.setContentType("application/json");
		JSONObject temp = new JSONObject();
		response.getWriter().write(temp.toString());
	}

	@RequestMapping(value = "/cancelOrder")
	public void cancelOrder(HttpServletResponse response) throws IOException {

		// Create An Order and Attach To Session
		if (httpSession.getAttribute("Order") != null) {
			Order order = new Order();
			List<OrderItems> orderItems = new ArrayList<>();
			order.setOrderItems(orderItems);
			httpSession.setAttribute("Order", order);
		}

		// Respond with 200 Message
		response.setStatus(200);
		response.setContentType("application/json");
		JSONObject temp = new JSONObject();
		response.getWriter().write(temp.toString());
	}

	@RequestMapping(value = "/checkCustomerPickupDateTime")
	public void checkCustomerPickupDateTime(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		float orderProcessingTime = 0;
		String pickuptime = request.getParameter("pickuptime");
		String pickupdate = request.getParameter("pickupdate");

		System.out.println("pickupdate == " + pickupdate);
		System.out.println("pickuptime == " + pickuptime);
		
		Date date = null;
		try {
			date = formatter.parse(pickupdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		JSONObject respTemp = new JSONObject();

		// Get the order from the Session
		Order order = (Order) httpSession.getAttribute("Order");

		if (order == null) {
			// Respond with 404 Message
			response.setStatus(404);
			respTemp.put("Error", "Order Not Found");
		} else {
			response.setStatus(200);
			orderProcessingTime = orderService.getProcessingTime(order);
			if (orderSchedulingService.checkPickUpTime(pickuptime)) {
				HashMap<Integer, OrderTimes> slots = orderSchedulingService.checkFeasibiltyOfPickUpTIme(date, pickuptime, (int) orderProcessingTime);
				if (slots != null) {
					for (Entry<Integer, OrderTimes> entry : slots.entrySet()) {
						System.out.println(
								"Suggested pickup time by checkFeasibiltyOfPickUpTIme is - with pipeline number - "
										+ entry.getKey() + " Time is " + entry.getValue().toString());
						
						Pipeline pipeline = TheAppUtility.getPipeline(entry.getKey());
				        
						order.setPipeline(pipeline);
						LocalTime startTime = entry.getValue().getOrderStartTime();
						LocalTime endTime = entry.getValue().getOrderEndTime();
						order.setOrderStartTime(LocalDateTime.of(LocalDate.parse(pickupdate),startTime));
						order.setOrderEndTime(LocalDateTime.of(LocalDate.parse(pickupdate),endTime));
						order.setPickUpTime(LocalDateTime.of(LocalDate.parse(pickupdate),TheAppUtility.convertStringToLocalTime(pickuptime)));
						System.out.println(order.toString());
						break;
					}
					respTemp.put("pickupdatetime", true);
					respTemp.put("estimatedDateTime", pickuptime);
				} else {
					respTemp.put("pickupdatetime", false);
					slots = orderSchedulingService.getEarliestTimeSlots(date,(int) orderProcessingTime);
					LocalTime minLocalTime = LocalTime.of(23, 59);
					int minKey =0;
					for (Entry<Integer, OrderTimes> entry : slots.entrySet()) {
						System.out.println("Suggested pickup time is by getEarliestTimeSlots - with pipeline number - "
								+ entry.getKey() + " Time is " + entry.getValue().toString());
						if(entry.getValue().getOrderStartTime().isBefore(minLocalTime)){
							minKey=entry.getKey();
							minLocalTime=entry.getValue().getOrderStartTime();
						}
					}
					Pipeline pipeline = TheAppUtility.getPipeline(minKey);
			        
					order.setPipeline(pipeline);
					LocalTime startTime = slots.get(minKey).getOrderStartTime();
					LocalTime endTime = slots.get(minKey).getOrderEndTime();
					order.setOrderStartTime(LocalDateTime.of(LocalDate.parse(pickupdate),startTime));
					order.setOrderEndTime(LocalDateTime.of(LocalDate.parse(pickupdate),endTime));
					order.setPickUpTime(LocalDateTime.of(LocalDate.parse(pickupdate),TheAppUtility.convertStringToLocalTime(pickuptime)));
					System.out.println(order.toString());
				}

			} else {
				response.setStatus(404);
				respTemp.put("Error", "Order Not Found");
			}
		}

		//Set the order to the Session
		httpSession.setAttribute("Order", order);

		System.out.println("orderProcessingTime == " + orderProcessingTime);

		// Send the Response
		response.setContentType("application/json");
		response.getWriter().write(respTemp.toString());
	}

	@RequestMapping(value = "/estimatePickupDataTime")
	public void estimatePickupDataTime(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String pickupdate = request.getParameter("pickupdate");

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = null;
		try {
			date = formatter.parse(pickupdate);//(LocalDate.now().toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		float orderProcessingTime = 0;

		System.out.println("pickupdate == " + pickupdate);

		JSONObject respTemp = new JSONObject();

		// Get the order from the Session
		Order order = (Order) httpSession.getAttribute("Order");
		
		HashMap<Integer,OrderTimes> slots = null;

		if (order == null) {
			// Respond with 404 Message
			response.setStatus(404);
			respTemp.put("Error", "Order Not Found");
		} else {
			orderProcessingTime = orderService.getProcessingTime(order);
			
			System.out.println("orderProcessingTime == " + orderProcessingTime);
			
			slots = orderSchedulingService.getEarliestTimeSlots(date,(int)orderProcessingTime);
			
			LocalTime minLocalTime = LocalTime.of(23, 59);
			int minKey =0;
			for (Entry<Integer, OrderTimes> entry : slots.entrySet()) {
				System.out.println("Suggested pickup time is by getEarliestTimeSlots - with pipeline number - "
						+ entry.getKey() + " Time is " + entry.getValue().toString());
				if(entry.getValue().getOrderStartTime().isBefore(minLocalTime)){
					minKey=entry.getKey();
					minLocalTime=entry.getValue().getOrderStartTime();
				}
			}
			
			Pipeline pipeline = TheAppUtility.getPipeline(minKey);
	        
			order.setPipeline(pipeline);
			LocalTime startTime = slots.get(minKey).getOrderStartTime();
			LocalTime endTime = slots.get(minKey).getOrderEndTime();
			order.setOrderStartTime(LocalDateTime.of(LocalDate.now(),startTime));
			order.setOrderEndTime(LocalDateTime.of(LocalDate.now(),endTime));
			order.setPickUpTime(LocalDateTime.of(LocalDate.now(),endTime));
			
			System.out.println(order.toString());
			
			//Set Response
			String estimatedPickUpDateTime =  endTime.toString();//.substring(0, endTime.toString().indexOf("."));
			
			System.out.println(estimatedPickUpDateTime);
			
			response.setStatus(200);
			respTemp.put("pickupdatetime", false);
			respTemp.put("estimatedDateTime",estimatedPickUpDateTime);
		}

		//Set the order to the Session
		httpSession.setAttribute("Order", order);
		
		// Send the Response
		response.setContentType("application/json");
		response.getWriter().write(respTemp.toString());
	}

	@RequestMapping(value = "/getMenuItems", method = RequestMethod.POST)
	public void getMenuItems(HttpServletRequest request, HttpServletResponse response) throws IOException {

		JSONArray jsonArray = new JSONArray();
		List<Item> itemList = itemService.getActiveItems();

		for (Item item : itemList) {
			JSONObject temp = new JSONObject();
			temp.put("PreparationTime", item.getPreparationTime());
			temp.put("Calories", item.getCalories());
			temp.put("Category", item.getCategory());
			temp.put("ItemName", item.getItemName());
			temp.put("UnitPrice", item.getUnitPrice());
			temp.put("Picture", item.getPicture());
			temp.put("Quantity", 1);
			jsonArray.put(temp);
		}
		response.setContentType("application/json");
		response.getWriter().write(jsonArray.toString());
	}
	
	@RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
	public void submitOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		// Get the order from the Session
		Order order = (Order) httpSession.getAttribute("Order");
		
		// Setting the user id in order object
	//	order.setOrderUserId(((Customer)(SecurityContextHolder.getContext().getAuthentication())).getId());
		
		//This will return true after the saving the order
		orderSchedulingService.saveOrder(order);
		
		//Have to work on the response 
		response.setContentType("application/json");
		response.getWriter().write("");
	}

	@RequestMapping(value = "/getShoppingCart", method = RequestMethod.POST)
	public void getShoppingCart(HttpServletRequest request, HttpServletResponse response) throws IOException {

		JSONArray jsonArray = new JSONArray();
		Order order = (Order) httpSession.getAttribute("Order");
		if (order != null) {
			List<OrderItems> orderItems = order.getOrderItems();
			if (orderItems != null && orderItems.size() > 0) {
				for (OrderItems orderItem : orderItems) {
					JSONObject jsonOrderItem = new JSONObject();
					jsonOrderItem.put("PreparationTime", orderItem.getPreparationTime());
					jsonOrderItem.put("Calories", orderItem.getCalories());
					jsonOrderItem.put("Category", orderItem.getCategory());
					jsonOrderItem.put("ItemName", orderItem.getItemName());
					jsonOrderItem.put("UnitPrice", orderItem.getUnitPrice());
					jsonOrderItem.put("Picture", orderItem.getPicture());
					jsonOrderItem.put("Quantity", orderItem.getQuantity());
					jsonArray.put(jsonOrderItem);
				}
			}
		}

		response.setContentType("application/json");
		response.getWriter().write(jsonArray.toString());
	}

	@RequestMapping(value = "/deleteLineItem", method = RequestMethod.POST)
	public void deleteLineItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONArray jsonArray = new JSONArray();
		Order order = (Order) httpSession.getAttribute("Order");
		if (order != null) {
			List<OrderItems> orderItems = order.getOrderItems();
			if (orderItems != null && orderItems.size() > 0) {
				Iterator<OrderItems> orditm = orderItems.iterator();
				while (orditm.hasNext()) {
					OrderItems s = orditm.next();
					if (request.getParameter("item[ItemName]").equals(s.getItemName())) {
						orditm.remove();
						break;
					}
				}
				for (OrderItems orderItem : orderItems) {
					JSONObject jsonOrderItem = new JSONObject();
					jsonOrderItem.put("PreparationTime", orderItem.getPreparationTime());
					jsonOrderItem.put("Calories", orderItem.getCalories());
					jsonOrderItem.put("Category", orderItem.getCategory());
					jsonOrderItem.put("ItemName", orderItem.getItemName());
					jsonOrderItem.put("UnitPrice", orderItem.getUnitPrice());
					jsonOrderItem.put("Picture", orderItem.getPicture());
					jsonOrderItem.put("Quantity", orderItem.getQuantity());
					jsonArray.put(jsonOrderItem);
				}
			}
			order.setOrderItems(orderItems);
			httpSession.setAttribute("Order", order);
		}
		response.setContentType("application/json");
		response.getWriter().write(jsonArray.toString());
	}

	@RequestMapping(value = "/updateItemQuantity", method = RequestMethod.POST)
	public void updateItemQuantity(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONArray jsonArray = new JSONArray();
		Order order = (Order) httpSession.getAttribute("Order");
		if (order != null) {
			List<OrderItems> orderItems = order.getOrderItems();
			if (orderItems != null && orderItems.size() > 0) {
				for (OrderItems orderItem : orderItems) {
					if (request.getParameter("item[ItemName]").equals(orderItem.getItemName())) {
						int qty = orderItem.getQuantity();
						qty++;
						orderItem.setQuantity(qty);
					}
					JSONObject jsonOrderItem = new JSONObject();
					jsonOrderItem.put("PreparationTime", orderItem.getPreparationTime());
					jsonOrderItem.put("Calories", orderItem.getCalories());
					jsonOrderItem.put("Category", orderItem.getCategory());
					jsonOrderItem.put("ItemName", orderItem.getItemName());
					jsonOrderItem.put("UnitPrice", orderItem.getUnitPrice());
					jsonOrderItem.put("Picture", orderItem.getPicture());
					jsonOrderItem.put("Quantity", orderItem.getQuantity());
					jsonArray.put(jsonOrderItem);
				}
			}
			httpSession.setAttribute("Order", order);
		}
		response.setContentType("application/json");
		response.getWriter().write(jsonArray.toString());
	}

	@RequestMapping(value = "/addLineItem", method = RequestMethod.POST)
	public void addLineItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONArray jsonArray = new JSONArray();
		Order order = (Order) httpSession.getAttribute("Order");
		if (order != null) {

			List<OrderItems> orderItems = order.getOrderItems();

			System.out.println("item == > " + request.getParameter("item"));

			JSONObject jsonOrderItm = new JSONObject();

			Enumeration sessionVariables = request.getParameterNames();
			while (sessionVariables.hasMoreElements()) {
				String param = (String) sessionVariables.nextElement();
				System.out.println("Session Variables " + param + " and Value == " + request.getParameter(param));
				switch (param) {

				case "item[UnitPrice]":
					jsonOrderItm.put("UnitPrice", request.getParameter(param));
					break;
				case "item[Category]":
					jsonOrderItm.put("Category", request.getParameter(param));
					break;
				case "item[Picture][]":
					/*
					 * jsonOrderItm.put("Picture", request.getParameter(param));
					 */
					break;
				case "item[ItemName]":
					jsonOrderItm.put("ItemName", request.getParameter(param));
					break;
				case "item[PreparationTime]":
					jsonOrderItm.put("PreparationTime", request.getParameter(param));
					break;
				case "item[Calories]":
					jsonOrderItm.put("Calories", request.getParameter(param));
					break;

				case "item[Quantity]":
					System.out.println(request.getParameter(param) + " Quantity....");
					int quantity = Integer.valueOf(request.getParameter(param));
					if (quantity == 0)
						quantity = 1;
					jsonOrderItm.put("Quantity", quantity);
					break;

				default:
					break;
				}
			}

			System.out.println(jsonOrderItm.toString());

			// Check if Item is Existing
			for (OrderItems orderItem : orderItems) {
				if (request.getParameter("item[ItemName]").equals(orderItem.getItemName())) {
					int qty = orderItem.getQuantity();
					qty++;
					orderItem.setQuantity(qty);
					jsonOrderItm = null;
					order.setOrderItems(orderItems);
					httpSession.setAttribute("Order", order);
					break;
				}
			}

			if (jsonOrderItm != null && jsonOrderItm.length() > 0) {

				int calories = Integer.valueOf((String) jsonOrderItm.get("Calories"));
				String category = (String) jsonOrderItm.get("Category");
				String ItemName = (String) jsonOrderItm.get("ItemName");
				float unitPrice = Float.parseFloat((String) jsonOrderItm.get("UnitPrice"));
				/*
				 * byte[] picture = null; if(jsonOrderItm.get("Picture") !=
				 * null){ picture = (byte[]) jsonOrderItm.get("Picture"); }
				 */
				int preparationTime = Integer.valueOf((String) jsonOrderItm.get("PreparationTime"));
				int quantity = (int) jsonOrderItm.get("Quantity");

				OrderItems orderitm = new OrderItems();
				orderitm.setCalories(calories);
				orderitm.setCategory(category);
				orderitm.setItemName(ItemName);
				// orderitm.setPicture(picture);
				orderitm.setPreparationTime(preparationTime);
				orderitm.setUnitPrice(unitPrice);
				orderitm.setQuantity(quantity);

				orderItems.add(orderitm);

				order.setOrderItems(orderItems);

				httpSession.setAttribute("Order", order);
			}

			if (orderItems != null && orderItems.size() > 0) {
				for (OrderItems orderItem : orderItems) {
					JSONObject jsonOrderItem = new JSONObject();
					jsonOrderItem.put("Calories", orderItem.getCalories());
					jsonOrderItem.put("Category", orderItem.getCategory());
					jsonOrderItem.put("ItemName", orderItem.getItemName());
					jsonOrderItem.put("UnitPrice", orderItem.getUnitPrice());
					jsonOrderItem.put("Picture", orderItem.getPicture());
					jsonOrderItem.put("Quantity", orderItem.getQuantity());
					jsonArray.put(jsonOrderItem);
				}
			}
		}
		response.setContentType("application/json");
		response.getWriter().write(jsonArray.toString());
	}

	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("getPrincipal" + principal.toString());

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

}
