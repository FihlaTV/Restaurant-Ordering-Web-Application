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

<style>

/*CSS For rating-start*/
div.stars {
	width: 270px;
	display: inline-block;
}

input.star {
	display: none;
}

label.star {
	float: right;
	padding: 10px;
	font-size: 30px;
	color: #444;
	transition: all .2s;
}

input.star:checked ~ label.star:before {
	content: '\f005';
	color: #FD4;
	transition: all .25s;
}

input.star-5:checked ~ label.star:before {
	color: #FE7;
	text-shadow: 0 0 20px #952;
}

input.star-1:checked ~ label.star:before {
	color: #F62;
}

label.star:hover {
	transform: rotate(-15deg) scale(1.3);
}

label.star:before {
	content: '\f006';
	font-family: FontAwesome;
}
/*CSS For rating-end*/
</style>

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


			<div class="panel panel-success" ng-show="ratingDetails"
				style="background: rgba(255, 255, 255, 0.9); height: 50%; margin-bottom: 5px; overflow-y: auto">
				<div class="panel-heading">
					<h3 class="panel-title" style="color: #000000;">Ratings</h3>
				</div>
				<div class="panel-body" style="height: 80%; overflow-y: auto;">
					<h4>
					<div class="row" style="padding-bottom: 10px">
						<div class="col-md-2">Order Id</div>

						<div class="col-md-3">Items Name</div>

						<div class="col-md-4" style="text-align: center">Rating</div>

					</div>
					</h4>

					<div class="row" ng-repeat="order in ratingDetails">
						<div class="col-md-2">{{order.orderId}}</div>

						<div class="col-md-8">
							<div class="row" ng-repeat="item in order.items">
								<div class="col-md-4">{{item.item_name}}</div>
								<div class="col-md-5" style="text-align: left">
									<input class="star star-5"
										ng-attr-id="star-5-'{{item}}'-'{{order.orderId}}'"
										type="radio" ng-model="item.rating" value="5" /> <label
										class="star star-5"
										for="star-5-'{{item}}'-'{{order.orderId}}'"></label> <input
										class="star star-4"
										ng-attr-id="star-4-'{{item}}'-'{{order.orderId}}'"
										type="radio" ng-model="item.rating" value="4" /> <label
										class="star star-4"
										for="star-4-'{{item}}'-'{{order.orderId}}'"></label> <input
										class="star star-3"
										ng-attr-id="star-3-'{{item}}'-'{{order.orderId}}'"
										type="radio" ng-model="item.rating" value="3" /> <label
										class="star star-3"
										for="star-3-'{{item}}'-'{{order.orderId}}'"></label> <input
										class="star star-2"
										ng-attr-id="star-2-'{{item}}'-'{{order.orderId}}'"
										type="radio" ng-model="item.rating" value="2" /> <label
										class="star star-2"
										for="star-2-'{{item}}'-'{{order.orderId}}'"></label> <input
										class="star star-1" selected="selected"
										ng-attr-id="star-1-'{{item}}'-'{{order.orderId}}'"
										type="radio" ng-model="item.rating" value="1" /> <label
										class="star star-1"
										for="star-1-'{{item}}'-'{{order.orderId}}'"></label>
								</div>
								<!-- <hr style="width: 100%; color: black; height: 1px; background-color:black;" /> -->
							</div>
						</div>

						<div class="col-md-2">
							<input type="button" ng-click="submitRating(order)"
								value="Submit Rating">
						</div>
						<hr style="width: 100%; color: black; height: 1px; background-color:black;" />
					</div>
				</div>
			</div>


				<!-- 			<table class="table">
				<tr>
					<td>Order Id</td>
					<td>Item</td>	
					<td></td>			
				</tr>
				<tr ng-repeat="order in ratingDetails">
					<td>{{order.orderId}}</td>
						
					<td>
						<ul ng-repeat="item in order.items">
						<div>
						{{item.item_name}} <br>
							<input class="star star-5" ng-attr-id="star-5-'{{item}}'-'{{order.orderId}}'" type="radio" ng-model="item.rating" value="5"/>
	                          <label class="star star-5" for="star-5-'{{item}}'-'{{order.orderId}}'"></label>
	                          <input class="star star-4" ng-attr-id="star-4-'{{item}}'-'{{order.orderId}}'" type="radio" ng-model="item.rating" value="4"/>
	                          <label class="star star-4" for="star-4-'{{item}}'-'{{order.orderId}}'"></label>
	                          <input class="star star-3" ng-attr-id="star-3-'{{item}}'-'{{order.orderId}}'" type="radio" ng-model="item.rating" value="3"/>
	                          <label class="star star-3" for="star-3-'{{item}}'-'{{order.orderId}}'"></label>
	                          <input class="star star-2" ng-attr-id="star-2-'{{item}}'-'{{order.orderId}}'" type="radio" ng-model="item.rating" value="2"/>
	                          <label class="star star-2" for="star-2-'{{item}}'-'{{order.orderId}}'"></label>
	                          <input class="star star-1" selected="selected" ng-attr-id="star-1-'{{item}}'-'{{order.orderId}}'" type="radio" ng-model="item.rating" value="1"/>
	                          <label class="star star-1" for="star-1-'{{item}}'-'{{order.orderId}}'"></label>
	                          <input type="hidden" id="rideId" name="rideId" data-ng-model="rideData.id">
	                          <input type="hidden" id="driverId" name="driverId" data-ng-model="rideData.driver_id">
	                          <br>
	                         </div>
                         </ul>
					</td>
					<td>
						<input type="button" ng-click="submitRating(order)" value="Submit Rating">
					</td>
				</tr>
			</table> -->

			<div class="row" style="height: 50%;">
				<div class="col-md-12 text-left">
					<div class="panel panel-success"
						style="background: rgba(255, 255, 255, 0.9); height: 100%;">
						<div class="panel-heading">
							<h3 class="panel-title" style="color: #000000;">Order
								History</h3>
						</div>
						<div class="panel-body" style="height: 80%; overflow-y: auto;">
							<div class="well well-sm"
								ng-repeat="order in orderHistory | orderBy:'OrderPlacementTime':true ">
								<div class="row">
									<div class="col-md-8">
										<dl class="dl-horizontal">
											<dt style="text-align: center;">
												<span style="font-size: large;">Order Id</span>
											</dt>
											<dd>
												<span style="font-size: large;">{{order.Id}}</span>
											</dd>
											<dt style="text-align: center;">
												<span style="font-size: large;">Order Placed On</span>
											</dt>
											<dd>
												<span style="font-size: large;">{{order.OrderPlacementTime
													| date:'medium'}}</span>
											</dd>
											<dt style="text-align: center;">
												<span style="font-size: large;">Order Pickup Time</span>
											</dt>
											<dd>
												<span style="font-size: large;">{{order.PickUpTime |
													date:'medium'}}</span>
											</dd>
											<dt style="text-align: center;">
												<span style="font-size: large;">Order Status</span>
											</dt>
											<dd>
												<span style="font-size: large;">{{order.Status |
													status}}</span>
											</dd>
										</dl>
									</div>
									<div class="col-md-4">
										<div class="row">
											<div class="col-md-12">
												<span style="font-size: x-large; text-align: center;">
													<b>Total </b>{{order.TotalPrice | currency:"USD $"}}
												</span>
											</div>
										</div>
										<div class="row" ng-show="order.Status == 'N'">
											<div class="col-md-8">
												<button type="button"
													class="btn btn-danger btn-sm btn-block"
													ng-click="cancelOrder(order)">Cancel</button>
											</div>
										</div>
									</div>
								</div>
							</div>
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