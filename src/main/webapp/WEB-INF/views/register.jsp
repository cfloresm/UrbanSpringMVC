<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register</title>
</head>
<body>
	<form:form  action="register" name="registerForm" modelAttribute="registerForm"  method='post' accept-charset='UTF-8' >
		<form:input path="userId" name="id usuario" type="text" placeholder="Email"/> <br/>
		<form:input path="appId" name="appid" type="text" placeholder="Appid"/><br/>
		<button  type="submit" value="Sign-In">Registrar </button> 
	</form:form>
</body>
</html>