package TheApp275Final.term.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import TheApp275Final.term.services.OrderSchedulingService;

@Controller
public class HomeController {

	@Autowired
	private OrderSchedulingService orderSchedulingService;

	@RequestMapping(value = "/")
	public ModelAndView test(HttpServletResponse response) throws IOException {
		return new ModelAndView("home");
	}

	@RequestMapping(value = "/postData", method = RequestMethod.POST)
	public ModelAndView mockController(
			@RequestParam(value = "pickup_date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date pickupDate,
			@RequestParam(value = "tot_proc_time", required = false) int mins, HttpServletResponse response,
			@RequestParam(value = "pickup_time", required = false) String pickupTime) throws IOException {
		System.out.println(pickupDate.toString() + " " + mins + " " + "variables they are");
		ModelMap map = new ModelMap();
		map.addAttribute("pickup", pickupDate);
		map.addAttribute("mins", mins);
		if (orderSchedulingService.checkPickUpTime(pickupTime)) {
			orderSchedulingService.testInputdata();
			//orderSchedulingService.findEmptyPipeline(pickupDate, pickupTime, mins);
			/**
			 * calculate preparation start and end time check if any pipeline is
			 * empty to fullfill the order if not - tell user to choose
			 * alternate pick up time
			 * 
			 */

		} else {
			// Go to choose Alternate Pick up time
		}
		ModelAndView mv = new ModelAndView("home2", map);
		return mv;

	}

	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
}
