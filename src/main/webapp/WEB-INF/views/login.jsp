<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Login Page</title>

<link href="<c:url value='resources/css/loginstyle.css'/>"	rel="stylesheet"></link>
<link rel="stylesheet" type="text/css"
	href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css" />
</head>

<body>
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:700,600'
		rel='stylesheet' type='text/css'>
	<div class="row">
		<div class="col-xs-12">
			<c:if test="${param.error != null}">
				<div class="alert alert-danger">
					<h1>
						<span id="alertText">Invalid Username or Password!!!
							Please Try Again..</span>
					</h1>
				</div>
			</c:if>
			<c:if test="${param.logout != null}">
				<div class="alert alert-success">
					<h1>
						<span id="alertText">You have been logged out Successfully.</span>
					</h1>
				</div>
			</c:if>
			<c:if test="${param.signup != null}">
				<div class="alert alert-success">
					<h1>
						<span id="alertText">You Account has been Created
							Successfully, To activate your Account kindly follow the
							instructions sent in the Activation Mail</span>
					</h1>
				</div>
			</c:if>
			<c:if test="${param.signuperror != null}">
				<div class="alert alert-success">
					<h1>
						<span id="alertText">Error!! ${param.signuperrormsg}</span>
					</h1>
				</div>
			</c:if>
		</div>
	</div>
	<div class="box" style="background: rgba(255, 255, 255, 0.7);">
		<h1>Customer Login</h1>
		<c:url var="loginUrl" value="/login" />
		<c:url var="signUpUrl" value="/signup" />
		<div class="row">
			<div class="col-xs-12">
				<form action="${loginUrl}" method="post" class="form-horizontal"
					id="loginForm">
					<div class="row">
						<div class="col-xs-12">
							<input type="email" name="username" value="email"
								onFocus="field_focus(this, 'email');"
								onblur="field_blur(this, 'email');" class="email" />
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" /> <input type="password"
								name="password" value="email"
								onFocus="field_focus(this, 'email');"
								onblur="field_blur(this, 'email');" class="email" />
						</div>
					</div>
				</form>
				<form action="${signUpUrl}" id="signUpForm"></form>
				<div class="row">
					<div class="col-xs-6">
						<input type="submit" class="btn btn-block btn-primary btn-default"
							value="Log in" form="loginForm"> <input type="submit"
							class="btn btn-block btn-primary btn-default" value="Sign Up"
							form="signUpForm">
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<style>
</style>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"
	type="text/javascript"></script>
<script>
	function field_focus(field, email) {
		if (field.value == email) {
			field.value = '';
		}
	}

	function field_blur(field, email) {
		if (field.value == '') {
			field.value = email;
		}
	}

	//Fade in dashboard box
	$(document).ready(function() {
		$('.box').hide().fadeIn(1000);
	});

	//Stop click event
	$('a').click(function(event) {
		event.preventDefault();
	});
</script>