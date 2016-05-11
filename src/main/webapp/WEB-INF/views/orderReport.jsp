<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Order Report Page</title>

<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
	<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.26/angular.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.0-beta.1/angular-sanitize.js"></script>

<script type="text/javascript" src="../resources/js/orderReport.js"></script>
</head>
<body ng-app="orderReportPageApp" ng-controller="orderReportController" ng-init='initToken("${_csrf.parameterName}","${_csrf.token}");'
	style="padding-top: 80px;">

<header>
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
						<li class="active"><a href="<c:url value="/admin/" />">Home</a></li>
						<li><a href="<c:url value="/logout" />">Logout</a></li>
					</ul>
				</div>
			</div>
		</div>
		
	</header>

<div class="container">
    <table class="table">
    <form ng-submit="getData()">
    	<tr>
    		<td>Start Date</td>
    		<td>
    			<input required type='date' ng-model="fromDate" value="2016-05-01"/>
                <input required  type='time'  ng-model="fromTime" value="00:00:00"/>
    		</td>
    	</tr>
    	
    	<tr>
    		<td>End Date</td>
    		<td>
    			<input required type='date' ng-model="toDate" value="2016-05-20" />
                <input required type='time' ng-model="toTime" value="00:00:00"/>
    		</td>
    	</tr>
    	<tr>
    		<td>Sort By</td>
    		<td>
    			<input ng-model="sortBy" type="radio" name="Order Time" value="ORDER_PLACEMENT_TIME" checked="checked" style="margin-right:10px" />Get data by Order Time
				<br>
				<input ng-model="sortBy" type="radio" name="Fulfillment Start Time" value="ORDER_START_TIME" style="margin-right:10px" />Get data by Fulfillment Start Time
    		</td>
    	</tr>
    	<tr>
    		<td colspan="2">
    			<input type="submit" value="Submit">
    		</td>
    	</tr>
    	
    	</form>
    </table>
    
    <table class="table">
		<tr ng-show="orders.length>0">
		<td>ID</td>
		<td>Total</td>
		<td>Order Start Time</td>
		<td>Order End Time</td>
		<td>Pick Up Time</td>
		<td>Order Time</td>
		<td>Status</td>
		<td>User email</td>
		<td>Item Details</td>
		</tr>
		<tr ng-repeat="order in orders">
		<td>{{order.id}}</td>
		<td>$ {{order.total}}</td>
		<td>{{order.orderStartTime | datetime}}</td>
		<td>{{order.orderEndTime| datetime}}</td>
		<td>{{order.pickUpTime| datetime}}</td>
		<td>{{order.orderTime| datetime}}</td>
		<td>{{order.status}}</td>
		<td>{{order.username}}</td>
		<td>
			<ul ng-repeat="item in order.items">
			{{item.itemName}}  {{item.quantity}}
			</ul>
		</td>
		</tr>
		</table>

</div>




</body>
</html>