<%--
  Created by IntelliJ IDEA.
  User: 老头
  Date: 2024/10/14
  Time: 19:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>个人信息</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <style>
    body {
      background-color: #f8f9fa;
    }
    h1 {
      margin-top: 20px;
      margin-bottom: 20px;
      color: #333;
    }
    .user-info {
      background-color: #ffffff;
      border-radius: 0.5rem;
      padding: 20px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    .btn-primary {
      background-color: #007bff;
      border: none;
    }
    .btn-primary:hover {
      background-color: #0056b3;
    }
  </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="${pageContext.request.contextPath}/">首页</a>
  <div class="collapse navbar-collapse">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/voting">投票管理</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="${pageContext.request.contextPath}/userInfo">用户信息</a>
      </li>
      <li class="nav-item">
        <a class="nav-link active" href="${pageContext.request.contextPath}/personalInfo">个人信息</a>
      </li>
    </ul>
  </div>
</nav>

<div class="container mt-5">
  <div class="user-info">
    <h1>个人信息</h1>
    <c:if test="${not empty requestScope.user}">
      <p><strong>姓名:</strong> ${requestScope.user.lastName}</p>
      <p><strong>Email:</strong> ${requestScope.user.email}</p>
      <p><strong>性别:</strong>
        <c:if test="${requestScope.user.gender == 1}">男
        </c:if>
        <c:if test="${requestScope.user.gender == 0}">女
        </c:if>
      </p>
      <p><strong>部门:</strong> ${requestScope.user.department.departmentName}</p>
      <a href="${pageContext.request.contextPath}/personalInfo" class="btn btn-warning">编辑信息</a>
    </c:if>
    <c:if test="${empty requestScope.user}">
      <p>没有找到用户信息。</p>
    </c:if>
  </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>