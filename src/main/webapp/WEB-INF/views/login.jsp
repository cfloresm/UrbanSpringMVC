<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Log In</title>
</head>
<body>
<div>
${errorMsg}
</div>
<div>
${infoMsg}
</div>
	<form:form  action="login" name="loginForm" modelAttribute="loginForm" method='post' accept-charset='UTF-8' >
		<form:input path="username" name="username" type="text" placeholder="Email"/> <br/>
		<form:input path="password" name="password" type="password" placeholder="Password"/><br/>
		<a href="userForm">Sign In</a>
		<button type="submit" value="Log In">Log In</button>
	</form:form>
</body>
</html>