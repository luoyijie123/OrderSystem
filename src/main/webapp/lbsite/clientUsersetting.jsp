<%--
  Created by IntelliJ IDEA.
  User: 75293
  Date: 2019/4/28
  Time: 1:55
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    <title> 用户资料 </title>
</head>
<body ontouchstart style="width:100%;height:100%;">
<div id="mainbody" style=" margin-bottom: 100px;width:100%;">


    <div style="margin:0 auto;"><Br>
        联系方式为了能顺利返款及订单出问题时有及时通知您，务必要填写正确。只需设置一次，后续可修改。
        <br>
        <form name="wfform" action="/lbsite/custominfo//" accept-charset="UTF-8" method="post">
            <input type='hidden' name='csrfmiddlewaretoken' value='t9BjgGrE8zuv4UXx1z5qhpCwapVjYO3QD8m0GaYgZtGw9ERG647qGp8bPppiKkV4' />
            <div style="display: flex;justify-content: left;flex-direction:column;margin-left: 10px;margin-right:10px;">


                <div class="weui-cell">
                    <div class="weui-cell__hd"><label class="weui-label">微信昵称 </label></div>
                    <div class="weui-cell__bd">
                        <input class="weui-input" name="nickname" type="text" placeholder="请输入"
                               value="">
                    </div>
                </div>

                <div style="width:100%;text-align: center">
                    <input type="submit" style="margin-top:10px;" onclick="savetocookie()" value="提交"
                           class="weui-btn  weui-btn_primary">
                </div>

            </div>

        </form>
    </div>



</div>
<div class="weui-tabbar" style="position:fixed;bottom:0;">
    <a href="clientHistoryOrder?useraccount=${usermodel.useraccount}" class="weui-tabbar__item">
        <img src="../static/icons-activity_history.png" alt="" class="weui-tabbar__icon">
        <p class="weui-tabbar__label">订单操作</p>
    </a>
    <a href="clientUsersetting?useraccount=${usermodel.useraccount}" class="weui-tabbar__item">
        <img src="../static/icons-user.png" alt="" class="weui-tabbar__icon">
        <p class="weui-tabbar__label">个人设置</p>
    </a>


</div>
</body>
</html>

