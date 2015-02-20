<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="com.googlecode.objectify.*" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="guestbook2.Greeting" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
<%  
UserService userService = UserServiceFactory.getUserService();
User user = userService.getCurrentUser();%> 

    
<html>
  <head>
  	 <link type="text/css" rel="stylesheet" href="/stylesheets/superhero.css" />
  </head>
 
 <body>
 <div class="container">
 	<div class="jumbotron">
  		<div class="row">
  			<div class="col-md-6"><h1>What's your story?</h1></div>
			<div class="col-md-6">	
			  	<img src="http://images4.fanpop.com/image/photos/20800000/DOMO-D-domo-kun-20858807-500-306.png" alt="Responsive image">
			</div>
		</div>
 	</div>
 	<div class="row">
 		<legend>This is where your life defining post go!</legend>
 		<div class="col-md-10 .col-md-offset-3">
	    <% if(user != null){ %>
			<form action="/ofysign" method="post">
				<label for="textArea" class="col-lg-2 control-label">Title</label>
				<div col-lg-10>
					<textarea name = "title" rows = "1" cols = "100"></textarea>
				</div>
				<p></p>
				<label for="textArea" class="col-lg-2 control-label">Content</label>
		   		<div col-lg-10>
		   		<textarea name="content" rows="3" cols="100"></textarea>
		   		</div> 
		   		<br>
					<div class="col-lg-10 col-lg-offset-2"><input class="btn btn-default" type="submit" value="Create a new post" /></div>
		      		<input type="hidden" name="guestbookName" value="${fn:escapeXml(guestbookName)}"/>
		   	</form>
		   	<br><br>
		   	<form action = "ofyguestbook2.jsp" method = "link">
		   		
		   		<div class="col-lg-10 col-lg-offset-2"><input class="btn btn-primary" type = "submit" value = "Cancel"></div>
		   		
		   	</form>
		 <% } %>
		   	
	   	<% if(user == null){ %>
 			<p>Please <a href = "ofyguestbook2.jsp">log in</a> to create a post.</p>
 		<% } %>	
 		</div>
 	</div>	    	
</div>
</body>   
</html>