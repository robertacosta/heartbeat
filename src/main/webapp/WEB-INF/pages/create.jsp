<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
<title>Create User</title>
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
}

.error {
	padding: 5px 10px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #a94442;
	background-color: #f2dede;
	border-color: #ebccd1;
}
 
.msg {
	padding: 5px 10px;
	margin-bottom: 20px;
	border: 1px solid transparent;
	border-radius: 4px;
	color: #31708f;
	background-color: #d9edf7;
	border-color: #bce8f1;
}
 
#login-box {
	width: 300px;
	padding: 0px 15px;
	margin: 100px auto;
	background: #fff;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}
</style>
</head>
<body onload='document.createUserForm.firstname.focus();'>
	<div id="login-box">
 
		<h3>Create a User</h3>
 
 
		<form name='createUserForm'
		  action="<c:url value='/admin/user/add' />" method='POST'>
 
		<table>
			<tr>
				<td>First Name:</td>
				<td><input type='text' name='firstName'></td>
			</tr>
			<tr>
				<td>Last Name:</td>
				<td><input type='text' name='lastName'></td>
			</tr>
			<tr>
				<td>Username:</td>
				<td><input type='text' name='username'></td>
			</tr>
			<tr>
				<td>Email:</td>
				<td><input type='text' name='email'></td>
			</tr>
			<tr>
				<td>Job Title:</td>
				<td><input type='text' name='jobTitle'></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
				  value="submit" /></td>
			</tr>
		  </table>
 
		  <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
 
		</form>
	</div>
 
</body>
</html>