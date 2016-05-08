<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
  
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script type="text/javascript" src="http://cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script type="text/javascript" src="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.3.0/css/font-awesome.min.css"
    rel="stylesheet" type="text/css">
    <link href="http://pingendo.github.io/pingendo-bootstrap/themes/default/bootstrap.css"
    rel="stylesheet" type="text/css">
  </head>
  
  <body>
    <div class="cover">
      <div class="navbar" style="background: rgba(255, 255, 255, 0.7);">
        <div class="container" style="background: rgba(255, 255, 255, 0.7);">
          <div class="navbar-header" style="background: rgba(255, 255, 255, 0.7);">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#navbar-ex-collapse">
              <span class="sr-only">Toggle navigation</span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="<c:url value='/'/>"><span style="font-size: 25px;color: #000000" >Food Order Service</span></a>
          </div>
          <div class="collapse navbar-collapse" id="navbar-ex-collapse">
            <ul class="nav navbar-nav navbar-right">
              <li class="active">
                <a href="<c:url value='/login'/>" style="font-size: 25px;color: #000000">Login</a>
              </li>
              <li>
                <a href="<c:url value='/signup'/>" style="font-size: 25px;color: #000000">SignUp</a>
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div class="cover-image" style="background-image : url('http://static4.businessinsider.com/image/536d34b6ecad045b143e69bc/15-etiquette-rules-for-dining-at-fancy-restaurants.jpg')"></div>
      <div class="container" >
        <div class="row">
          <div class="col-md-12 text-center">
            <h1 class="text-center" style="background: rgba(255, 255, 255, 0.7);">Food Ordering Service</h1>
            <p class="text-center" style="background: rgba(255, 255, 255, 0.7);">Order Food at the Comfort of Your Home :)</p>
            <br>
            <br>
            <a class="btn btn-lg btn-success" href="<c:url value='/signup'/>">Sign Up Now!!</a>
          </div>
        </div>
      </div>
    </div>
  </body>

</html>
