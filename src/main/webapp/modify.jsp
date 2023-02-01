<%@page import="com.hyungseo.exe.MemberDao"%>
<%@page import="com.hyungseo.exe.MemberDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정</title>
</head>
<body>

	<h2>회원 정보 수정</h2>
	<hr>
	<form action="modifyOk.do">
		아이디 : <input type="text" name="id" value="${memberDto.id }" readonly="readonly"><br><br>
		비밀번호 : <input type="password" name="pw" value="${memberDto.password }"><br><br>
		이름 : <input type="text" name="name" value="${memberDto.name }"><br><br>
		이메일 : <input type="text" name="email" value="${memberDto.email }"><br><br>
		가입일 : <input type="text" name="jointime" value="${memberDto.jointime }" readonly="readonly"><br><br>
		<input type="submit" value="정보수정">
		<input type="button" value="취소" onclick="javacript:window.location='main.do'">
	</form>
	
</body>
</html>