<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Diary List</title>
</head>
<body>
<h1>Diary List</h1>
<ul>
    <c:forEach items="${diary}" var="diaryId">
        <li><a href="/diary/${diaryId}">Diary ${diaryId}</a></li>
    </c:forEach>
</ul>
</body>
</html>
