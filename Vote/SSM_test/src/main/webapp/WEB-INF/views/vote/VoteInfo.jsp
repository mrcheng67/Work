<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>投票页面</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
            padding-top: 50px;
        }
        h1 {
            margin-bottom: 20px;
            font-size: 24px;
            color: #333;
        }
        .card {
            margin-bottom: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .card-header {
            background-color: #007bff;
            color: white;
            font-weight: bold;
        }
        .form-check-label {
            font-size: 16px;
        }
        .btn-primary {
            background-color: #007bff;
            border: none;
            transition: all 0.3s ease;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .no-vote {
            text-align: center;
            margin-top: 50px;
            color: #777;
        }
        .VoteRecord {
            max-height: 400px;
            overflow-y: auto;
        }
        .VoteRecord-item {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            display: flex;
            align-items: center;
        }
        .VoteRecord-item:last-child {
            border-bottom: none;
        }
        .VoteRecord-item span {
            margin-right: 10px;
        }
        /* 为 .navbar-collapse 添加背景颜色 */
        .navbar-collapse {
            background-color: #e9ecef; /* 选择一个合适的背景颜色 */
            padding: 10px; /* 可选：添加内边距以增加空间感 */
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        function submitForm() {
            const form = document.getElementById('voteForm');
            const formData = new FormData(form);

            const token = localStorage.getItem('loginToken');

            fetch('${pageContext.request.contextPath}/vote/submit', {
                method: 'POST',
                headers: {
                    'Authorization': 'Bearer ' + token,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    voteId: formData.get('voteId'),
                    optionId: formData.get('optionId'),
                })
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert('投票成功！');
                        window.location.href = '${pageContext.request.contextPath}/vote/vote';
                    } else {
                        alert('投票失败:令牌错误或权限不足 —— 是否未登录？' );
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('网络错误，请稍后再试。');
                });

            return false; // 阻止表单默认提交
        }
    </script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="${pageContext.request.contextPath}/">首页</a>
    <div class="collapse navbar-collapse">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/vote/vote">投票管理</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/vote/voteInfo">投票项目</a>
            </li>
            <li class="nav-item">
                <a class="nav-link active" href="${pageContext.request.contextPath}/user/getEmployee">用户信息</a>
            </li>
        </ul>
    </div>
</nav>
<div class="container">
    <h1 class="text-center">投票页面</h1>

    <c:if test="${empty requestScope.Votes}">
        <div class="no-vote">
            <h2>没有找到投票内容</h2>
            <p>请稍后再试或联系管理员。</p>
        </div>
    </c:if>
    <c:if test="${not empty requestScope.Votes}">
        <div class="row">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">
                        <h3>${requestScope.Votes.title}</h3>
                    </div>
                    <div class="card-body">
                        <p>${requestScope.Votes.description}</p>
                        <form id="voteForm" onsubmit="return submitForm()">
                            <input type="hidden" name="voteId" value="${requestScope.Votes.voteId}">
                            <c:forEach var="option" items="${requestScope.Votes.options}" varStatus="status">
                                <div class="form-check mb-3">
                                    <input class="form-check-input" type="radio" name="optionId" id="option${status.index}" value="${option.optionId}">
                                    <label class="form-check-label" for="option${status.index}">
                                            ${option.optionText}
                                    </label>
                                </div>
                            </c:forEach>
                            <br>
                            <button type="submit" class="btn btn-primary">提交投票</button>
                        </form>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <div class="card-header">
                        <h3>排行榜</h3>
                    </div>
                    <div class="card-body VoteRecord">
                        <c:if test="${empty requestScope.VoteRecord}">
                            <p class="text-center">暂无投票记录</p>
                        </c:if>
                        <c:if test="${not empty requestScope.VoteRecord}">
                            <c:forEach var="record" items="${requestScope.VoteRecord}" varStatus="status">
                                <div class="VoteRecord-item">
                                    <span class="font-weight-bold">${status.index + 1}.</span>
                                    <span class="ml-2">${record.voterName}</span>
                                    <span class="ml-2">选项ID: ${record.optionId}</span>
                                    <span class="float-right">票数: ${record.voteCount}</span>
                                </div>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>