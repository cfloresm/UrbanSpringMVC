<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Send Notification</title>
</head>
<body>
	<form:form  action="pushnotification" name="messageForm" modelAttribute="messageForm"  method='post' accept-charset='UTF-8' >
		<form:label path="email">User:</form:label>
		<form:select path="email" items="${users}" multiple="true" />
		<br/>
		<form:textarea path="message" rows="5" cols="30" /><br/>
		<input type="submit" value="Enviar notificaci&oacute;n">
	</form:form>
	<a>${confirm}</a>
</body>
</html>