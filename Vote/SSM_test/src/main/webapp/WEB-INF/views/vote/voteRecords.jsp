<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Vote Records</title>
    <!--<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">-->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
            padding-top: 60px; /* 调整为导航栏高度加上一些额外空间 */
        }
        h1 {
            margin-bottom: 20px;
            font-size: 24px;
            color: #333;
        }
        .table {
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
            cursor: pointer;
        }
        .navbar {
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 1000; /* 确保导航栏在其他内容之上 */
        }
    </style>
</head>
<body>
<!-- 导航栏 -->
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
        <!-- 添加聊天图标按钮到导航栏右侧 -->
        <div class="chat-icon" data-toggle="modal" data-target="#chatModal">
            <i class="fas fa-comment-dots" style="font-size: 24px; color: #007bff;"></i>
        </div>
    </div>
</nav>

<div class="container">
    <div class="row justify-content-end">
        <form class="form-inline" action="${pageContext.request.contextPath}/vote/voteInfo" method="post">
            <input type="text" name="id" class="form-control mr-2" placeholder="请输入投票ID">
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
    </div>

    <c:if test="${empty requestScope.Records}">
        <h1 class="text-center">没有任何投票记录</h1>
    </c:if>
    <c:if test="${not empty requestScope.Records}">
        <h1 class="text-center">投票记录</h1>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Record ID</th>
                <th>Voter title</th>
                <th>description</th>
                <th>createorName</th>
                <th>CreateTime</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="record" items="${requestScope.Records}">
                <tr>
                    <td>${record.voteId}</td>
                    <td>${record.title}</td>
                    <td>${record.description}</td>
                    <td>${record.createName}</td>
                    <td>${record.createdAt}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <br>
    <div class="row justify-content-center">
        <div class="col-md-4 text-center">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/vote/createvote">添加</a>
        </div>
    </div>
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
    let socket;

    function connectWebSocket() {
        socket = new WebSocket('ws://localhost:1024/channel'); // 替换为你的 WebSocket 服务器地址

        socket.onopen = function() {
            console.log('Connected to WebSocket server');
        };

        socket.onmessage = function(event) {
            console.log('Received message: ' + event.data);
            $('#chatMessages').append('<div class="chat-message"><strong>服务器：</strong>' + event.data + '</div>');
            $('#chatMessages').scrollTop($('#chatMessages')[0].scrollHeight);
        };

        socket.onclose = function(event) {
            console.log('Disconnected from WebSocket server');
        };

        socket.onerror = function(error) {
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

    $(document).ready(function() {
        // 初始化模态框
        $('#chatModal').modal({
            show: false
        });

        // 绑定表单提交事件
        $('#chatForm').on('submit', function(event) {
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
</script>
</body>
</html>