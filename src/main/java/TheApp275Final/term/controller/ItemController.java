package TheApp275Final.term.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import TheApp275Final.term.services.ItemService;


@Controller
public class ItemController {
	
	@Autowired
	ItemService itemService;
	
	@Value("#{'${item.category}'.split(',')}")
	private List<String> category;
	
	
	@RequestMapping(value="/admin/getAllItems")
	public ModelAndView getAllItems(HttpServletResponse response, Model model) throws IOException{
		response.setContentType("application/json");
		List<Item> itemList = itemService.getAllItems();
		for(int i=0;i<itemList.size();i++){
			itemList.get(i).setPicture(null);
		}
		model.addAttribute("items", new Gson().toJson(itemList));
		model.addAttribute("categories", new Gson().toJson(category));
		return new ModelAndView("items");
	}
	
	@RequestMapping(value="/admin/deleteItem", method = RequestMethod.POST)
	public ModelAndView deleteItem(HttpServletRequest request, @RequestBody String itemJson) throws IOException{
		Gson gson = new Gson();
		Item item = gson.fromJson(itemJson, Item.class);
		itemService.deleteItem(item);
		
		return new ModelAndView("items");
	}
	
	@RequestMapping(value="/admin/enableItem", method = RequestMethod.POST)
	public ModelAndView enableItem(HttpServletRequest request, @RequestBody String itemJson) throws IOException{
		Gson gson = new Gson();
		Item item = gson.fromJson(itemJson, Item.class);
		itemService.deleteItem(item);
		return new ModelAndView("items");
	}
	
	@RequestMapping(value="/admin/addItem", method = RequestMethod.POST)
	public ModelAndView addItem(HttpServletRequest request, 
			@ModelAttribute(value = "item") String itemJson, 
			@ModelAttribute(value = "file") MultipartFile file) throws IOException{
		Gson gson = new Gson();
		Item item = gson.fromJson(itemJson, Item.class);
		if(file.getBytes()!=null&&file.getBytes().length>0){
			item.setPicture(file.getBytes());
		}
		
		itemService.addItem(item);
		return new ModelAndView("items");
	}
	
}
