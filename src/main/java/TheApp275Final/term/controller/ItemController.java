package TheApp275Final.term.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import TheApp275Final.term.model.Item;
import TheApp275Final.term.model.Order;
import TheApp275Final.term.model.OrderItems;
import TheApp275Final.term.model.OrderItemsDisplay;
import TheApp275Final.term.services.IOrderService;
import TheApp275Final.term.services.ItemService;
import TheApp275Final.term.services.OrderService;


@Controller
public class ItemController {
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	IOrderService orderService;
	
	@Value("#{'${item.category}'.split(',')}")
	private List<String> category;
	
	@RequestMapping(value={"/admin/getAllItems","/admin/"})
	public ModelAndView getAllItems(HttpServletResponse response, Model model) throws IOException{
		response.setContentType("application/json");
		List<Item> itemList = itemService.getAllItems();
//		for(Item item : itemList){
//			item.setPicture(null);
//		}
		JSONArray jsonArray = new JSONArray();
		for(Item item:itemList){
			JSONObject temp = new JSONObject();
			temp.put("itemName", item.getItemName());
			temp.put("category", item.getCategory());
			temp.put("calories",item.getCalories());
			temp.put("id",item.getId());
			temp.put("preparationTime",item.getPreparationTime());
			temp.put("unitPrice",item.getUnitPrice());
			temp.put("status", item.isStatus());
			jsonArray.put(temp);
		}
		model.addAttribute("items", jsonArray);
//		model.addAttribute("items", new Gson().toString(itemList));
		model.addAttribute("categories", new Gson().toJson(category));
		return new ModelAndView("items");
	}
	
//	@RequestMapping(value={"/admin/getAllItems","/admin/"})
//	public ModelAndView getAllItems(HttpServletResponse response, Model model) throws IOException{
//		response.setContentType("application/json");
//		List<Item> itemList = itemService.getAllItems();
//		for(Item item : itemList){
//			item.setPicture(null);
//		}
//		model.addAttribute("items", new Gson().toJson(itemList));
//		model.addAttribute("categories", new Gson().toJson(category));
//		return new ModelAndView("items");
//	}
	
	@RequestMapping(value="/admin/deleteItem", method = RequestMethod.POST)
	public ModelAndView deleteItem(HttpServletRequest request) throws IOException{
		Gson gson = new Gson();
		Item item = gson.fromJson(request.getParameter("item"), Item.class);
		itemService.deleteItem(item);
		return new ModelAndView("items");
	}
	
	@RequestMapping(value="/admin/enableItem", method = RequestMethod.POST)
	public ModelAndView enableItem(HttpServletRequest request) throws IOException{
		System.out.println("In enable"+request.getParameter("item"));
		Gson gson = new Gson();
		Item item = gson.fromJson(request.getParameter("item"), Item.class);
		itemService.deleteItem(item);
		return new ModelAndView("items");
	}
	
	@RequestMapping(value="/admin/addItem", method = RequestMethod.POST)
	public void addItem(HttpServletResponse response,
			@ModelAttribute(value = "item") String itemJson, 
			@ModelAttribute(value = "file") MultipartFile file
			) throws IOException{
		Gson gson = new Gson();
		Item item = gson.fromJson(itemJson, Item.class);
		if(file.getBytes()!=null&&file.getBytes().length>0){
			item.setPicture(file.getBytes());
		}
		itemService.addItem(item);
		response.setContentType("application/json");
		response.getWriter().write(Long.toString(item.getId()));
	}
	
	@RequestMapping(value="/admin/resetOrders", method = RequestMethod.POST)
	public void resetOrders(HttpServletResponse response) throws IOException{
		orderService.resetOrders();
	}
	
	@RequestMapping(value={"/admin/orderReportPage"})
	public ModelAndView getOrderReportPage(Model model) throws IOException{
		return new ModelAndView("orderReport");
	}
	
	@RequestMapping(value={"/admin/popularityReportPage"})
	public ModelAndView getPopulatrityReportPage(HttpServletResponse response, Model model) throws IOException{
		return new ModelAndView("popularityReport");
	}
	
	@RequestMapping(value={"/admin/getOrderReport"})
	public void getOrderReport(HttpServletResponse response, HttpServletRequest request) throws IOException{
		System.out.println(request.getParameter("fromDate"));
		response.setContentType("application/json");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		String sortBy = request.getParameter("sortBy");
		LocalDateTime startTime = LocalDateTime.parse(request.getParameter("fromDate").toString(),formatter);
		LocalDateTime endTime = LocalDateTime.parse(request.getParameter("toDate").toString(),formatter);
		HashMap<Character, String> status = new HashMap<Character, String> ();
		status.put('N', "New");
		status.put('R', "Ready");
		status.put('P', "Processing");
		status.put('F', "Fulfilled");
		List<Order> orders = orderService.getOrderReport(request.getParameter("fromDate"),request.getParameter("toDate"),sortBy);
		float total = 0;
		
		JSONArray jsonArray = new JSONArray();
		for(Order order: orders){
			total =0;
			JSONObject temp = new JSONObject();
			temp.put("id", order.getOrderId());
			temp.put("orderStartTime",order.getOrderStartTime());
			temp.put("orderEndTime",order.getOrderEndTime());
			temp.put("status",status.get(order.getStatus()));
			temp.put("pickUpTime",order.getPickUpTime());
			temp.put("orderTime", order.getOrderPlacementTime());
			temp.put("username", order.getCustomer().getUsername());
			JSONArray orderItems = new JSONArray();
			for(OrderItems orderItem:order.getOrderItems()){
				JSONObject item = new JSONObject();
				item.put("itemName",orderItem.getItemName());
				item.put("quantity",orderItem.getQuantity());
				total += orderItem.getQuantity()*orderItem.getUnitPrice();
				temp.put("items", item);
				orderItems.put(item);
			}
			temp.put("items", orderItems);
			temp.put("total", total);
			jsonArray.put(temp);
		}
		System.out.println(orders.size()+" SD: "+startTime.toString()+" ED:"+endTime.toString()+" "+jsonArray);
		response.getWriter().write(jsonArray.toString());
	}
	
	@RequestMapping(value={"/admin/getPopularityReport"})
	public void getPopularityReport(HttpServletResponse response, HttpServletRequest request) throws IOException{
		System.out.println(request.getParameter("fromDate"));
		response.setContentType("application/json");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		LocalDateTime startTime = LocalDateTime.parse(request.getParameter("fromDate").toString(),formatter);
		LocalDateTime endTime = LocalDateTime.parse(request.getParameter("toDate").toString(),formatter);
		
		List<OrderItems> items = orderService.getPopularityReport(request.getParameter("fromDate"),request.getParameter("toDate"));
		HashMap<String,HashMap<String,Integer>> map = new HashMap<String,HashMap<String,Integer>>();
		for(OrderItems item:items){
			if(!map.containsKey(item.getCategory())){
				map.put(item.getCategory(), new HashMap<String,Integer>());
			}
			if(!map.get(item.getCategory()).containsKey(item.getItemName())){
				map.get(item.getCategory()).put(item.getItemName(),0);
			}
			map.get(item.getCategory()).put(item.getItemName(),map.get(item.getCategory()).get(item.getItemName())+item.getQuantity());			
		}
		
		
		JSONArray jsonArray = new JSONArray();
		Iterator<Entry<String,HashMap<String,Integer>>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String,HashMap<String,Integer>> entry = it.next();
			JSONObject temp = new JSONObject();
			temp.put("category", entry.getKey());
			JSONArray arr = new JSONArray();
			
			MyComparator comp = new MyComparator(entry.getValue());
			Map<String, Integer> newMap = new TreeMap(comp);
			newMap.putAll(entry.getValue());
			
			Iterator<Entry<String,Integer>> it2 = newMap.entrySet().iterator();
			while(it2.hasNext()){
				Map.Entry<String,Integer> entry2 = it2.next();
				JSONObject temp2 = new JSONObject();
				temp2.put("itemName", entry2.getKey());
				temp2.put("count", entry2.getValue());
				arr.put(temp2);
			}
			temp.put("items", arr);
			jsonArray.put(temp);
		}
		response.getWriter().write(jsonArray.toString());
	}
	
}

@SuppressWarnings("rawtypes")
class MyComparator implements Comparator {
    Map map;
    public MyComparator(Map map) {
        this.map = map;
    }
    public int compare(Object o1, Object o2) {
        return ((Integer) map.get(o2)).compareTo((Integer) map.get(o1));
    }
}
