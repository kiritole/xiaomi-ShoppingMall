<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/test?method=upload" method="post" enctype="multipart/form-data">
        <input type="file" name="pic"/>
        <input type="text" name="userName"/>
        <button type="submit">上传</button>
    </form>
</body>
</html>
