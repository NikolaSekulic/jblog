<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>

	<h2>Blogeri:</h2>
	
	
	<c:choose>
		<c:when test="${empty authors}">
			<p>Trenutno nije registriran niti jedan bloger!</p>
		</c:when>
		<c:otherwise>
			<ol>
				<c:forEach var="author" items="${authors}">
					<li>
						<a href="/aplikacija5/servleti/author/${author.nick}">${author.nick}</a>
						
					</li>
				</c:forEach>
			</ol>
			
		</c:otherwise>
	</c:choose>



</div>