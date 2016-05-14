package TheApp275Final.term.controller;

import java.io.IOException;
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
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import TheApp275Final.term.dto.OrderTimes;
import TheApp275Final.term.model.Customer;
import TheApp275Final.term.model.Item;
import TheApp275Final.term.model.ItemRating;
import TheApp275Final.term.model.Order;
import TheApp275Final.term.model.OrderItems;
import TheApp275Final.term.model.Pipeline;
import TheApp275Final.term.services.CustomerService;
import TheApp275Final.term.services.EmailService;
import TheApp275Final.term.services.IOrderService;
import TheApp275Final.term.services.ItemService;
import TheApp275Final.term.services.OrderSchedulingService;
import TheApp275Final.term.utility.TheAppUtility;

@Controller
@RequestMapping(value = "/user/*")
public class CustomerController {
	
	@Value("${business.start.hour}")
	private String businessStartTime;

	@Autowired
	CustomerService customerService;

	@Autowired
	private HttpSession httpSession;

	@Autowired
	ItemService itemService;

	@Autowired
	IOrderService orderService;
	
	@Autowired
	EmailService emailService;

	@Autowired
	OrderSchedulingService orderSchedulingService;

	@RequestMapping(value={"*", "/"})
	public ModelAndView defaultUserHomePage(HttpServletResponse response) throws IOException {
		/*Enumeration sessionVariables = httpSession.getAttributeNames();
		while (sessionVariables.hasMoreElements()) {
			String param = (String) sessionVariables.nextElement();
			System.out.println("Session Variables " + param + " and Value == " + httpSession.getAttribute(param));
		}*/
		System.out.println("Session in Home GET PRINCIPLE" + getPrincipal());
		Customer customer = customerService.getCustomer(getPrincipal());
		return new ModelAndView("userhome").addObject("customer", customer);
	}
	
	@RequestMapping(value={"/newOrder"})
	public ModelAndView newOrder(HttpServletResponse response) throws IOException {
		/*Enumeration sessionVariables = httpSession.getAttributeNames();
		while (sessionVariables.hasMoreElements()) {
			String param = (String) sessionVariables.nextElement();
			System.out.println("Session Variables " + param + " and Value == " + httpSession.getAttribute(param));
		}*/
		System.out.println("Session in Home GET PRINCIPLE" + getPrincipal());
		Customer customer = customerService.getCustomer(getPrincipal());
		return new ModelAndView("userNewOrder").addObject("customer", customer);
	}

	@RequestMapping(value = "/initializeOrder")
	public void newOrderPage(HttpServletResponse response) throws IOException {

		// Create An Order and Attach To Session
		if (httpSession.getAttribute("Order") == null) {
			Order order = new Order();
			order.setStatus('N');
			order.setOrderPlacementTime(LocalDateTime.now());
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
	
	@RequestMapping(value = "/cancelSubmittedOrder")
	public void cancelSubmittedOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		System.out.println("cancelSubmittedOrder ==> " + request.getParameter("orderid"));
		
		try{
			int orderId = Integer.valueOf(request.getParameter("orderid"));
			if(orderId > 0){
				response.setStatus(200);
				orderService.cancelOrder(orderId);
			}else{
				response.setStatus(404);
			}
		}catch(Exception e){
			e.printStackTrace();
			response.setStatus(404);	
		}
		// Respond with 200 Message
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
		HashMap<Integer, OrderTimes> slots = null;

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
				slots = orderSchedulingService.checkFeasibiltyOfPickUpTIme(date, pickuptime, (int) orderProcessingTime);
				if (slots != null) {
					System.out.println("User Slot Feasible!!!");
					for (Entry<Integer, OrderTimes> entry : slots.entrySet()) {
						System.out.println(	"Suggested pickup time by checkFeasibiltyOfPickUpTIme is - with pipeline number - "	+ entry.getKey() + " Time is " + entry.getValue().toString());
						Pipeline pipeline = TheAppUtility.getPipeline(entry.getKey());
						if (entry.getValue().getOrderEndTime().isBefore(TheAppUtility.convertStringToLocalTime(pickuptime))
								|| entry.getValue().getOrderEndTime().equals(TheAppUtility.convertStringToLocalTime(pickuptime))) {
							order.setPipeline(pipeline);
							LocalTime startTime = entry.getValue().getOrderStartTime();
							LocalTime endTime = entry.getValue().getOrderEndTime();
							order.setOrderStartTime(LocalDateTime.of(LocalDate.parse(pickupdate),startTime));
							order.setOrderEndTime(LocalDateTime.of(LocalDate.parse(pickupdate),endTime));
							order.setPickUpTime(LocalDateTime.of(LocalDate.parse(pickupdate),LocalTime.parse(pickuptime)));
							System.out.println(order.toString());
							break;
						}
					}
					respTemp.put("pickupdatetime", true);
					respTemp.put("estimatedDateTime", pickuptime);
				} else {
					System.out.println("User Slot NOT Feasible!!! Finding Alternatives");
					slots = orderSchedulingService.getEarliestTimeSlots(date,(int) orderProcessingTime);
					if(slots != null){
						LocalTime minLocalTime = LocalTime.of(23, 59);
						int minKey = 0;
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
						
						//Set Response
						String estimatedPickUpDateTime =  pickuptime.toString();
						System.out.println(estimatedPickUpDateTime);
						
						respTemp.put("pickupdatetime", true);
						respTemp.put("estimatedDateTime", estimatedPickUpDateTime);
					}else{
						respTemp.put("pickupdatetime", false);
					}
				}
			} else {
				System.out.println("User Slot NOT Feasible!!! Finding Alternatives");
				slots = orderSchedulingService.getEarliestTimeSlots(date,(int) orderProcessingTime);
				LocalTime busPickUpStartTime = TheAppUtility.convertStringToLocalTime(businessStartTime);
				LocalTime PickUpTime= TheAppUtility.convertStringToLocalTime(businessStartTime);
				if(slots != null){
					LocalTime minLocalTime = LocalTime.of(23, 59);
					int minKey = 0;
					for (Entry<Integer, OrderTimes> entry : slots.entrySet()) {
						System.out.println("Suggested pickup time is by getEarliestTimeSlots - with pipeline number - "
								+ entry.getKey() + " Time is " + entry.getValue().toString());
						if(entry.getValue().getOrderStartTime().isBefore(minLocalTime)){
							minKey=entry.getKey();
							minLocalTime=entry.getValue().getOrderStartTime();
						}
					}
					if(!slots.get(minKey).getOrderEndTime().isBefore(busPickUpStartTime)){
						PickUpTime=slots.get(minKey).getOrderEndTime();
					}
					Pipeline pipeline = TheAppUtility.getPipeline(minKey);
			        
					order.setPipeline(pipeline);
					LocalTime startTime = slots.get(minKey).getOrderStartTime();
					LocalTime endTime = slots.get(minKey).getOrderEndTime();
					order.setOrderStartTime(LocalDateTime.of(LocalDate.parse(pickupdate),startTime));
					order.setOrderEndTime(LocalDateTime.of(LocalDate.parse(pickupdate),endTime));
					order.setPickUpTime(LocalDateTime.of(LocalDate.parse(pickupdate),PickUpTime));
					System.out.println(order.toString());
					
					//Set Response
					String estimatedPickUpDateTime =  PickUpTime.toString();
					System.out.println(estimatedPickUpDateTime);
					
					respTemp.put("pickupdatetime", true);
					respTemp.put("estimatedDateTime", estimatedPickUpDateTime);
				}else{
					respTemp.put("pickupdatetime", false);
				}
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
			int minKey = -1;
			LocalTime busPickUpStartTime = TheAppUtility.convertStringToLocalTime(businessStartTime);
			LocalTime PickUpTime= TheAppUtility.convertStringToLocalTime(businessStartTime);
			if(slots != null){
				for (Entry<Integer, OrderTimes> entry : slots.entrySet()) {
					System.out.println("Suggested pickup time is by getEarliestTimeSlots - with pipeline number - "
							+ entry.getKey() + " Time is " + entry.getValue().toString());
					if(entry.getValue().getOrderStartTime().isBefore(minLocalTime)){
						minKey=entry.getKey();
						minLocalTime=entry.getValue().getOrderStartTime();
					}
				}
				if(!slots.get(minKey).getOrderEndTime().isBefore(busPickUpStartTime)){
					PickUpTime=slots.get(minKey).getOrderEndTime();
				}
			}
			
			if(minKey != -1){
				Pipeline pipeline = TheAppUtility.getPipeline(minKey);
		        
				order.setPipeline(pipeline);
				LocalTime startTime = slots.get(minKey).getOrderStartTime();
				LocalTime endTime = slots.get(minKey).getOrderEndTime();
				order.setOrderStartTime(LocalDateTime.of(LocalDate.parse(pickupdate),startTime));
				order.setOrderEndTime(LocalDateTime.of(LocalDate.parse(pickupdate),endTime));
				order.setPickUpTime(LocalDateTime.of(LocalDate.parse(pickupdate),PickUpTime));
				
				System.out.println(order.toString());
				
				//Set Response
				String estimatedPickUpDateTime =  PickUpTime.toString();//.substring(0, endTime.toString().indexOf("."));
				
				System.out.println(estimatedPickUpDateTime);
				
				//Set the order to the Session
				httpSession.setAttribute("Order", order);
				
				response.setStatus(200);
				respTemp.put("pickupdatetime", true);
				respTemp.put("estimatedDateTime",estimatedPickUpDateTime);	
			}else{
				response.setStatus(200);
				respTemp.put("pickupdatetime", false);
			}
		}

		//Set the order to the Session
		httpSession.setAttribute("Order", order);
		
		// Send the Response
		response.setContentType("application/json");
		response.getWriter().write(respTemp.toString());
	}

	@RequestMapping(value = "getMenuItems", method = RequestMethod.POST)
	public void getMenuItems(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		System.out.println("Customer from Session ==> " + httpSession.getAttribute("customer").toString());

		JSONArray jsonArray = new JSONArray();
		List<Item> itemList = itemService.getActiveItems();

		for (Item item : itemList) {
			JSONObject temp = new JSONObject();
			temp.put("id", item.getId());
			temp.put("PreparationTime", item.getPreparationTime());
			temp.put("Calories", item.getCalories());
			temp.put("Category", item.getCategory());
			temp.put("ItemName", item.getItemName());
			temp.put("UnitPrice", item.getUnitPrice());
			temp.put("Quantity", 1);
			
			//Determine the Item Average Rating
			List<ItemRating> ratings = item.getRatings();
			if(ratings != null){
				Set<Integer> userRating = new TreeSet<>();
				float avgRating = 0;
				float totalRating = 0;
				float numOfRating = ratings.size();
				
				for(ItemRating rating : ratings){
					if(!userRating.contains(rating.getCustomerId())){
						userRating.add(rating.getCustomerId());
					}
					totalRating += rating.getRating();
				}
				if(numOfRating != 0)
					avgRating = totalRating/numOfRating;
				
				System.out.println("userRating ==> " + userRating.size());
				System.out.println(totalRating + "/" + numOfRating + " == " + avgRating);
				
				if(userRating.size() >= 2){
					temp.put("rating", avgRating);
				}else{
					temp.put("rating", 0);
				}
				userRating.clear();
			}else{
				temp.put("rating", 0);
			}
			
			//Add the Item to the JSON Response Array
			jsonArray.put(temp);
		}
		response.setContentType("application/json");
		response.getWriter().write(jsonArray.toString());
	}
	
	@RequestMapping(value = "getOrderHistory", method = RequestMethod.POST)
	public void getOrderHistory(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Customer customer = (Customer) httpSession.getAttribute("customer");
		
		JSONArray jsonArray = new JSONArray();
		
		System.out.println("Customer from Session ==> " + httpSession.getAttribute("customer").toString());
		
		if(customer != null){
			
			List<Order> OrderHistory = customerService.getListOfOrder(customer.getId());

			for (Order order : OrderHistory) {
				
				System.out.println(order.getOrderId() + " ; "+ order.getStatus());
				float totalPrice = 0;
				JSONObject temp = new JSONObject();
				List<OrderItems> orderItems = order.getOrderItems();
				
				for(OrderItems orderItem : orderItems){
					totalPrice += (orderItem.getUnitPrice()*orderItem.getQuantity());
				}

				temp.put("TotalPrice", totalPrice);
				temp.put("Id", order.getOrderId());
				temp.put("OrderPlacementTime", order.getOrderPlacementTime());
				temp.put("PickUpTime", order.getPickUpTime());
				temp.put("Status", order.getStatus()+"");
				jsonArray.put(temp);
			}	
		}
		response.setContentType("application/json");
		response.getWriter().write(jsonArray.toString());
	}

	@RequestMapping(value = "/submitOrder", method = RequestMethod.POST)
	public ModelAndView submitOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Customer customer = null;
		try {
			customer = (Customer) httpSession.getAttribute("customer");

			// Get the order from the Session
			Order order = (Order) httpSession.getAttribute("Order");

			//Set the order Status
			order.setStatus('N');

			// Setting the user id in order object
			order.setCustomer(customer);

			// This will return true after the saving the order
			orderSchedulingService.saveOrder(order);
			
			//Send the Confirmation Email to the Customer
			emailService.sendOrderConfirmationMail(customer, order);
			
			// Create An Order and Attach To Session
			if (httpSession.getAttribute("Order") != null) {
				Order newOrder = new Order();
				List<OrderItems> orderItems = new ArrayList<>();
				newOrder.setOrderItems(orderItems);
				httpSession.setAttribute("Order", null);
			}

			return new ModelAndView("OrderSummary","order",order);
		} catch (Exception e) {
			System.out.println("Error::submitOrder::"+e.getMessage());
			e.printStackTrace();
			return new ModelAndView("userNewOrder").addObject("customer", customer);
		}
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
					OrderItems orderitm = orditm.next();
					if (request.getParameter("item[ItemName]").equals(orderitm.getItemName())) {
						orderitm.setOrder(null);
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
		//Create the Response JSON Array
		JSONArray jsonArray = new JSONArray();
		
		//Get the Order from the Session
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

			// Check if Item is Existing in the order
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
				int preparationTime = Integer.valueOf((String) jsonOrderItm.get("PreparationTime"));
				int quantity = (int) jsonOrderItm.get("Quantity");

				OrderItems orderitm = new OrderItems();
				orderitm.setOrder(order);
				orderitm.setCalories(calories);
				orderitm.setCategory(category);
				orderitm.setItemName(ItemName);
				orderitm.setPreparationTime(preparationTime);
				orderitm.setUnitPrice(unitPrice);
				orderitm.setQuantity(quantity);
				
				//Add the Item to the OrderItems List
				orderItems.add(orderitm);
				
				//Set the Order Items back to the Order
				order.setOrderItems(orderItems);

				//Update the order on the session
				httpSession.setAttribute("Order", order);
			}

			//Generate the Response
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
		
		//Send the Response
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
	
	@RequestMapping(value = "getRatingDetails", method = RequestMethod.POST)
	public void getRatingDetails(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Customer customer = (Customer) httpSession.getAttribute("customer");
		
		JSONArray jsonArray = new JSONArray();
		
		System.out.println("In getrating Details ==> " + httpSession.getAttribute("customer").toString());
		
		if(customer != null){
			System.out.println("In getrating Details: "+customer.getId());
			List<OrderItems> items = customerService.getRatingDetails(customer.getId());
			HashMap<Long, ArrayList<String>> map = new HashMap<Long, ArrayList<String>>();
			
			for (OrderItems item: items) {
				if(!map.containsKey(item.getOrder().getOrderId())){
					map.put(item.getOrder().getOrderId(), new ArrayList<String>());
				}
				map.get(item.getOrder().getOrderId()).add(item.getItemName());
			}

			Iterator<Entry<Long, ArrayList<String>>> it = map.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry<Long, ArrayList<String>> entry = it.next();
				JSONObject temp = new JSONObject();
				temp.put("orderId", entry.getKey());
				JSONArray orderItems = new JSONArray();
				for(String item : entry.getValue()){
					JSONObject temp2 = new JSONObject();
					temp2.put("item_name", item);
					temp2.put("rating", 1);
					temp2.put("order_id", entry.getKey());
					temp2.put("customer_id", customer.getId());
					orderItems.put(temp2);
				}
				temp.put("items", orderItems);
				jsonArray.put(temp);
			}
			
		}
		response.setContentType("application/json");
		response.getWriter().write(jsonArray.toString());
	}
	
	@RequestMapping(value = "submitRating", method = RequestMethod.POST)
	public void submitRating(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Gson gson = new Gson();
		Enumeration param = request.getParameterNames();
		while(param.hasMoreElements()){
			String paramname = (String) param.nextElement();
			System.out.println(paramname);
		}
		
		System.out.println(request.getParameter("items"));
		JSONArray orderItems = new JSONArray(request.getParameter("items"));
		List<ItemRating> items = new ArrayList<ItemRating>();
		for(int i=0; i<orderItems.length();i++){
			JSONObject temp = orderItems.getJSONObject(i);
			Item item = itemService.getItemByName(temp.getString("item_name"));
			ItemRating itemRating = new ItemRating(item, temp.getInt("rating"), temp.getInt("customer_id"),temp.getInt("order_id"));
			items.add(itemRating);
		}
		itemService.setRatings(items);
		System.out.println("Ratings submitted");
		
	}

}
