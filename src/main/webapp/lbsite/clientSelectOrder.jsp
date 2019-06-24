<%--
  Created by IntelliJ IDEA.
  User: 75293
  Date: 2019/4/28
  Time: 1:54
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: 75293
  Date: 2019/4/28
  Time: 1:54
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
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

    <ul class="nav nav-tabs">
        <li class="active"><a href="clientSelectOrder?useraccount=${usermodel.useraccount}">订单查询</a></li>
        <li><a href="clientHistoryOrder?useraccount=${usermodel.useraccount}">历史订单</a></li>
    </ul>

    <div>
        <form action="${pageContext.request.contextPath}/client/clientSelectOrderOperation?useraccount=${usermodel.useraccount}"><input type='hidden' name='useraccount'
                  value='${usermodel.useraccount}'/>
            一行一个订单号，多次提交以最后提交的为准。<br>
            <div style="display: flex;justify-content: left;flex-direction:column;margin-left: 10px;">
                <div style="display: flex;justify-content: left;">

            <textarea class="weui-textarea" id="dingdanlist" name="dingdanlist"
                      style="width:90%;background-color: #EEE;height:300px;" rows="11" placeholder="一行一个订单号"></textarea>
                </div>

                <br>

            </div>
            <input type="submit" style="width:300px;" class="weui-btn weui-btn_plain-primary">提交</input>
        </form>
    </div>

    <div id="showresulttb">
        <Br>"提交时间"默认时间为系统增加此功能的时间"2018-05-30 18:29:08"，之后提交的订单此项数据才会更新。
        <div class="table-responsive">
            <table id="restb" class="table table-bordered" style="text-align: center;font-size:12px;">
                <thead>
                <tr>
                    <th>下单时间</th>
                    <th>商品名称</th>
                    <th>订单号</th>
                    <th>预估金额</th>
                    <th>订单状态</th>
                    <th>完成时间</th>
                    <th>微信昵称</th>
                    <th>返款状态</th>
                    <th>是否提交</th>
                    <th>提交时间</th>
                    <th>订单渠道</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${usermodel.orderList}" var="order">
                    <tr>
                        <td>${order.orderTime}</td>
                        <td>${order.productName}</td>
                        <td>${order.orderId}</td>
                        <td>${order.estimated}</td>
                        <td>${order.state}</td>
                        <td>${order.finishTime}</td>
                        <td>${order.weixin}</td>
                        <td>${order.refunds}</td>
                        <td>${order.isSubmit}</td>
                        <td>${order.submitTime}</td>
                        <td>${order.channel}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

            </div>
        </div>
        <div style="font-size:16px;">
            <p style="font-weight: bold;font-size:16px;color:red;">
                京东放大招，99元买10本书，记得挑贵的买。
                <a href="https://union-click.jd.com/jdc?d=J6dEK5">https://union-click.jd.com/jdc?d=J6dEK5</a>
                <a href="https://union-click.jd.com/jdc?d=J6dEK5"><img style="width:100%;float:left" src="https://www.spsq.xyz/pubimg/jdbook.jpg" /></a>
            </p>

        </div>
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


