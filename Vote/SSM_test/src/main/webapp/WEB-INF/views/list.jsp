<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2024/7/25
  Time: 22:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: GH
  Date: 2021/2/12
  Time: 2:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>所有员工</title>
</head>
<body>
<div class="col-md-8 column">
    <form class="form-inline" action="/" method="post" style="float: right">
        <!--搜索框-->
        <input type="text" name="queryBookName" class="form-control" placeholder="请输入名称">
        <input type="submit" value="查询" class="btn btn-primary">
    </form>
</div>

<center>
    <c:if test="${empty requestScope.emps}">
        <h1>没有任何员工</h1>
    </c:if>
    <c:if test="${not empty requestScope.emps}">
        <h1>员工信息</h1>
        <table border="1" cellpadding="10" cellspacing="0">
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
                        <c:if test="${emp.gender == 1}">
                            男
                        </c:if>
                        <c:if test="${emp.gender == 0}">
                            女
                        </c:if>
                    </td>
                    <td>${emp.department.departmentName}</td>
                    <td><a href="${pageContext.request.contextPath}/toup?empId=${emp.id}">Edit</a> </td>
                    <td><a href="${pageContext.request.contextPath}/del?empId=${emp.id}">删除</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <br>
    <br>
    <div class="row">
        <div class="col-md-4 column">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/toAdd">添加</a>
        </div>
    </div>
</center>
</body>
</html>

