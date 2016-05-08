<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Signup</title>
<%-- <link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link> --%>
<link href="<c:url value='resources/css/signupstyle.css'/>"
	rel="stylesheet"></link>
<link rel="stylesheet" type="text/css"
	href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.2.0/css/font-awesome.css" />
</head>

<body>
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:700,600'
		rel='stylesheet' type='text/css'>
	<div class="row">
		<div class="col-xs-12">
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
		<h1>Customer Sign-up</h1>
		<c:url var="loginUrl" value="/login" />
		<c:url var="signUpUrl" value="/signup" />
		<div class="row">
			<div class="col-xs-12">
				<form action="${signUpUrl}" method="post" class="form-horizontal"
					id="signUpForm">
					<div class="row">
						<div class="col-xs-12">
							<input type="text" name="firstname" placeholder="First Name"
								onFocus="field_focus(this, 'First Name');"
								<c:if test="${param.firstname != null}">value="${param.firstname}"</c:if>
								onblur="field_blur(this, 'First Name');" class="email" required />
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<input type="text" name="lastname" placeholder="Last Name"
								onFocus="field_focus(this, 'Last Name');"
								<c:if test="${param.lastname != null}">value="${param.lastname}"</c:if>
								onblur="field_blur(this, 'Last Name');" class="email" required />
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<input type="email" name="username" placeholder="Email"
								onFocus="field_focus(this, 'Email');"
								<c:if test="${param.username != null}">value="${param.username}"</c:if>
								onblur="field_blur(this, 'Email');" class="email" required />
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" /> <input type="password"
								placeholder="Password" name="password" class="email" required />
						</div>
					</div>
				</form>
				<form action="${loginUrl}" id="loginForm"></form>
				<div class="row">
					<div class="col-xs-6">
						<input type="submit" class="btn btn-block btn-primary btn-default"
							value="SignUp" form="signUpForm"> <input type="submit"
							class="btn btn-block btn-primary btn-default"
							value="Existing User Login" form="loginForm">
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