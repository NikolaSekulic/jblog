<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>

<head>
	<title>Blogovi</title>
	<meta charset="utf-8">
	
	<style type="text/css">
		.error {
			color: #FF0000;
		}
		
		
		.left {
			border: 0;
			text-align: right;
			vertical-align: text-top;
			font-style: italic;
		}
		
		.leftComment {
			border: 0;
			text-align: right;
			vertical-align: text-top;
			font-style: italic;
			color: red;
		}
		
		.right {
			border: 1;
			text-align: justify;
			padding-left: 1cm;
		}
		
		.rightComment {
			border: 0;
			text-align: justify;
			padding-left: 1cm;
		}
	</style>
	
</head>

<body>
	<jsp:include page="userInfo.jsp"></jsp:include>	
	
	<h1>Blogovi od korisnika ${nickname}</h1>
	
	
	<table>
	
	<c:forEach var="entry" items="${entries}">
	
		<tr>
			<td class="left">
				<a href="/aplikacija5/servleti/author/${nickname}/${entry.id}">Pogledaj</a><br>
				
				<c:if test="${nickname eq sessionScope['current.user.nick']}">
					<form method="post" action="/aplikacija5/servleti/author/${nickname}/edit">
						<input type="hidden" name="eid" value="${entry.id}">
						<input type="hidden" name="nickname" value="${nickname}">
						<input type="submit" value="Uredi" name="metoda">
					</form>
				</c:if>
			</td>
			<td class="right"><p style="white-space: pre-wrap;color: blue; font-size: 200%">${entry.title}</p></td>
			
		</tr>
		<tr>
			<td class="left"></td>
			<td class="right"><p style="white-space: pre-wrap;">${entry.text}</p></td>
			
		</tr>
		
		<tr>
			<td class="leftComment">Komentari: </td>
			<td class="rightComment" >
			
				<hr>
				<c:if test="${empty entry.comments}">Nema komentara!</c:if>
				
				<ul>
					<c:forEach var="comment" items="${entry.comments}">
						<li>
							<c:if test="${not empty comment.usersEMail}">
    							<p><a href="mailto:${comment.usersEMail}">${comment.usersEMail}</a></p>
							</c:if>
							
							<p style="white-space: pre-wrap;">${comment.message}</p>						
							<hr>
						</li>
					</c:forEach>
				</ul>
				
			</td>
		</tr>
		
		
		<tr>
			<td></td>
			
			<td>
				<form action="/aplikacija5/servleti/comment/new" method="post">
					<input type="hidden" name="eid" value="${entry.id}">
					<input type="hidden" name="user" value="${sessionScope['user.current.nick']}">
					<input type="hidden" name="nick" value="${nickname}">
					<input type="hidden" name="path" value="${requestScope['javax.servlet.forward.servlet_path']}">
					<input type="hidden" name="info" value="${requestScope['javax.servlet.forward.path_info']}">
					<textarea name="message" cols="20" rows="5" maxlength="4096"></textarea><br>
					<input type="submit" name="metoda" value="Dodaj komentar">
				</form>
			</td>
		</tr>
		
		
		<br>
			
	</c:forEach>
	
	</table>
</body>