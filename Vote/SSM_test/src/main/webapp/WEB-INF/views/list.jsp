<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2024/7/25
  Time: 22:11
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>所有员工</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
        }
        h1 {
            margin-bottom: 20px;
            font-size: 24px;
            color: #333;
        }
        table {
            background-color: #ffffff;
            border-radius: 0.5rem;
            overflow: hidden;
        }
        th, td {
            text-align: center;
        }
        th {
            background-color: #007bff;
            color: #ffffff;
        }
        .btn-primary {
            background-color: #007bff;
            border: none;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .form-inline {
            margin-bottom: 20px;
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
                <a class="nav-link active" href="${pageContext.request.contextPath}/getEmployee">用户信息</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/personalInfo">个人信息</a>
            </li>
        </ul>
    </div>
</nav>
<div>
    todo zijiu.wa test
    ${requestScope.emps}
</div>
<div class="container mt-5">
    <div class="row justify-content-end">
        <!-- 修改表单的 action 和添加员工 ID 输入字段 -->
        <form class="form-inline" action="${pageContext.request.contextPath}/getOne" method="get">
            <input type="text" name="id" class="form-control mr-2" placeholder="请输入员工ID">
            <input type="submit" value="查询" class="btn btn-primary">
        </form>
    </div>

    <center>
        <c:if test="${empty requestScope.emps}">
            <h1>没有任何员工</h1>
        </c:if>
        <c:if test="${not empty requestScope.emps}">
            <h1>员工信息</h1>
            <table class="table table-bordered">
                <tr>
                    <th>Id</th>
                    <th>LastName</th>
                    <th>Email</th>
                    <th>Gender</th>
                    <th>Department</th>
                    <th colspan="2">操作</th>
                </tr>
                <c:forEach items="${requestScope.emps}" var="emp">
                    <tr>
                        <td>${emp.id}</td>
                        <td>${emp.lastName}</td>
                        <td>${emp.email}</td>
                        <td>
                            <c:if test="${emp.gender == 1}">男</c:if>
                            <c:if test="${emp.gender == 0}">女</c:if>
                        </td>
                        <td>${emp.department.departmentName}</td>
                        <td><a href="${pageContext.request.contextPath}/toup?empId=${emp.id}" class="btn btn-warning">Edit</a></td>
                        <td><a href="${pageContext.request.contextPath}/del?empId=${emp.id}" class="btn btn-danger">删除</a></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <br>
        <div class="row">
            <div class="col-md-4">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/toAdd">添加</a>
            </div>
        </div>
    </center>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>