<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>

<head>
	<title>Blog registracija</title>
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
	
	<h1>Registracija</h1>
	
	
	<form action="/aplikacija5/servleti/register" method="post" accept-charset="UTF-8">
	

	
	<table border="0">
		
		<tr>
			<td class="left">Ime: </td>
			<td class="rigth"><input type="text" name="firstname" size="42" value="${parameters.get('firstname')}"/></td>	
		</tr>
		<c:if test="${not empty errors.get('firstname')}">
			<tr>
				<td></td>
				<td class="error">${errors.get('firstname')}</td>
			</tr>
		</c:if>

		
		<tr>
			<td class="left">Prezime: </td>
			<td class="rigth"><input type="text" name="lastname"size="42" value="${parameters.get('lastname')}"/></td>	
		</tr>
		<c:if test="${not empty errors.get('lastname')}">
			<tr>
				<td></td>
				<td class="error">${errors.get('lastname')}</td>
			</tr>
		</c:if>
		
		<tr>
			<td class="left">E-mail: </td>
			<td class="rigth"><input type="text" name="email" size="42" value="${parameters.get('email')}"/></td>	
		</tr>
		<c:if test="${not empty errors.get('email')}">
			<tr>
				<td></td>
				<td class="error">${errors.get('email')}</td>
			</tr>
		</c:if>
		
		<tr>
			<td class="left">Korisničko ime: </td>
			<td class="rigth"><input type="text" name="username" size="42" value="${parameters.get('username')}"/></td>	
		</tr>
		<c:if test="${not empty errors.get('username')}">
			<tr>
				<td></td>
				<td class="error">${errors.get('username')}</td>
			</tr>
		</c:if>
		
		<tr>
			<td class="left">Lozinka: </td>
			<td class="rigth"><input type="password" autocomplete="off" name="password" size="42" value="${parameters.get('password')}"/></td>	
		</tr>
		<c:if test="${not empty errors.get('password')}">
			<tr>
				<td></td>
				<td class="error">${errors.get('password')}</td>
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