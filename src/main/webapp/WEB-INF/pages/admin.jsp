<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true"%>
<html>
<head>
<title>User Administrator</title>
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
}
.admin-header {
	font-size: 22px;
	font-weight: bold;
	margin-bottom:10px;
}
.admin-bar {
	font-size: 13px;
	font-weight: bold;
	margin-bottom:20px;
}
.user-table {
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
	margin-bottom: 20px;
}
.user-table th {
	font-weight:bold;
	padding: 2px 5px;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}

.user-table td {
	padding: 2px 5px;
	-webkit-border-radius: 2px;
	-moz-border-radius: 2px;
	border: 1px solid #000;
}
</style>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
</head>
<body>
	<div class="admin-header">User Administrator</div>

	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<div class="admin-bar">
			Welcome : ${pageContext.request.userPrincipal.name} | <a href="javascript:formSubmit()"> Logout</a>
		</div>
	</c:if>

 	<table class="user-table">
 		<thead><tr><th>User Name</th><th>First Name</th><th>Last Name</th><th>Actions</th></tr></thead>
	 	<c:forEach var="user" items="${users}">
	 		<tr>
	 			<td><c:out value="${user.username}"/></td>
	 			<td><c:out value="${user.firstName}"/></td>
	 			<td><c:out value="${user.lastName}"/></td>
	 			<td><a href="javascript:deleteUser(${user.id})"> Delete</a> | <a href="javascript:resetPassword(${user.id})"> Reset Password</a></td>
	 		</tr>
	 	</c:forEach>
 	</table>
 	
 	<div>
 		<a href="admin/create"> Create new user</a>
 	</div>
 
	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
	<form action="admin/user/delete" method="post" id="deleteForm">
		<input type="hidden" name="userid" value="0" />
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
	<form action="admin/user/reset" method="post" id="resetPasswordForm">
		<input type="hidden" name="userid" value="0" />
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
	<script>
		function formSubmit() {
			$('#logoutForm').submit();
		}
		function deleteUser(userId) {
			$('#deleteForm input').first().attr("value", userId);
			$('#deleteForm').submit();
		}
		function resetPassword(userId) {
			$('#resetPasswordForm input').first().attr("value", userId);
			$('#resetPasswordForm').submit();
		}
	</script>
</body>
</html>