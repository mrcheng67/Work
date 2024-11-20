<!DOCTYPE html>
<html>
<head>
    <title>WebSocket Test</title>
</head>
<body>
<h1>WebSocket Test</h1>
<input type="text" id="messageInput" placeholder="Enter message">
<button onclick="sendMessage()">Send Message</button>
<div id="messages"></div>

<script>
    var socket = new WebSocket('ws://localhost:1024/channel');

    socket.onopen = function() {
        console.log('Connected to WebSocket server');
        document.getElementById('messages').innerHTML += '<p>Connected to WebSocket server</p>';
    };

    socket.onmessage = function(event) {
        console.log('Received message: ' + event.data);
        document.getElementById('messages').innerHTML += '<p>Server response: ' + event.data + '</p>';
    };

    socket.onclose = function(event) {
        console.log('Disconnected from WebSocket server');
        document.getElementById('messages').innerHTML += '<p>Disconnected from WebSocket server</p>';
    };

    socket.onerror = function(error) {
        console.error('WebSocket error: ' + error);
        document.getElementById('messages').innerHTML += '<p>WebSocket error: ' + error + '</p>';
    };

    function sendMessage() {
        var message = document.getElementById('messageInput').value;
        if (message) {
            socket.send(message);
            document.getElementById('messages').innerHTML += '<p>Sent message: ' + message + '</p>';
            document.getElementById('messageInput').value = '';
        }
    }
</script>
</body>
</html>
