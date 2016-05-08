<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
	<form action="postData" method="POST">
        <h1>Hello World!</h1>
        <p>This is the homepage!</p>
        Enter pickup_date :
        <input type="date" name="pickup_date" />
        <br/>
        Enter total processing time 
        <input type="text" name="tot_proc_time" />
        <br/>
          Enter user pickup time 
        <input type="text" name="pickup_time" />
        <br/>
        
        <input type="submit" name="Submit" value="submit" />
		</form>
	</body>
</html>