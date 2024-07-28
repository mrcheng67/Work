<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
                    <small>修改人物</small>
                </h1>
            </div>
        </div>
    </div>

    <script>
        // 当页面加载时执行
        window.onload = function() {
            // 使用JavaScript和JSP EL表达式来读取URL查询参数
            var empId = "${param.empId}"; // JSP EL表达式在服务器端解析，所以这里会替换为实际的empId值

            // 查找表单中的id字段并设置其值
            var idInput = document.querySelector('input[name="id"]');
            if (idInput) {
                idInput.value = empId; // 将读取到的empId值设置到id字段中
            }
        };
    </script>
    <form action="${pageContext.request.contextPath}/update" method="post">
        <div class="form-group">
            <label>人物id:</label>
            <input type="text" name="id" class="form-control" required>
        </div>
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
