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
                <div class="row">
                    <div class="col-md-4">
                        <div class="list-group" id="userList">
                            <!-- 在线用户列表会在这里显示 -->
                        </div>
                    </div>
                    <div class="col-md-8">
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
                </div>
                <hr>
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

            // 发送初始化消息，包含用户的 token 和其他信息
            const token = localStorage.getItem('loginToken') || ''; // 从 LocalStorage 获取 token
            console.log("token = " + token);
            const initMessage = JSON.stringify({token: token, to: "init", message: "init"});
            socket.send(initMessage);
        };

        socket.onmessage = function(event) {
            const token = localStorage.getItem('loginToken') || '';
            console.log("接收到了")
            const data = event.data;
            console.log(data);
            if (data.startsWith("onlineUsers:")) {
                console.log("处理初始化消息" + token);
                // 更新在线用户列表
                const result = data.substring("onlineUsers:".length).slice(1, -1).split(",");
                updateOnlineUsersList(result);
            } else {
                console.log("处理普通消息" + token);
                // 处理普通消息
                handleChatMessage(data);
            }
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
        const toUsername = $('#userList .active').text();// 获取选中的用户名
        if (message.trim() !== '' && toUsername) {
            // 将消息添加到聊天窗口
            $('#chatMessages').append('<div class="chat-message"><strong>你：</strong>' + message + '</div>');

            // 清空输入框
            $('#chatInput').val('');
            // 滚动到底部
            $('#chatMessages').scrollTop($('#chatMessages')[0].scrollHeight);
            // 将消息发送到 WebSocket 服务器
            if (socket && socket.readyState === WebSocket.OPEN) {
                const token = localStorage.getItem('loginToken') || '';
                const messageData = JSON.stringify({token: token, to: toUsername, message: message});
                socket.send(messageData);
            } else {
                console.error('WebSocket connection is not open');
            }
        }
        return false; // 阻止表单默认提交，防止模态框关闭
    }

    function updateOnlineUsersList(users) {
        const userListElement = $('#userList');
        userListElement.empty();
        users.forEach(user => {
            const listItem = $('<a href="#" class="list-group-item list-group-item-action">' + user + '</a>');
            listItem.click(function() {
                userListElement.find('.active').removeClass('active');
                $(this).addClass('active');
            });
            userListElement.append(listItem);
        });
    }

    function handleChatMessage(data) {
        const messageParts = data.split(": ");
        const token = messageParts[0].replace("From ", "");
        const messageContent = messageParts.slice(1).join(": ");
        console.log("处理普通消息,到了函数里面" + token);
        // 将消息添加到聊天窗口
        $('#chatMessages').append('<div class="chat-message"><strong>' + token + '：</strong>' + messageContent + '</div>');
        $('#chatMessages').scrollTop($('#chatMessages')[0].scrollHeight);
    }

    $(document).ready(function() {
        // 连接到 WebSocket 服务器
        connectWebSocket();

        // 绑定发送消息表单的提交事件
        $('#chatForm').submit(sendChatMessage);
    });
</script>
</body>
</html>