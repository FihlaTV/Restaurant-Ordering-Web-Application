package TheApp275Final.term.services;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import TheApp275Final.term.model.Customer;
import TheApp275Final.term.model.Order;
import TheApp275Final.term.model.OrderItems;

@Service
public class EmailService {

	public void sendCustomerRegistrationConfirmationMail(Customer customer) {

		// Create the Message Body
		StringBuilder messageBody = new StringBuilder();

		messageBody.append(
				"<html><head><meta charset='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><meta name='viewport' content='width=device-width, initial-scale=1'><meta name='description' content=''><meta name='author' content=''><title>Blank Template for Bootstrap</title><!-- Bootstrap core CSS --><link href='bootstrap/css/bootstrap.css' rel='stylesheet'><!-- Custom styles for this template --><link href='style.css' rel='stylesheet'><!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and mediaqueries --><!--[if lt IE 9]><script src='https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js'></script><script src='https://oss.maxcdn.com/respond/1.4.2/respond.min.js'></script><![endif]--></head><body><div class='container'></div><!-- Bootstrap core JavaScript==================================================--><!-- Placed at the end of the document so the pages load faster --><script src='assets/js/jquery.min.js'></script><script src='bootstrap/js/bootstrap.min.js'></script><!-- IE10 viewport hack for Surface/desktop Windows 8 bug --><script src='assets/js/ie10-viewport-bug-workaround.js'></script><table width='100%' border='0' cellspacing='0' cellpadding='0'bgcolor='#8d8e90'><tbody><tr><td><table width='600' border='0' cellspacing='0' cellpadding='0' bgcolor='#FFFFFF'align='center'><tbody><tr><td><table width='100%' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td width='61'><a href='http://yourlink' target='_blank'><img src='http://www.lifewithfive.com/wp-content/uploads/2014/12/food-delivery-palo-alto.jpg' width='61' height='76' border='0' alt=''></a></td><td width='144'><a href='http://yourlink' target='_blank'><img src='https://upload.wikimedia.org/wikipedia/commons/c/cd/Doorstep_Delivery_Logo.jpg' width='144' height='76' border='0' alt=''></a></td><td width='393'><h1>Food Delivery Service</h1><table width='100%' border='0' cellspacing='0'cellpadding='0'><tbody><tr></tr><tr></tr></tbody></table></td></tr></tbody></table></td></tr><tr><td align='center'>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td><table width='100%' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td width='10%'>&nbsp;</td><td width='80%' align='left' valign='top'><h2>Dear XXXX YYYYYY,</h2><font style='font-family: Verdana, Geneva, sans-serif; color:#666766; font-size:13px; line-height:21px'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Thank you for registering atour Website , You are now one step closer to ordering your favorite foodat the convience of your home. To Complete the Account Activation we requestyou kindly enter this code at our website link shown below&nbsp;<br><h3>Username :  BBBBB</h3><h3><a href='WWWWWW?token=ZZZZZZZ&email=BBBBB'>Click here to Activate you Account</a></h3><h3>Or</h3><p style='font-family: Verdana, Geneva, sans-serif; color:#666766; font-size:13px;'>Copy the Activation Token <b>ZZZZZZZ</b> <br> Visit the Link <a href='WWWWWW'>WWWWWW</a> to Activate the Account Manually by Entering Email and Token Manually.</p><h3 style='color:#666766;font-size:16px;' >Happy Ordering :)</h3><b>Best Regards, &nbsp;<br>Rakshith K<br>Sneha Jain<br>Chandni B<br>Parteek M<b><br></font><link></td><td width='10%'>&nbsp;</td></tr><tr><td>&nbsp;</td><td align='right' valign='top'><link><link><table width='108' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td></td></tr><tr><td align='center' valign='middle' bgcolor='#6ebe44'><font style='font-family: Georgia, 'Times New Roman', Times, serif; color:#ffffff; font-size:14px'><em></em></font></td></tr><tr></tr><tr></tr></tbody></table><link></td><td>&nbsp;</td></tr></tbody></table></td></tr><tr><td><link>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td><img src='http://pingendo.github.io/pingendo-bootstrap/assets/blurry/800x600/10.jpg'width='598' height='7' style='display:block' border='0' alt=''></td></tr><tr></tr><tr><td></td></tr><tr><td>&nbsp;</td></tr><tr><td align='center'><font style='font-family:'Myriad Pro', Helvetica, Arial, sans-serif; color:#231f20; font-size:8px'><strong>Head Office &amp; Registered Office | Food Delivery Service , San JoseState University, San Jose , California , 95112 &nbsp;|&nbsp;lab2userauthentication@gmail.com</strong></font></td></tr><tr><td>&nbsp;</td></tr></tbody></table></td></tr></tbody></table><table width='100%' border='0' cellspacing='0' cellpadding='0' bgcolor='#8d8e90'><tbody><tr></tr></tbody></table><table width='100%' border='0' cellspacing='0' cellpadding='0' bgcolor='#8d8e90'><tbody><tr></tr></tbody></table></body></html>");
		String htmlMessage = messageBody.toString();
		htmlMessage = htmlMessage.replace("XXXX", customer.getFirstname());
		htmlMessage = htmlMessage.replace("YYYYYY", customer.getLastname());
		htmlMessage = htmlMessage.replace("ZZZZZZZ", customer.getUserAccessCode());
		htmlMessage = htmlMessage.replace("WWWWWW", "http://ec2-52-33-41-84.us-west-2.compute.amazonaws.com:8080/term/activateaccount");
		htmlMessage = htmlMessage.replace("BBBBB", customer.getUsername());

		sendEmail(htmlMessage, customer.getUsername(), "CMPE 275 - Complete Account Activation");

	}

	public void sendOrderConfirmationMail(Customer customer, Order order) {
		
		// Create the Message Body
		StringBuilder messageBody = new StringBuilder();
		
		String htmlMessage; 
		
		int count = 0;
		
		try{
			
			messageBody.append("<html><head><meta charset='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><meta name='viewport' content='width=device-width, initial-scale=1'><meta name='description' content=''><meta name='author' content=''><title>Blank Template for Bootstrap</title><!-- Bootstrap core CSS --><link href='bootstrap/css/bootstrap.css' rel='stylesheet'><!-- Custom styles for this template --><link href='style.css' rel='stylesheet'><!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and mediaqueries --><!--[if lt IE 9]><script src='https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js'></script><script src='https://oss.maxcdn.com/respond/1.4.2/respond.min.js'></script><![endif]--></head><body><div class='container'></div><!-- Bootstrap core JavaScript==================================================--><!-- Placed at the end of the document so the pages load faster --><script src='assets/js/jquery.min.js'></script><script src='bootstrap/js/bootstrap.min.js'></script><!-- IE10 viewport hack for Surface/desktop Windows 8 bug --><script src='assets/js/ie10-viewport-bug-workaround.js'></script><table width='100%' border='0' cellspacing='0' cellpadding='0' bgcolor='#8d8e90'><tbody><tr><td><table width='600' border='0' cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' align='center'><tbody><tr><td><table width='100%' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td width='61'><a href='http://yourlink' target='_blank'><img src='http://www.lifewithfive.com/wp-content/uploads/2014/12/food-delivery-palo-alto.jpg' width='61' height='76' border='0' alt=''></a></td><td width='144'><a href='http://yourlink' target='_blank'><img src='https://upload.wikimedia.org/wikipedia/commons/c/cd/Doorstep_Delivery_Logo.jpg' width='144' height='76' border='0' alt=''></a></td><td width='393'><h1>Food Delivery Service</h1><table width='100%' border='0' cellspacing='0' cellpadding='0'><tbody><tr></tr><tr></tr></tbody></table></td></tr></tbody></table></td></tr><tr><td align='center'>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td><table width='100%' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td width='10%'>&nbsp;</td><td width='80%' align='left' valign='top'><h2>Dear XXXX YYYYYY,</h2><font style='font-family: Verdana, Geneva, sans-serif; color:#666766; font-size:13px; line-height:21px'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Thank you for Order, it willnow be Passed on the Our Cooks who are eager to prepare the dishes andKeep it ready for pickup. Please find the Details of the Order Placed below. &nbsp;<br><h3>Order Id : BBBBB</h3> <h3>Order Places On : CCCCC</h3><h3>Order Ready By : DDDDD</h3><h3>Order Pickup On : EEEEE</h3><h3>Order Status : FFFFF</h3>");
			messageBody.append("<table class='table table-bordered table-condensed table-hover table-striped' style='text-align:center;'>"
					+ "<thead><tr><th>#</th><th>Item name</th><th>Quantity</th><th>Price</th></tr></thead>"
					+ "<tbody>");
			
			for(OrderItems items : order.getOrderItems()){
				count++;
				messageBody.append("<tr>");
				messageBody.append("<td>" + count + "</td>");
				messageBody.append("<td>" + items.getItemName() + "</td>");
				messageBody.append("<td>" + items.getQuantity() + "</td>");
				float totalPrice = items.getUnitPrice()*items.getQuantity();
				messageBody.append("<td> $ " + totalPrice + "</td>");
				messageBody.append("</tr>");
			}
			messageBody.append("</tbody></table><h3 style='color:#666766;font-size:16px;'>Happy Ordering :)</h3><b>Best Regards, &nbsp;<br>Rakshith K<br>Sneha Jain<br>Chandni B<br>Parteek M<b><br></b></b></font><b><b><link></b></b></td><td width='10%'>&nbsp;</td></tr><tr><td>&nbsp;</td><td align='right' valign='top'><link><link><table width='108' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td></td></tr><tr><td align='center' valign='middle' bgcolor='#6ebe44'><font style='font-family: Georgia, 'Times New Roman', Times, serif; color:#ffffff; font-size:14px'><em></em></font></td></tr><tr></tr><tr></tr></tbody></table><link></td><td>&nbsp;</td></tr></tbody></table></td></tr><tr><td><img src='http://pingendo.github.io/pingendo-bootstrap/assets/blurry/800x600/10.jpg' width='598' height='7' style='display:block' border='0' alt=''></td></tr><tr><td align='center'><font style='font-family:'Myriad Pro', Helvetica, Arial, sans-serif; color:#231f20; font-size:8px'><strong>Head Office &amp; Registered Office | Food Delivery Service , San JoseState University, San Jose , California , 95112 &nbsp;|&nbsp;lab2userauthentication@gmail.com</strong></font></td></tr><tr><td>&nbsp;</td></tr></tbody></table></td></tr></tbody></table><table width='100%' border='0' cellspacing='0' cellpadding='0' bgcolor='#8d8e90'><tbody><tr></tr></tbody></table><table width='100%' border='0' cellspacing='0' cellpadding='0' bgcolor='#8d8e90'><tbody><tr></tr></tbody></table></body></html>");
			htmlMessage = messageBody.toString();
			htmlMessage = htmlMessage.replace("XXXX", customer.getFirstname());
			htmlMessage = htmlMessage.replace("YYYYYY", customer.getLastname());
			htmlMessage = htmlMessage.replace("BBBBB", Long.toString(order.getOrderId()));
			htmlMessage = htmlMessage.replace("CCCCC", order.getOrderPlacementTime().toString().replace("T", " "));
			htmlMessage = htmlMessage.replace("DDDDD", order.getOrderEndTime().toString().replace("T", " "));
			htmlMessage = htmlMessage.replace("EEEEE", order.getPickUpTime().toString().replace("T", " "));
			htmlMessage = htmlMessage.replace("FFFFF", "Order Submitted");
			
			sendEmail(htmlMessage, customer.getUsername(), "CMPE 275 - Order Confirmation");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void sendOrderPreperationStartedMail(Customer customer, Order order) {
		
		// Create the Message Body
		StringBuilder messageBody = new StringBuilder();
		
		String htmlMessage; 
		
		int count = 0;
		
		try{
			
			messageBody.append("<html><head><meta charset='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><meta name='viewport' content='width=device-width, initial-scale=1'><meta name='description' content=''><meta name='author' content=''><title>Blank Template for Bootstrap</title><!-- Bootstrap core CSS --><link href='bootstrap/css/bootstrap.css' rel='stylesheet'><!-- Custom styles for this template --><link href='style.css' rel='stylesheet'><!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and mediaqueries --><!--[if lt IE 9]><script src='https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js'></script><script src='https://oss.maxcdn.com/respond/1.4.2/respond.min.js'></script><![endif]--></head><body><div class='container'></div><!-- Bootstrap core JavaScript==================================================--><!-- Placed at the end of the document so the pages load faster --><script src='assets/js/jquery.min.js'></script><script src='bootstrap/js/bootstrap.min.js'></script><!-- IE10 viewport hack for Surface/desktop Windows 8 bug --><script src='assets/js/ie10-viewport-bug-workaround.js'></script><table width='100%' border='0' cellspacing='0' cellpadding='0' bgcolor='#8d8e90'><tbody><tr><td><table width='600' border='0' cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' align='center'><tbody><tr><td><table width='100%' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td width='61'><a href='http://yourlink' target='_blank'><img src='http://www.lifewithfive.com/wp-content/uploads/2014/12/food-delivery-palo-alto.jpg' width='61' height='76' border='0' alt=''></a></td><td width='144'><a href='http://yourlink' target='_blank'><img src='https://upload.wikimedia.org/wikipedia/commons/c/cd/Doorstep_Delivery_Logo.jpg' width='144' height='76' border='0' alt=''></a></td><td width='393'><h1>Food Delivery Service</h1><table width='100%' border='0' cellspacing='0' cellpadding='0'><tbody><tr></tr><tr></tr></tbody></table></td></tr></tbody></table></td></tr><tr><td align='center'>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td><table width='100%' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td width='10%'>&nbsp;</td><td width='80%' align='left' valign='top'><h2>Dear XXXX YYYYYY,</h2><font style='font-family: Verdana, Geneva, sans-serif; color:#666766; font-size:13px; line-height:21px'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Hello There , We thought you'll like to know that we have Started to Prepare your Order and will be ready by DDDDD &nbsp;<br><h3>Order Id : BBBBB</h3> <h3>Order Places On : CCCCC</h3><h3>Order Ready By : DDDDD</h3><h3>Order Pickup On : EEEEE</h3><h3>Order Status : FFFFF</h3>");
			messageBody.append("<table class='table table-bordered table-condensed table-hover table-striped' style='text-align:center;'>"
					+ "<thead><tr><th>#</th><th>Item name</th><th>Quantity</th><th>Price</th></tr></thead>"
					+ "<tbody>");
			
			for(OrderItems items : order.getOrderItems()){
				count++;
				messageBody.append("<tr>");
				messageBody.append("<td>" + count + "</td>");
				messageBody.append("<td>" + items.getItemName() + "</td>");
				messageBody.append("<td>" + items.getQuantity() + "</td>");
				float totalPrice = items.getUnitPrice()*items.getQuantity();
				messageBody.append("<td> $ " + totalPrice + "</td>");
				messageBody.append("</tr>");
			}
			
			messageBody.append("</tbody></table><h3 style='color:#666766;font-size:16px;'>Happy Ordering :)</h3><b>Best Regards, &nbsp;<br>Rakshith K<br>Sneha Jain<br>Chandni B<br>Parteek M<b><br></b></b></font><b><b><link></b></b></td><td width='10%'>&nbsp;</td></tr><tr><td>&nbsp;</td><td align='right' valign='top'><link><link><table width='108' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td></td></tr><tr><td align='center' valign='middle' bgcolor='#6ebe44'><font style='font-family: Georgia, 'Times New Roman', Times, serif; color:#ffffff; font-size:14px'><em></em></font></td></tr><tr></tr><tr></tr></tbody></table><link></td><td>&nbsp;</td></tr></tbody></table></td></tr><tr><td><img src='http://pingendo.github.io/pingendo-bootstrap/assets/blurry/800x600/10.jpg' width='598' height='7' style='display:block' border='0' alt=''></td></tr><tr><td align='center'><font style='font-family:'Myriad Pro', Helvetica, Arial, sans-serif; color:#231f20; font-size:8px'><strong>Head Office &amp; Registered Office | Food Delivery Service , San JoseState University, San Jose , California , 95112 &nbsp;|&nbsp;lab2userauthentication@gmail.com</strong></font></td></tr><tr><td>&nbsp;</td></tr></tbody></table></td></tr></tbody></table><table width='100%' border='0' cellspacing='0' cellpadding='0' bgcolor='#8d8e90'><tbody><tr></tr></tbody></table><table width='100%' border='0' cellspacing='0' cellpadding='0' bgcolor='#8d8e90'><tbody><tr></tr></tbody></table></body></html>");
			htmlMessage = messageBody.toString();
			htmlMessage = htmlMessage.replace("XXXX", customer.getFirstname());
			htmlMessage = htmlMessage.replace("YYYYYY", customer.getLastname());
			htmlMessage = htmlMessage.replace("BBBBB", Long.toString(order.getOrderId()));
			htmlMessage = htmlMessage.replace("CCCCC", order.getOrderPlacementTime().toString().replace("T", " "));
			htmlMessage = htmlMessage.replace("DDDDD", order.getOrderEndTime().toString().replace("T", " "));
			htmlMessage = htmlMessage.replace("EEEEE", order.getPickUpTime().toString().replace("T", " "));
			htmlMessage = htmlMessage.replace("FFFFF", "Preparation In Progress");
			
			sendEmail(htmlMessage, customer.getUsername(), "CMPE 275 - Order Status Update - Preparing Order");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void sendOrderReadyForPickupMail(Customer customer, Order order) {
		
		// Create the Message Body
		StringBuilder messageBody = new StringBuilder();
		
		String htmlMessage; 
		
		int count = 0;
		
		try{
			
			messageBody.append("<html><head><meta charset='utf-8'><meta http-equiv='X-UA-Compatible' content='IE=edge'><meta name='viewport' content='width=device-width, initial-scale=1'><meta name='description' content=''><meta name='author' content=''><title>Blank Template for Bootstrap</title><!-- Bootstrap core CSS --><link href='bootstrap/css/bootstrap.css' rel='stylesheet'><!-- Custom styles for this template --><link href='style.css' rel='stylesheet'><!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and mediaqueries --><!--[if lt IE 9]><script src='https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js'></script><script src='https://oss.maxcdn.com/respond/1.4.2/respond.min.js'></script><![endif]--></head><body><div class='container'></div><!-- Bootstrap core JavaScript==================================================--><!-- Placed at the end of the document so the pages load faster --><script src='assets/js/jquery.min.js'></script><script src='bootstrap/js/bootstrap.min.js'></script><!-- IE10 viewport hack for Surface/desktop Windows 8 bug --><script src='assets/js/ie10-viewport-bug-workaround.js'></script><table width='100%' border='0' cellspacing='0' cellpadding='0' bgcolor='#8d8e90'><tbody><tr><td><table width='600' border='0' cellspacing='0' cellpadding='0' bgcolor='#FFFFFF' align='center'><tbody><tr><td><table width='100%' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td width='61'><a href='http://yourlink' target='_blank'><img src='http://www.lifewithfive.com/wp-content/uploads/2014/12/food-delivery-palo-alto.jpg' width='61' height='76' border='0' alt=''></a></td><td width='144'><a href='http://yourlink' target='_blank'><img src='https://upload.wikimedia.org/wikipedia/commons/c/cd/Doorstep_Delivery_Logo.jpg' width='144' height='76' border='0' alt=''></a></td><td width='393'><h1>Food Delivery Service</h1><table width='100%' border='0' cellspacing='0' cellpadding='0'><tbody><tr></tr><tr></tr></tbody></table></td></tr></tbody></table></td></tr><tr><td align='center'>&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td><table width='100%' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td width='10%'>&nbsp;</td><td width='80%' align='left' valign='top'><h2>Dear XXXX YYYYYY,</h2><font style='font-family: Verdana, Geneva, sans-serif; color:#666766; font-size:13px; line-height:21px'>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; Great News, Our Chefs have prepared your order to perfrection just for you to enjoy.Please come by to Pickup your order at EEEEE &nbsp;<br><h3>Order Id : BBBBB</h3> <h3>Order Places On : CCCCC</h3><h3>Order Ready By : DDDDD</h3><h3>Order Pickup On : EEEEE</h3><h3>Order Status : FFFFF</h3>");
			messageBody.append("<table class='table table-bordered table-condensed table-hover table-striped' style='text-align:center;'>"
					+ "<thead><tr><th>#</th><th>Item name</th><th>Quantity</th><th>Price</th></tr></thead>"
					+ "<tbody>");
			
			for(OrderItems items : order.getOrderItems()){
				count++;
				messageBody.append("<tr>");
				messageBody.append("<td>" + count + "</td>");
				messageBody.append("<td>" + items.getItemName() + "</td>");
				messageBody.append("<td>" + items.getQuantity() + "</td>");
				float totalPrice = items.getUnitPrice()*items.getQuantity();
				messageBody.append("<td> $ " + totalPrice + "</td>");
				messageBody.append("</tr>");
			}
			
			messageBody.append("</tbody></table><h3 style='color:#666766;font-size:16px;'>Happy Ordering :)</h3><b>Best Regards, &nbsp;<br>Rakshith K<br>Sneha Jain<br>Chandni B<br>Parteek M<b><br></b></b></font><b><b><link></b></b></td><td width='10%'>&nbsp;</td></tr><tr><td>&nbsp;</td><td align='right' valign='top'><link><link><table width='108' border='0' cellspacing='0' cellpadding='0'><tbody><tr><td></td></tr><tr><td align='center' valign='middle' bgcolor='#6ebe44'><font style='font-family: Georgia, 'Times New Roman', Times, serif; color:#ffffff; font-size:14px'><em></em></font></td></tr><tr></tr><tr></tr></tbody></table><link></td><td>&nbsp;</td></tr></tbody></table></td></tr><tr><td><img src='http://pingendo.github.io/pingendo-bootstrap/assets/blurry/800x600/10.jpg' width='598' height='7' style='display:block' border='0' alt=''></td></tr><tr><td align='center'><font style='font-family:'Myriad Pro', Helvetica, Arial, sans-serif; color:#231f20; font-size:8px'><strong>Head Office &amp; Registered Office | Food Delivery Service , San JoseState University, San Jose , California , 95112 &nbsp;|&nbsp;lab2userauthentication@gmail.com</strong></font></td></tr><tr><td>&nbsp;</td></tr></tbody></table></td></tr></tbody></table><table width='100%' border='0' cellspacing='0' cellpadding='0' bgcolor='#8d8e90'><tbody><tr></tr></tbody></table><table width='100%' border='0' cellspacing='0' cellpadding='0' bgcolor='#8d8e90'><tbody><tr></tr></tbody></table></body></html>");
			htmlMessage = messageBody.toString();
			htmlMessage = htmlMessage.replace("XXXX", customer.getFirstname());
			htmlMessage = htmlMessage.replace("YYYYYY", customer.getLastname());
			htmlMessage = htmlMessage.replace("BBBBB", Long.toString(order.getOrderId()));
			htmlMessage = htmlMessage.replace("CCCCC", order.getOrderPlacementTime().toString().replace("T", " "));
			htmlMessage = htmlMessage.replace("DDDDD", order.getOrderEndTime().toString().replace("T", " "));
			htmlMessage = htmlMessage.replace("EEEEE", order.getPickUpTime().toString().replace("T", " "));
			htmlMessage = htmlMessage.replace("FFFFF", "Ready For Pickup");
			
			sendEmail(htmlMessage, customer.getUsername(), "CMPE 275 - Order Status Update - Ready for Pickup");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void sendEmail(String htmlMessage, String destinationEmail, String subject) {

		final String username = "lab2userauthentication@gmail.com";
		final String password = "testpass1234";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		try {

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			String hostname = null;
			InetAddress ip;
			try {
				ip = InetAddress.getLocalHost();
				hostname = ip.getHostName();
				System.out.println("Your current IP address : " + ip);
				System.out.println("Your current Hostname : " + hostname);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

			// Create the Message to Send
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("lab2userauthentication@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinationEmail));
			message.setSubject(subject);
			message.setContent(htmlMessage, "text/html; charset=utf-8");

			// Send the Message
			Transport.send(message);

			System.out.println("Message Sent to Customer!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
