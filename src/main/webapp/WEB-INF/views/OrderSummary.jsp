<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script type="text/javascript"
	src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
<script type="text/javascript"
	src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<link
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<link
	href="http://pingendo.github.io/pingendo-bootstrap/themes/default/bootstrap.css"
	rel="stylesheet" type="text/css">
<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.26/angular.min.js"></script>
<script
	src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.0-beta.1/angular-sanitize.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/angular-filter/0.4.7/angular-filter.js"></script>
</head>

<body style="overflow: auto;">
	<div class="cover">
		<div class="navbar navbar-default navbar-fixed-top"
			style="background: rgba(200, 54, 54, 0.7);">
			<div class="container" style="background: rgba(255, 255, 255, 0.9);">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target="#navbar-ex-collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand"><span>Food Delivery Service</span></a>
				</div>
				<div class="collapse navbar-collapse" id="navbar-ex-collapse">
					<ul class="nav navbar-nav navbar-right">
						<li class="active"><a href="<c:url value="/user/" />">Home</a></li>
						<li><a href="<c:url value="/logout" />">Logout</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="background-image-fixed cover-image"
			style="background-image: url('https://upload.wikimedia.org/wikipedia/commons/c/c1/Indian-Food-wikicont.jpg');"></div>
		<div class="container">
			<div class="row" style="padding-bottom: 70px;">
				<div class="col-md-12">
					<div class="row"></div>
				</div>
			</div>
			
			
			<div class="row">
				<div class="col-md-12 text-left">
					<div class="text-left well well-sm"
						style="background: rgba(255, 255, 255, 0.9);">
						<center><h4>Your order has been placed !!</h4>
						<b><h4>Order Id</b>
						</center>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 text-left">
					<div class="text-left well well-sm"
						style="background: rgba(255, 255, 255, 0.9);">
						<center><h4>The Order Includes</h4>
						
						</center>
					</div>
				</div>
			</div>
			
		</div>
	</div>
</body>

</html>