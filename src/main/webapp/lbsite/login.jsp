<%--
  Created by IntelliJ IDEA.
  User: 75293
  Date: 2018/11/2
  Time: 19:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>后台登录</title>
    <link rel="stylesheet" href="https://unpkg.com/mobi.css/dist/mobi.min.css">
</head>
<body>
<div class="flex-center">
    <div class="container">
        <div>


            请登录<br>
            <form class="form" action="${pageContext.request.contextPath}/user/checklogin.action" method="post">
                <input type='hidden' name='csrfmiddlewaretoken' value='iWc2M1pxRymeYYsdVWCxGbkl5C1GALwcF0NPOAyjarDGuV3bZFvw6i6d9Pknd72i' />
                用户名<input type="text" name="account" ><br>
                密码<input type="password" name="password" >
                <br>
                <button type="submit" class="btn btn-primary btn-block">登录</button>
            </form>


        </div>
    </div>
</div>
</body>
</html>

