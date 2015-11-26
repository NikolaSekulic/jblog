<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<h2>Prijava</h2>
<form action="/aplikacija5/servleti/login" method="post" accept-charset="UTF-8">
	<table>
		<tr>
			<td class="left">Korisničko ime: </td>
			<td class="right"><input type="text" size="40" name="username" value="${username}"></td>
		</tr>
		<tr>
			<td class="left">Lozinka: </td>
			<td class="right"><input type="password" size="40" name="password"></td>
		</tr>
		<tr>
			<td class="left"></td>
			<td class="right"><input type="submit" name="prijava" value="Prijava"></td>
		</tr>
		<tr>
			<td></td>
			<td style="color: red;">${LoginError}</td>			
		</tr>
	</table>

</form>