<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 이 페이지가 에러가 발생했을 때 출력되는 페이지라는 설정 -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>에러 페이지</title>
</head>
<body>
		<%@ include file="../include/header.jsp"%>
		<h3>시스템에 장애가 발생했습니다.<br/>
		조치 중이나 잠시 후에 접속해 주시면 감사하겠습니다.</h3> 
		<%@ include file="../include/footer.jsp"%>
</body>
</html>