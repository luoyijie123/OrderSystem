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

    <div style="width:100%;clear:both;">
        您所有提交过的订单(若发现缺少订单可尝试重新提交，若还少，请联系管理员。)

    </div>
    <div style="width:100%;clear:both;">

        <div class="table-responsive">
            <div>
                <table class="table table-bordered" style="text-align: center;font-size:12px;">
                    <tr style="text-align: center;">
                        <td>订单号</td>
                        <td>商品名</td>
                        <td>付款金额</td>
                        <td>状态</td>
                    </tr>

                </table>
                <p>说明： <br>若有未查询到的订单请稍后再查询。<br>
                    若显示"已付款"表示订单状态正常。<br>
                    请确保在"个人设置"中设置了联系方式。
                </p>
                <div style="marign-top:30px;">
                    <button id="btn" style="z-index:999;opacity:0;width:1px;height:1px;"></button>
                    <script src="https://cdn.bootcss.com/clipboard.js/2.0.0/clipboard.js"></script>
                    <script type="text/javascript">

                        var clipboard = new ClipboardJS('#btn', {
                                text: function () {

                                    return "1mk1w663M8";
                                }
                            }
                        );


                        function wxclosepage() {
                            $.post("/lbsite/ajaxredbag/",
                                {
                                    type: "redbag_checkresult"
                                }
                            );

                            $("#btn").click();
                            WeixinJSBridge.call('closeWindow');

                            return false;
                        }
                    </script>
                    <a id="btnclose" onclick="wxclosepage()" href="#" style="width:150px;"

                       class="weui-btn weui-btn_primary ">退出</a>
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
    <a href="clientSelectOrder" class="weui-tabbar__item">
                    <span style="display: inline-block;position: relative;">
                        <img src="../static/icons-search.png" alt="" class="weui-tabbar__icon">
                    </span>
        <p class="weui-tabbar__label">查询订单</p>
    </a>
    <a href="clientHistoryOrder" class="weui-tabbar__item">
        <img src="../static/icons-activity_history.png" alt="" class="weui-tabbar__icon">
        <p class="weui-tabbar__label">历史订单</p>
    </a>
    <a href="clientUsersetting" class="weui-tabbar__item">
        <img src="../static/icons-user.png" alt="" class="weui-tabbar__icon">
        <p class="weui-tabbar__label">个人设置</p>
    </a>


</div>
</body>
</html>


