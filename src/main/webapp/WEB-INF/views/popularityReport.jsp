<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Populartity Page</title>

<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
	<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.26/angular.min.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.0-beta.1/angular-sanitize.js"></script>

<script type="text/javascript" src="../resources/js/popularityReport.js"></script>
</head>
<body ng-app="popularityReportPageApp" ng-controller="popularityReportController" ng-init='initToken("${_csrf.parameterName}","${_csrf.token}");'
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
    		<td colspan="2">
    			<input type="submit" value="Submit">
    		</td>
    	</tr>
    	</form>
    </table>
     
     <table class="table">
		<tr>
		<td>Category</td>
		<td></td>
		</tr>
		<tr ng-repeat="item in items">			
			<th>{{item.category}}</th>
			<td>
				<ul ng-repeat="details in item.items">
				{{details.itemName}} {{details.count}}
				</ul>
			</td>
		</tr>
		<tr>
			 
			</ul>
		</tr>
	</table>          
    
</div>
</body>
</html>