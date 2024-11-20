<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>所有员工</title>
    <!--<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.min.css">
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
        .chat-icon {
            position: absolute;
            top: 10px;
            right: 10px;
            cursor: pointer;
        }
    </style>
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
                <a class="nav-link" href="${pageContext.request.contextPath}/vote/VoteInfo">投票项目</a>
            </li>
            <li class="nav-item">
                <a class="nav-link active" href="${pageContext.request.contextPath}/user/getEmployee">用户信息</a>
            </li>
        </ul>
    </div>
    <!-- 添加聊天图标按钮 -->
    <div class="chat-icon" data-toggle="modal" data-target="#chatModal">
        <i class="fas fa-comment-dots" style="font-size: 24px; color: #007bff;"></i>
    </div>
</nav>

<div class="container mt-5">
    <div class="row justify-content-end">
        <form class="form-inline" action="${pageContext.request.contextPath}/user/getOne" method="get">
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
                            <c:choose>
                                <c:when test="${emp.gender == 1}">男</c:when>
                                <c:when test="${emp.gender == 0}">女</c:when>
                                <c:otherwise>未知</c:otherwise>
                            </c:choose>
                        </td>
                        <td>${emp.department.departmentName}</td>
                        <td>
                            <button class="btn btn-warning" onclick="editEmployee(${emp.id})">Edit</button>
                        </td>
                        <td>
                            <button class="btn btn-danger" onclick="deleteEmployee(${emp.id})">删除</button>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <br>
        <div class="row">
            <div class="col-md-4">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/user/toAdd">添加</a>
            </div>
        </div>
    </center>
</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="chatModal" tabindex="-1" role="dialog" aria-labelledby="chatModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="chatModalLabel">用户聊天</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div id="chatMessages" style="max-height: 300px; overflow-y: auto;">
                    <!-- 聊天消息会在这里显示 -->
                </div>
                <form id="chatForm">
                    <div class="input-group">
                        <input type="text" id="chatInput" class="form-control" placeholder="输入消息..." required>
                        <div class="input-group-append">
                            <button type="submit" class="btn btn-primary">发送</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.min.js"></script>
<script>

    function editEmployee(empId) {
        window.location.href = "${pageContext.request.contextPath}/user/toup?id=" + empId;
    }

    function deleteEmployee(empId) {
        if (confirm('确定要删除此员工吗？')) {
            $.ajax({
                url: "${pageContext.request.contextPath}/user/del",
                method: "GET", // 注意这里使用 GET 方法，因为你的后端方法是 @RequestMapping("/user/del") 并且没有指定 POST
                data: {id: empId},
                success: function (response) {
                    console.log('删除成功:', response);
                    window.location.href = "${pageContext.request.contextPath}/user/getEmployee"; // 手动重定向
                },
                error: function (xhr, status, error) {
                    console.error('删除失败:', error);
                    alert('删除员工时出错: ' + error.message);
                }
            });
        }
    }

    let socket;

    function connectWebSocket() {
        socket = new WebSocket('ws://localhost:1024/channel'); // 替换为你的 WebSocket 服务器地址

        socket.onopen = function () {
            console.log('Connected to WebSocket server');
        };

        socket.onmessage = function (event) {
            console.log('Received message: ' + event.data);
            $('#chatMessages').append('<div class="chat-message"><strong>服务器：</strong>' + event.data + '</div>');
            $('#chatMessages').scrollTop($('#chatMessages')[0].scrollHeight);
        };

        socket.onclose = function (event) {
            console.log('Disconnected from WebSocket server');
        };

        socket.onerror = function (error) {
            console.error('WebSocket error: ' + error);
        };
    }

    function sendChatMessage() {
        const message = $('#chatInput').val();
        if (message.trim() !== '') {
            // 将消息添加到聊天窗口
            $('#chatMessages').append('<div class="chat-message"><strong>你：</strong>' + message + '</div>');

            // 将消息保存到 LocalStorage
            let chatHistory = JSON.parse(localStorage.getItem('chatHistory')) || [];
            chatHistory.push(message);
            localStorage.setItem('chatHistory', JSON.stringify(chatHistory));

            // 清空输入框
            $('#chatInput').val('');
            // 滚动到底部
            $('#chatMessages').scrollTop($('#chatMessages')[0].scrollHeight);

            // 将消息发送到 WebSocket 服务器
            if (socket && socket.readyState === WebSocket.OPEN) {
                socket.send(message);
            } else {
                console.error('WebSocket connection is not open');
            }
        }
        return false; // 阻止表单默认提交，防止模态框关闭
    }

    $(document).ready(function () {
        // 初始化模态框
        $('#chatModal').modal({
            show: false
        });

        // 绑定表单提交事件
        $('#chatForm').on('submit', function (event) {
            event.preventDefault(); // 阻止表单的默认提交行为
            sendChatMessage();
        });

        // 从 LocalStorage 恢复聊天记录
        let chatHistory = JSON.parse(localStorage.getItem('chatHistory')) || [];
        chatHistory.forEach(message => {
            $('#chatMessages').append('<div class="chat-message"><strong>你：</strong>' + message + '</div>');
        });

        // 连接到 WebSocket 服务器
        connectWebSocket();

        // 滚动到底部
        $('#chatMessages').scrollTop($('#chatMessages')[0].scrollHeight);
    });


    // 在页面加载时执行的代码
    document.addEventListener('DOMContentLoaded', function () {
        // 设置 axios 默认配置，允许携带凭据
        axios.defaults.withCredentials = true;

        const token = localStorage.getItem('loginToken'); // 从 localStorage 获取 token
        console.log(" ----------------------------------- 执行 ---------------------------------------");
        console.log(token)
        // 使用 axios 拦截器设置请求头
        axios.interceptors.request.use(function (config) {
            if (token) {
                config.headers['Authorization'] = token; // 将 token 设置到请求头中
                // config.headers['Accept'] = 'application/json'; // 可选，设置期望返回的数据类型
            } else {
                console.log('Token 不存在，无法发送到服务器');
                return Promise.reject(new Error('Token 不存在'));
            }
            return config; // 继续发送请求
        }, function (error) {
            return Promise.reject(error);
        });

        // 发送请求到后端
        axios.get('http://localhost:8080/user/getEmployee')
            .then(response => {
                console.log('请求成功:', response.data);
                document.body.innerHTML += `<pre>${JSON.stringify(response.data, null, 2)}</pre>`;
            })
            .catch(error => {
                console.error('请求出错:', error);
                if (error.response && error.response.status === 401) {
                    // 处理未授权的情况
                    // window.location.href = 'login.html';
                } else if (error.message === 'Token 不存在') {
                    // 处理 token 不存在的情况
                    // window.location.href = 'login.html';
                }
            });
    });

</script>
</body>
</html>