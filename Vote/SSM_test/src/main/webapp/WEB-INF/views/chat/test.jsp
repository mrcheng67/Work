<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>群聊天室</title>
    <style type="text/css">
        body {
            margin-right:50px;
            margin-left:50px;
        }
        .ddois {
            position: fixed;
            left: 120px;
            bottom: 30px;
        }
    </style>
</head>
<body>
群名：<input type="text" id="room" name="group" placeholder="请输入群">
<br /><br />
昵称：<input type="text" id="nick" name="name" placeholder="请输入昵称">
<br /><br />
<button type="button" onclick="enter()">进入聊天群</button>
<br /><br />
<div id="message"></div>
<br /><br />
<div class="ddois">
    <textarea name="send" id="text" rows="10" cols="30" placeholder="输入发送消息"></textarea>
    <br /><br />
    <button type="button" onclick="send()">发送</button>
</div>
<script type="text/javascript">
    var webSocket;
    if (window.WebSocket) {
        webSocket = new WebSocket("ws://localhost:53134/ws");
    } else {
        alert("抱歉，您的浏览器不支持WebSocket协议!");
    }

    //连通之后的回调事件
    webSocket.onopen = function() {
        console.log("已经连通了websocket");
//                setMessageInnerHTML("已经连通了websocket");
    };
    //连接发生错误的回调方法
    webSocket.onerror = function(event){
        console.log("出错了");
//              setMessageInnerHTML("连接失败");
    };

    //连接关闭的回调方法
    webSocket.onclose = function(){
        console.log("连接已关闭...");

    }

    //接收到消息的回调方法
    webSocket.onmessage = function(event){
        console.log("bbdds");
        var data = JSON.parse(event.data)
        var msg = data.msg;
        var nick = data.sendUser;
        switch(data.type){
            case 'init':
                console.log("mmll");
                break;
            case 'msg':
                console.log("bblld");
                setMessageInnerHTML(nick+":  "+msg);
                break;
            default:
                break;
        }
    }
    function enter(){
        var map = new Map();
        var nick=document.getElementById('nick').value;
        var room=document.getElementById('room').value;
        map.set("type","init");
        map.set("nick",nick);
        console.log(room);
        map.set("room",room);
        var message = Map2Json(map);
        webSocket.send(message);
    }

    function send() {
        var msg = document.getElementById('text').value;
        var nick = document.getElementById('nick').value;
        console.log("1:"+msg);
        if (msg != null && msg != ""){
            var map = new Map();
            map.set("type","msg");
            map.set("msg",msg);
            var map2json=Map2Json(map);
            if (map2json.length < 8000){
                console.log("4:"+map2json);
                webSocket.send(map2json);
            }else {
                console.log("文本太长了，少写一点吧😭");
            }
        }
    }

    //将消息显示在网页上
    function setMessageInnerHTML(innerHTML) {
        document.getElementById("message").innerHTML += innerHTML + "<br/>";
    }

    function Map2Json(map) {
        var str = "{";
        map.forEach(function (value, key) {
            str += '"'+key+'"'+':'+ '"'+value+'",';
        })
        str = str.substring(0,str.length-1)
        str +="}";
        return str;
    }

</script>
</body>
</html>
