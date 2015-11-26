<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>

<head>
	<title>Blog</title>
	<meta charset="utf-8">
	
</head>


<body>

	
	<c:choose>
		<c:when test="${empty sessionScope.get('current.user.id')}">
			<div>
				<jsp:include page="loginForm.jsp"></jsp:include>
			</div>
			<a href="/aplikacija5/servleti/register">Registracija</a>
		</c:when>
		<c:otherwise>
			<h2>Korisnik:</h2>
			<jsp:include page="userInfo.jsp"></jsp:include>
				
			
		</c:otherwise>
	</c:choose>
	
	<jsp:include page="authors.jsp"></jsp:include>
	
	
</body>

</html>