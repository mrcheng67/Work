<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Login Page</title>

    <style>
        * {
            margin: 0;
            padding: 0;
        }
        html {
            height: 100%;
        }
        body {
            height: 100%;
        }
        .container {
            height: 100%;
            background-image: linear-gradient(to right, #999999, #330867);
        }
        .login-wrapper {
            background-color: bisque;
            width: 358px;
            height: 588px;
            border-radius: 15px;
            padding: 0 50px;
            position: relative;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
        }
        .header {
            font-size: 38px;
            font-weight: bold;
            text-align: center;
            line-height: 200px;
        }
        .input-item {
            display: block;
            width: 100%;
            margin-bottom: 20px;
            border: 0;
            padding: 10px;
            border-bottom: 1px solid rgb(128, 125, 125);
            font-size: 15px;
            outline: none;
        }
        .input-item::placeholder {
            text-transform: uppercase;
        }
        .btn {
            text-align: center;
            padding: 10px;
            width: 100%;
            margin-top: 40px;
            background-image: linear-gradient(to right, #a6c1ee, #fbc2eb);
            color: #fff;
        }
        .msg {
            text-align: center;
            line-height: 88px;
            color: red;
        }
        a {
            text-decoration-line: none;
            color: #abc1ee;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="login-wrapper">
        <div class="header">Login</div>
        <form id="loginForm">
            <input type="text" name="username" placeholder="username" class="input-item" required>
            <input type="password" name="password" placeholder="password" class="input-item" required>
            <button type="submit" class="btn">Login</button>
        </form>

        <div class="msg" id="msg"></div>
        <div class="msg">
            Don't have account?
            <a href="${pageContext.request.contextPath}/user/getEmployee">Sign up</a>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script> <!-- 引入 Axios -->
<script>
    document.getElementById('loginForm').addEventListener('submit', function(event) {
        event.preventDefault(); // 阻止表单默认提交行为
        axios.defaults.withCredentials = true;

        const formData = new FormData(event.target); // 获取表单数据
        fetch('/', { // 发送 POST 请求到 /login
            method: 'POST',
            body: formData,
            headers: {
                'Accept': 'application/json'
            }
        })
            .then(response => response.json()) // 将响应转换为 JSON
            .then(data => {
                // 根据 data.code 判断登录是否成功
                if (data.code === 200) { // 判断是否登录成功
                    // 将 token 存储到 localStorage 或 sessionStorage
                    const token = data.data; // 假设 data.data 是 token
                    localStorage.setItem('loginToken', token); // 使用 localStorage 存储 token

                    document.getElementById('msg').innerText = '登录成功，请稍候...'; // 显示登录成功的信息

                    axios.defaults.headers['Authorization'] = token;
                    location.href = '/vote/vote'
                    // 不需要在这里跳转，后端已经处理了重定向

                } else {
                    document.getElementById('msg').innerText = '登录失败，信息: ' + data.message; // 显示失败信息
                }
            })
            .catch(error => {
                console.log('Error:', error);
                document.getElementById('msg').innerText = '网络请求出错，请稍后再试。'; // 显示网络请求错误
            });
    });
</script>
</body>
</html>