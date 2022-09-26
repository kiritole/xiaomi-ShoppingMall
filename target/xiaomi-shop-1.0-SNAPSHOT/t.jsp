<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>测试多个请求指向同一个Servlet</title>
</head>
<body>
	<h1>测试多个请求指向同一个Servlet</h1>
	<a href="${pageContext.request.contextPath}/userController?method=login">登录</a>
	<hr>
	<a href="${pageContext.request.contextPath}/userController?method=logout">登出</a>
	<hr>
	<a href="${pageContext.request.contextPath}/userController?method=register">注册</a>
	<hr>
	<a href="${pageContext.request.contextPath}/userController?method=userInfo">获取用户信息</a>
	<hr>
	验证码是: <img src="${pageContext.request.contextPath}/userController?method=captcha" />
	<hr>
	<hr>
	<a href="${pageContext.request.contextPath}/userController?">特殊情况-没有传递methodName</a>
	<hr>
	<a href="${pageContext.request.contextPath}/userController?methodName=xxxx">特殊情况-传递值不存在</a>
</body>
</html>
