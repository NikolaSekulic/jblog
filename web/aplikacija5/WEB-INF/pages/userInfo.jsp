<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<div>
	
	<c:choose>
		<c:when test="${empty sessionScope.get('current.user.id')}">
			<div>
				<p>Anonimni korisnik</p>
				<a href="/aplikacija5/servleti/register">Registracija</a>
				<a href="/aplikacija5">Početna stranica</a>
			</div>
		</c:when>
		<c:otherwise>
			<p>
				${sessionScope.get('current.user.fn')}
				${sessionScope.get('current.user.ln')}
				(${sessionScope.get('current.user.nick')})
			</p>
			<a href="/aplikacija5/servleti/logout">Odjava</a>
			<br>
			<a href="/aplikacija5/servleti/author/${sessionScope.get('current.user.nick')}">Moji zapisi</a>
			<br>
			<a href="/aplikacija5/servleti/author/${sessionScope.get('current.user.nick')}/new">Novi zapis</a>
			<br>
			<a href="/aplikacija5">Početna stranica</a>
			
			
			
		</c:otherwise>
	</c:choose>
</div>