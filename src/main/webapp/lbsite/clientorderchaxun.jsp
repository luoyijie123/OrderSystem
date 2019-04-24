<%--
  Created by IntelliJ IDEA.
  User: 75293
  Date: 2019/4/25
  Time: 5:50
  To change this template use File | Settings | File Templates.
--%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
    <script src="https://res.wx.qq.com/open/libs/weuijs/1.0.0/weui.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/4.1.0/js/bootstrap.js"></script>
    <script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
    <script src="/static/js/csrf.js" type="text/javascript"></script>






    <!-- Global site tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=UA-118662203-1"></script>
    <script>
        window.dataLayer = window.dataLayer || [];

        function gtag() {
            dataLayer.push(arguments);
        }

        gtag('js', new Date());

        gtag('config', 'UA-118662203-1');
    </script>
    <script>
        function loadfromcookie() {
            readfromcookie();
            readDDfromcookie();
        }

        function savetocookie() {
            add2cookie();
            saveDDtocookie();
            return true;
        }

        function add2cookie() {
            var txtlist = $(":text");

            for (var i = 0; i < txtlist.length; i++) {
                var iname = txtlist[i].name;
                var ivalue = txtlist[i].value;
                $.cookie(iname, ivalue);

            }

        }

        function readfromcookie() {
            //只读取表单中有的项目
            var txtlist = $(":text");
            for (var i = 0; i < txtlist.length; i++) {
                var iname = txtlist[i].name;
                var thisval = $.cookie(iname);
                if (thisval != null) {

                    txtlist[i].value = thisval;
                }


            }
        }

        function saveDDtocookie() {
            var ddlist = $("#dingdanlist");
            console.log(ddlist.val());
            $.cookie("ddlist", ddlist.val());
        }

        function readDDfromcookie() {
            var dd = $.cookie("ddlist");
            if (dd != null) {
                $("#dingdanlist").val(dd);
            }
        }

        $(loadfromcookie);

    </script>

    <title> 查询结果 </title>
</head>
<body ontouchstart style="width:100%;height:100%;">
<div id="mainbody" style=" margin-bottom: 100px;width:100%;">

    <div>
        <input type='hidden' name='csrfmiddlewaretoken' value='vVmdq6loOkBT9nqK0KYkbh8ctAe1JX57km8hMCiAmviY9Qu6o7uY43PVRpnlzFL4' />
        一行一个订单号，多次提交以最后提交的为准。<br>
        <div style="display: flex;justify-content: left;flex-direction:column;margin-left: 10px;">
            <div style="display: flex;justify-content: left;">

            <textarea class="weui-textarea" id="dingdanlist" name="dingdanlist"
                      style="width:90%;background-color: #EEE;height:300px;" rows="11" placeholder="一行一个订单号"></textarea>
            </div>

            <br>

        </div>
        <a style="width:300px;" class="weui-btn weui-btn_plain-primary" onclick="getfromdb()">提交</a>
    </div>
    </div>



</div>
<div class="weui-tabbar" style="position:fixed;bottom:0;">
    <a href="/lbsite/checkjd/empty/" class="weui-tabbar__item">
                    <span style="display: inline-block;position: relative;">
                        <img src="/static/icons-search.png" alt="" class="weui-tabbar__icon">
                    </span>
        <p class="weui-tabbar__label">查询订单</p>
    </a>
    <a href="/lbsite/customhistoryorders/" class="weui-tabbar__item">
        <img src="/static/icons-activity_history.png" alt="" class="weui-tabbar__icon">
        <p class="weui-tabbar__label">历史订单</p>
    </a>
    <a href="/lbsite/custominfo/empty/" class="weui-tabbar__item">
        <img src="/static/icons-user.png" alt="" class="weui-tabbar__icon">
        <p class="weui-tabbar__label">个人设置</p>
    </a>


</div>
</body>
</html>

