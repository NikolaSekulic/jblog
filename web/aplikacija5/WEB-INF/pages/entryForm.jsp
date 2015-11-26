<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
	<title>Radionica</title>
	<meta charset="utf-8">
	
	<style type="text/css">
		.error {
			color: #FF0000;
		}
		
		.left {
			border: 0;
			text-align: right;
			font-style: italic;
		}
		
		.right {
			border: 0;
			text-align: left;
		}
	</style>
	
	
</head>

<body>

	<jsp:include page="userInfo.jsp"></jsp:include>

	<h1>
		<c:choose>
				<c:when test="${empty id}">Novi zapis</c:when>
				<c:otherwise>Uređivanje zapisa</c:otherwise>
		</c:choose>
	</h1>
		
	<form action="/aplikacija5/servleti/save" method="post" accept-charset="UTF-8">
	
	<input type="hidden" name="id" value="${id}"/>
	<input type="hidden" name="nickHidden" value="${nickname}">
	
	<table border="0">
		<tr>
			<td class="left">Naslov: </td>
			<td class="rigth"><input type="text" name="title" size="42" maxlength="200" value="${title}"/></td>	
		</tr>
		<c:if test="${not empty errors.get('title')}">
			<tr>
				<td></td>
				<td class="error">${errors.get('title')}</td>
			</tr>
		</c:if>
		
		
		
		<tr>
			<td class="left">Zapis: </td>
			<td class="rigth">
				<textarea name="entry" cols="80" rows="10" maxlength="4096">${entry}</textarea>
			</td>	
		</tr>
		<c:if test="${not empty errors.get('entry')}">
			<tr>
				<td></td>
				<td class="error">${errors.get('entry')}</td>
			</tr>
		</c:if>
		
		<tr>
			<td></td>
			<td align="left">
				<input type="submit" name="metoda" value="Pohrani">
				<input type="submit" name="metoda" value="Odustani">
			</td>
		</tr>
	</table>
	
		
	
	
	</form>



</body>