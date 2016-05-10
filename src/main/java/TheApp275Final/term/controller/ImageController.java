package TheApp275Final.term.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import TheApp275Final.term.services.ItemService;

@Controller
public class ImageController {

	@Autowired
	ItemService itemService;
	
	@RequestMapping(value="/images/{itemId}", method = RequestMethod.GET)
	public ModelAndView addItem(HttpServletResponse response, @PathVariable("itemId") int itemId) throws IOException{
		
		byte[] imageBytes = itemService.getImage(itemId);
		if(imageBytes!=null&&imageBytes.length>0){
			response.setContentType("image/gif");
			OutputStream o = response.getOutputStream();
			o.write(imageBytes);
			o.flush();
			o.close();
		}
		return new ModelAndView("items");
	}
	
}
