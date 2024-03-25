<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Create Diary</title>
</head>
<body>
<h1>Create Diary</h1>
<form action="/diary/create" method="post">
    <label for="title">Title:</label><br>
    <input type="text" id="title" name="title"><br>
    <label for="content">Content:</label><br>
    <textarea id="content" name="content"></textarea><br>
    <label for="emotion">Emotion:</label><br>
    <input type="text" id="emotion" name="emotion"><br>
    <input type="submit" value="Create">
</form>
</body>
</html>
