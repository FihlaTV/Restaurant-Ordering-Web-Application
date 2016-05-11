<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Customer Home</title>
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
<script type="text/javascript" src="../resources/js/homeController.js"></script>
</head>

<body style="overflow: auto;" ng-app="FoodOrderApp"
	ng-controller="HomeController"
	ng-init="init('${_csrf.parameterName}','${_csrf.token}')">
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
						<li class="active"><a href="<c:url value="/user/newOrder" />">Place
								Order</a></li>
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
						<h2>
							Welcome,
							<c:out value="${customer.firstname}" />
							<c:out value="${customer.lastname}" />
						</h2>
						<h4>Here is what you have Ordered from us us before....</h4>
					</div>
				</div>
			</div>
			<div class="row" style="height: 50%;">
				<div class="col-md-12 text-left">
					<div class="panel panel-success"
						style="background: rgba(255, 255, 255, 0.9); height: 100%;">
						<div class="panel-heading">
							<h3 class="panel-title" style="color: #000000;">Order
								History</h3>
						</div>
						<div class="panel-body">
							<ul class="media-list" style="height: 80%; overflow-y: auto;">
								<li class="media" ng-repeat="order in orderHistory">
									<dl class="dl-horizontal">
										<dt style="text-align:center;"><span style="font-size: large;">Order ID</span></dt>
										<dd>{{order.id}}</dd>
										<dt style="text-align:center;"><span style="font-size: medium;">Order ID</span></dt>
										<dd>Client-side scripting generally refers to the
											category of computer programs on the web that are executed
											client-side i.e. by the user's web browser.</dd>
										<dt>Document Tree</dt>
										<dd>The tree of elements encoded in the source document.</dd>
									</dl> <!-- <div class="media-body">
									<div class="row">
									<div class="col-md-2">Order Id : {{order.id}}</div>
									<div class="col-md-2"></div>
									<div class="col-md-2"></div>
									</div>
										<h4 class="media-heading">
											<b>Order Id : {{order.id}}&nbsp;</b>
											<button type="button" class="btn btn-success btn-sm"
												ng-click="AddLineItem(item)">Add Item</button>
										</h4>
										<p style="font-size: 10pt;">
											<b>Order Placed On : </b>{{order.OrderPlacementTime | date:'medium'}}<br> <b>UnitPrice
												:</b> {{order.UnitPrice | currency:"USD$"}}
												<br> <b>Order Pickup Time
												:</b> {{order.PickUpTime | date:'medium'}}
										</p>
									</div></li> -->
							</ul>
						</div>
					</div>
				</div>
			</div>
			<!-- 			<div class="row" style="padding-top: 10px; height: 28%;">
				<div class="col-md-12" style="padding-top: 10px; height: 100%;">
					<div class="panel panel-success"
						style="background: rgba(255, 255, 255, 0.9); height: 100%;">
						<div class="panel-heading">
							<h3 class="panel-title" style="color: #000000;">Order
								Actions</h3>
						</div>
						<div class="panel-body">
							<div class="row"
								style="text-align: center; text-shadow: white; vertical-align: middle; padding: 15px 0px;"">
								<div class="col-md-2"></div>
								<div class="col-md-3"
									style="text-align: center; text-shadow: white; vertical-align: middle; padding: 0px 0px;">
									<span style="font-size: 15pt;">Pickup Date and Time </span>
								</div>
								<div class="col-md-2" style="text-align: left;">

								</div>
								<div class="col-md-2" style="text-align: left;">
								</div>
								<div class="col-md-1"></div>
							</div>

						</div>
					</div>
				</div>
			</div> -->
		</div>
	</div>
</body>

</html>