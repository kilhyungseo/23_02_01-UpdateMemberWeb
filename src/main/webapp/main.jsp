<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인 페이지</title>
</head>
<body>
	<h2>메인페이지입니다.</h2>
	<hr>
	<%
		String sessionId = (String)session.getAttribute("memberId");
		// String ValidSession = (String)session.getAttribute("ValidSession");
		if(sessionId == null){
			sessionId = "guest";
		}
		
	%>
	<%= sessionId %> 님 로그인 하셨습니다. 반갑습니다 <br><br>
	<%
		if(session.getAttribute("ValidSession") != null){
			//response.sendRedirect("login.jsp");
			
	%>  
	<form action="logout.do">
		<input type="submit" value="로그아웃">
		<input type="button" value="정보수정" onclick="javacript:window.location='modify.do'">
		<input type="button" value="회원탈퇴" onclick="javacript:window.location='delOk.do'">
	</form>
	
	<%
		}else{
	%>		
		로그인 하시면 정보를 더 제공받을수 있습니다.<br>
		<a href="login.do">로그인 페이지로 이동</a>
	<%
		}
	%>
		
</body>
</html>