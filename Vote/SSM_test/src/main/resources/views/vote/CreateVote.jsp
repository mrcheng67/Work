<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>创建投票</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }

        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }

        form {
            background: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            max-width: 500px;
            margin: auto;
        }

        .input-group {
            margin-bottom: 15px;
        }

        .input-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #555;
        }

        .input-group input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            transition: border-color 0.3s, box-shadow 0.3s;
        }

        .input-group input:focus {
            border-color: #66afe9;
            box-shadow: 0 0 5px rgba(102, 175, 233, 0.5);
            outline: none;
        }

        .options {
            list-style-type: none;
            padding: 0;
        }

        .options li {
            display: flex;
            align-items: center;
            margin: 10px 0;
        }

        .option-input {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            width: 85%;
            transition: border-color 0.3s, box-shadow 0.3s;
        }

        .option-input:focus {
            border-color: #66afe9;
            box-shadow: 0 0 5px rgba(102, 175, 233, 0.5);
            outline: none;
        }

        .remove-btn {
            margin-left: 8px;
            padding: 8px 12px;
            border: none;
            border-radius: 5px;
            background-color: #d9534f;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .remove-btn:hover {
            background-color: #c9302c;
        }

        .btn {
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            background-color: #5cb85c;
            color: white;
            cursor: pointer;
            transition: background-color 0.3s;
            width: 100%;
        }

        .btn:hover {
            background-color: #4cae4c;
        }

        .add-option-btn {
            margin-top: 10px;
            background-color: #5bc0de;
            width: 100%;
        }

        .add-option-btn:hover {
            background-color: #31b0d5;
        }
    </style>

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

        function addOption() {
            const optionsList = document.getElementById('optionsList');
            const newListItem = document.createElement('li');
            newListItem.innerHTML = `
                <input type="text" class="option-input" placeholder="选项内容" required>
                <button type="button" class="remove-btn" onclick="removeOption(this)">删除选项</button>
            `;
            optionsList.appendChild(newListItem);
        }

        function removeOption(button) {
            const listItem = button.parentElement;
            listItem.remove();
        }

        document.getElementById('VoteForm').addEventListener('submit', function(event) {
            event.preventDefault(); // 阻止默认表单提交

            // 收集投票标题、描述和过期时间
            const voteTitle = document.getElementById('voteTitle').value;
            const voteDescription = document.getElementById('voteDescription').value;
            const expiryTime = document.getElementById('expiryTime').value;

            // 收集选项内容
            const options = Array.from(document.querySelectorAll('.option-input'))
                .map(input => input.value)
                .filter(value => value); // 过滤出有效值

            // 创建一个投票对象
            const voteData = {
                title: voteTitle,
                description: voteDescription,
                expiryTime: expiryTime,
                options: options
            };
            const token = localStorage.getItem('loginToken');

            // 发送数据到后端
            fetch('${pageContext.request.contextPath}/vote/createvote', {
                method: 'POST',
                headers: {
                    'Authorization': 'Bearer ' + token,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(voteData)
            })
                .then(response => response.json())
                .then(data => {
                    console.log('Success:', data);
                    // 可以在这里处理成功响应（例如，显示成功消息）
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
        });
    </script>
</head>
<body>

<h1>创建投票</h1>
<form id="VoteForm">
    <div class="input-group">
        <label for="voteTitle">投票标题:</label>
        <input type="text" id="voteTitle" required placeholder="输入投票标题">
    </div>
    <div class="input-group">
        <label for="voteDescription">投票描述:</label>
        <input type="text" id="voteDescription" required placeholder="输入投票描述">
    </div>
    <div class="input-group">
        <label for="expiryTime">过期时间（秒）:</label>
        <input type="number" id="expiryTime" min="1" required placeholder="设置投票过期时间">
    </div>
    <div class="input-group">
        <label>投票选项:</label>
        <ul class="options" id="optionsList">
            <li>
                <input type="text" class="option-input" placeholder="选项内容" required>
                <button type="button" class="remove-btn" onclick="removeOption(this)">删除选项</button>
            </li>
        </ul>
        <button type="button" class="btn add-option-btn" onclick="addOption()">添加选项</button>
    </div>
    <button type="submit" class="btn">创建投票</button>
</form>

<!-- 模态框（Modal） -->
<div class="modal fade" id="chatModal" tabindex="-1" role="dialog" aria-labelledby="chatModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="chatModalLabel">公共聊天室</h5>
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

</body>
</html>