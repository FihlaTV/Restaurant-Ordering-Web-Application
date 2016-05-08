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
</head>

<body style="overflow: hidden;">
	<div class="cover">
		<div class="navbar navbar-default navbar-fixed-top"
			style="background: rgba(200, 54, 54, 0.7);">
			<div class="container" style="background: rgba(255, 255, 255, 0.7);">
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
			style="background-image: url('https://upload.wikimedia.org/wikipedia/commons/c/c1/Indian-Food-wikicont.jpg')"></div>
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="row"></div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 text-left">
					<div class="text-left well well-sm"
						style="background: rgba(255, 255, 255, 0.7);">
						<h2>Welcome,  <c:out value="${customer.firstname}" /> <c:out value="${customer.lastname}" /></h2>
					</div>
				</div>
			</div>
			<div class="row" style="height: 50%;">
				<div class="col-md-6 text-left">
					<div class="panel panel-success"
						style="background: rgba(255, 255, 255, 0.7); height: 50%;">
						<div class="panel-heading">
							<h3 class="panel-title" style="color: #000000;">Customer
								Actions</h3>
						</div>
						<div class="panel-body">
							<a class="btn btn-block btn-lg btn-success"
								href="<c:url value="/user/neworder" />">Place New Orders</a> <a
								class="btn btn-block btn-lg btn-danger"
								href="<c:url value="/logout" />">Logout</a>
						</div>
					</div>
				</div>
				<div class="col-md-6 text-left">
					<div class="panel panel-success"
						style="background: rgba(255, 255, 255, 0.7); height: 100%;">
						<div class="panel-heading">
							<h3 class="panel-title" style="color: #000000;">Customer
								Order History</h3>
						</div>
						<div class="panel-body">
							<ul class="media-list" style="height: 80%; overflow-y: auto;">
								<li class="media"><a class="pull-left" href="#"><img
										class="media-object"
										src="http://pingendo.github.io/pingendo-bootstrap/assets/placeholder.png"
										height="64" width="64"></a>
									<div class="media-body">
										<h4 class="media-heading">Media heading</h4>
										<p>Cras sit amet nibh libero, in gravida nulla. Nulla vel
											metus scelerisque ante sollicitudin commodo. Cras purus odio,
											vestibulum in vulputate at, tempus viverra turpis.</p>
									</div></li>
								<li class="media"><a class="pull-left" href="#"><img
										class="media-object"
										src="http://pingendo.github.io/pingendo-bootstrap/assets/placeholder.png"
										height="64" width="64"></a>
									<div class="media-body">
										<h4 class="media-heading">Media heading</h4>
										<p>Cras sit amet nibh libero, in gravida nulla. Nulla vel
											metus scelerisque ante sollicitudin commodo. Cras purus odio,
											vestibulum in vulputate at, tempus viverra turpis.</p>
									</div></li>
								<li class="media"><a class="pull-left" href="#"><img
										class="media-object"
										src="http://pingendo.github.io/pingendo-bootstrap/assets/placeholder.png"
										height="64" width="64"></a>
									<div class="media-body">
										<h4 class="media-heading">Media heading</h4>
										<p>Cras sit amet nibh libero, in gravida nulla. Nulla vel
											metus scelerisque ante sollicitudin commodo. Cras purus odio,
											vestibulum in vulputate at, tempus viverra turpis.</p>
									</div></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12"></div>
			</div>
		</div>
	</div>
</body>

</html>