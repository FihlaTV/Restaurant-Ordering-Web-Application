<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<title>Account Activation</title>
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

<body>
	<div class="cover">
		<div class="navbar">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target="#navbar-ex-collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="<c:url value='/login'/>"><span
						style="font-size: 20px; color: #FFFFFF">Food Ordering
							Service</span></a>
				</div>
				<div class="collapse navbar-collapse" id="navbar-ex-collapse">
					<ul class="nav navbar-nav navbar-right">
						<li class="active"><a href="<c:url value='/'/>"
							style="font-size: 20px; color: #FFFFFF">Home</a></li>
						<li class="active"><a href="<c:url value='/login'/>"
							style="font-size: 20px; color: #FFFFFF">Login</a></li>
						<li class="active"><a href="<c:url value='/signup'/>"
							style="font-size: 20px; color: #FFFFFF">Signup</a></li>
					</ul>
				</div>
			</div>
		</div>
		<div class="background-image-fixed cover-image"
			style="background-image: url('https://pixabay.com/static/uploads/photo/2014/06/11/17/00/cook-366875_960_720.jpg')"></div>
		<div class="container">
			<div class="row">
				<div class="col-md-12 text-center">
					<h1 style="font-size: 45px; color: #FFFFFF">Food Ordering
						Service</h1>
					<c:if test="${param.activated != null}">
						<div class="alert alert-success">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							<span style="font-size: 20px; color: #000000"><strong>Congratulations!!</strong>
								Your Account is activated Successfully!!</span>
						</div>
					</c:if>
					<c:if test="${param.error != null}">
						<div class="alert alert-danger">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							<span style="font-size: 20px; color: #000000"><strong>Error!!</strong>
								${param.errormsg}</span>
						</div>
					</c:if>
					<c:url var="activationURL" value="/activateaccount" />
					<form action="${activationURL}" method="get" role="form">
						<div class="form-group">
							<input class="form-control input-lg" id="email"
								placeholder="Email" name="email" type="email" required>
						</div>
						<div class="form-group">
							<input class="form-control input-lg" id="token"
								placeholder="Token" name="token" type="text" required>
						</div>
						<button type="submit" class="btn btn-lg btn-primary">
							Activate Account <i class="fa fa-star fa-fw"></i>
						</button>
					</form>
					<br> <br>
				</div>
			</div>
		</div>
	</div>
</body>

</html>