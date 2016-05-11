package TheApp275Final.term.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrderSummaryController {

	@Autowired
	private HttpSession httpSession;
}
