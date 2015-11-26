<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>

<head>
	<title>Blog</title>
	<meta charset="utf-8">
	
</head>

<body>
	
	<jsp:include page="userInfo.jsp"></jsp:include>
	
	<h1>Pogreška</h1>
	<p>${message}</p>
	<a href="/aplikacija5/index.html"></a>	
</body>