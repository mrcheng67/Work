<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <!-- 引入 Bootstrap -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>新增人物</small>
                </h1>
            </div>
        </div>
    </div>
    <form action="${pageContext.request.contextPath}/add" method="post">
        <div class="form-group">
            <label>人物名称:</label>
            <input type="text" name="lastName" class="form-control" required>
        </div>
        <div class="form-group">
            <label>邮箱:</label>
            <input type="text" name="email" class="form-control" required>
        </div>
        <div class="form-group">
            <label>性别:</label>
            <input type="text"  name="gender" class="form-control" required>
        </div>
        <div class="form-group">
            <label>部门:</label>
            <select name="department.id" class="form-control" required>
                <c:forEach items="${departments}" var="dept">
                    <option value="${dept.id}">${dept.departmentName}</option>
                    <option value="1">教学部</option>
                    <option value="2">教务部</option>
                    <option value="3">运营部</option>
                    <option value="4">咨询部</option>
                    <option value="5">就业部</option>
                    <option value="6">财务部</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <input type="submit" class="form-control" value="添加">
        </div>
    </form>
</div>
</body>
</html>
