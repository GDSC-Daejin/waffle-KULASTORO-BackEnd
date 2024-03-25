<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Update Diary</title>
</head>
<body>
<h1>Update Diary</h1>
<form action="/diary/update" method="post">
    <input type="hidden" name="id" value="${diary.id}"> <!-- 일기의 ID를 전달 -->
    <label for="title">Title:</label><br>
    <input type="text" id="title" name="title" value="${diary.title}"><br>
    <label for="content">Content:</label><br>
    <textarea id="content" name="content">${diary.context}</textarea><br>
    <label for="emotion">Emotion:</label><br>
    <input type="text" id="emotion" name="emotion" value="${diary.emotion}"><br>
    <input type="submit" value="Update">
</form>
</body>
</html>
