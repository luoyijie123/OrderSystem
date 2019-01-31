<%--
  Created by IntelliJ IDEA.
  User: 75293
  Date: 2018/11/2
  Time: 2:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="author" content="Bluth Company">
    <link href="../static/assets/css/bootstrap.css" rel="stylesheet">
    <link href="../static/assets/css/theme.css" rel="stylesheet">
    <link href="../static/assets/css/font-awesome.min.css" rel="stylesheet">
    <link href="../static/assets/css/alertify.css" rel="stylesheet">
    <link rel="stylesheet" href="https://res.wx.qq.com/open/libs/weui/1.1.2/weui.min.css">

    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/4.1.0/js/bootstrap.js"></script>

    <script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
    <script src="https://res.wx.qq.com/open/libs/weuijs/1.0.0/weui.min.js"></script>

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <script src="../static/js/lyz.calendar.min.js" type="text/javascript"></script>
    <script src="https://cdn.bootcss.com/clipboard.js/2.0.0/clipboard.js"></script>
    <script>
        function getCookie(name) {
            var cookieValue = null;
            if (document.cookie && document.cookie != '') {
                var cookies = document.cookie.split(';');
                for (var i = 0; i < cookies.length; i++) {
                    var cookie = jQuery.trim(cookies[i]);
                    // Does this cookie string begin with the name we want?
                    if (cookie.substring(0, name.length + 1) == (name + '=')) {
                        cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                        break;
                    }
                }
            }
            return cookieValue;
        }

        function csrfSafeMethod(method) {
            // these HTTP methods do not require CSRF protection
            return (/^(GET|HEAD|OPTIONS|TRACE)$/.test(method));
        }

        $.ajaxSetup({
            beforeSend: function (xhr, settings) {
                var csrftoken = getCookie('csrftoken');
                if (!csrfSafeMethod(settings.type) && !this.crossDomain) {
                    xhr.setRequestHeader("X-CSRFToken", csrftoken);
                }
            }
        });

        function autofetch(optype) {
            $.post("/lbsite/ajaxjosdata/",
                {
                    type: optype,
                    datestr: $("#txtBeginDate").val(),

                },
                function (ret) {
                    var status = ret["status"];
                    if (status == "noauth") {
                        alert("未授权");
                    }
                    else if (status == "empty") {
                        alert("未查到相关数据");
                    }
                    else if (status == "ok") {
                        alert("获取成功，共" + ret["msg"] + "条");
                    }
                });
            return false;
        }

        function getNowFormatDate() {
            var date = new Date();
            var seperator1 = "-";
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            var currentdate = year + seperator1 + month + seperator1 + strDate;
            return currentdate;
        }

        $(
            function () {
                new ClipboardJS('.btn');
                var today = new Date();
                var todaystr = getNowFormatDate()
                $("#txtBeginDate").val(todaystr);
                $("#txtBeginDate").calendar({

                    controlId: "divDate",                                 // 弹出的日期控件ID，默认: $(this).attr("id") + "Calendar"

                    speed: 200,                                           // 三种预定速度之一的字符串("slow", "normal", or "fast")或表示动画时长的毫秒数值(如：1000),默认：200

                    complement: true,                                     // 是否显示日期或年空白处的前后月的补充,默认：true

                    readonly: true,                                       // 目标对象是否设为只读，默认：true

                    upperLimit: new Date(),                               // 日期上限，默认：NaN(不限制)

                    lowerLimit: new Date("2018/04/15"),                   // 日期下限，默认：NaN(不限制)

                    callback: function () {                               // 点击选择日期后的回调函数


                    }

                });


            });

    </script>




    <style>
        .greenbtn {
            color: #e8f0de;
            border: solid 1px #538312;
            background: -webkit-gradient(linear, left top, left bottom, from(#7db72f), to(#4e7d0e));
            display: inline-block;
            zoom: 1;
            vertical-align: baseline;
            margin: 0 2px;
            outline: none;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            font: 14px/100% Arial, Helvetica, sans-serif;
            padding: .5em 2em .55em;
            text-shadow: 0 1px 1px rgba(0, 0, 0, .3);
            border-radius: .5em;
            box-shadow: 0 1px 2px rgba(0, 0, 0, .2);
            box-sizing: border-box;
            align-items: flex-start;
            text-rendering: auto;
            letter-spacing: normal;
            word-spacing: normal;
            text-transform: none;
            text-indent: 0px;
        }

        .oneitem {
            float: left;
            clear: both;
            width: 100%
        }

        #divDate {
            margin-top: 20px;
        }

        #divDate2 {
            margin-top: 60px;
        }

        #id_filepath {
            background-color: #e8f0de;
        }
    </style>
    <link href="../static/css/lyz.calendar.css" rel="stylesheet" type="text/css"/>



    <title> 后台首页 </title>
</head>
<body>
<div id="wrap">
    <div class="navbar navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container-fluid">
                <div class="logo">
                    <img src="../static/assets/img/logo.png" alt="Realm Admin Template">
                </div>
                <a class="btn btn-navbar visible-phone" data-toggle="collapse" data-target=".nav-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </a>
                <a class="btn btn-navbar slide_menu_left visible-tablet">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </a>

                <div class="top-menu visible-desktop">
                    <ul class="pull-right">
                        <li><a href="logout"><i class="icon-off"></i>退出</a></li>
                    </ul>
                </div>

                <div class="top-menu visible-phone visible-tablet">
                    <ul class="pull-right">
                        <li><a title="link to View all Messages page, no popover in phone view or tablet" href="#"><i
                                class="icon-envelope"></i></a></li>
                        <li><a title="link to View all Notifications page, no popover in phone view or tablet" href="#"><i
                                class="icon-globe"></i></a></li>
                        <li><a href="login.html"><i class="icon-off"></i></a></li>
                    </ul>
                </div>

            </div>
        </div>
    </div>

    <div class="container-fluid">

        <!-- Side menu -->
        <div class="sidebar-nav nav-collapse collapse">
            <div class="user_side clearfix">
                <img src="../static/assets/img/odinn.jpg" alt="Odinn god of Thunder">
                <h5>user39</h5>
            </div>
            <div class="accordion" id="accordion2">
                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a class="accordion-toggle active b_F79999" href="index"><i class="icon-dashboard"></i>
                            <span>首页</span></a>
                    </div>
                </div>
                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a class="accordion-toggle b_9FDDF6" href="uploadjddb"><i class="icon-bullhorn"></i>
                            <span>上传数据</span></a>
                    </div>
                </div>
                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a class="accordion-toggle b_9FDDF6" href="selectorder"><i class="icon-bullhorn"></i>
                            <span>订单查询</span></a>
                    </div>
                </div>
                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a class="accordion-toggle b_9FDDF6" href="checkuser"><i class="icon-bullhorn"></i>
                            <span>查找用户</span></a>
                    </div>
                </div>
                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a class="accordion-toggle b_9FDDF6" href="selectproduct"><i class="icon-bullhorn"></i>
                            <span>按商品查询</span></a>
                    </div>
                </div>
                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a class="accordion-toggle b_9FDDF6" href="output"><i class="icon-bullhorn"></i>
                            <span>导出统计表</span></a>
                    </div>
                </div>

                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a class="accordion-toggle b_9FDDF6" href="UserManager"><i class="icon-bullhorn"></i>
                            <span>新建会员</span></a>
                    </div>
                </div>

                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a class="accordion-toggle b_9FDDF6" href="jiankong"><i class="icon-bullhorn"></i>
                            <span>监控账号管理</span></a>
                    </div>
                </div>

            </div>
        </div>
        <!-- /Side menu -->

        <!-- Main window -->
        <div class="main_container" id="dashboard_page">


            <div class="oneitem">
                <span style="color:red;float:left"></span>


            </div>


            <div class="oneitem">


                <div style="float:left;clear:both;width:100%;">
                    <div>
                        <span style="font-size:16px;">我们可实时为您监控最新订单信息。</span>
                        <br>也就是说如果您已经在"首页"设置了京东授权并填写京东联盟ID,系统将自动为您监控最新订单，下面的可以都不用看了。
                        <Br>
                        若不想后台自动监控，可在"首页"中将"联盟ID"设为 0
                    </div>


                    <div style="width:100%;margin-top:20px;border:solid 1px green;">
                        特殊情况下可通过下面的两个按键手动更新某日的订单状态<Br>
                        <span style="width:70px;">日期</span>
                        <input id="txtBeginDate" name="txtBeginDate"
                               style="width:170px;padding:7px 10px;border:1px solid #ccc;margin-right:10px;"
                               value="日期"/>
                        <div style="float:left;margin-left:200px;">
                            <div id="calcontainer"></div>

                        </div>
                        <Br>
                        <div style="margin-top:2px;margin-bottom: 10px;">

                            <br>
                            <a href="#" onclick="autofetch('autofetchnew')" style="margin-right: 30px;">获取该日所有订单</a>
                            <a href="#" onclick="autofetch('autoupdate')">自动更新(暂不需要使用)</a>
                            <Br>点击之后可能要等待1分钟才完成任务，有返回弹窗才表示操作成功。
                        </div>



                    </div>


                </div>



            </div>



            <div class="oneitem" style="margin-top:20px;border:solid 1px blue;">

                <Br>
                当然您仍可以在关闭自动监控之后继续使用手动上传Excel的模式，完全不受影响。
                <br>
                上传京东后台导出的订单数据(请将导出的csv文件转换为xls格式后再上传)
                <form method="post" enctype="multipart/form-data">
                    <p><label for="id_filepath">Filepath:</label> <input type="file" name="filepath" required id="id_filepath" /></p>
                    <input type="submit" value="上传" style="width:100px;height:30px;" class="greenbtn"/>

                </form>

                <hr>

            </div>
            本系统也可用于淘宝及其他平台的订单统计，使用说明：
            <br>按照下面的模板填写相应字段后上传，其中标红的为必填项，非必填的列也不要删除。<br>
            <a href="/lbsite/taobaotemplate/">模板下载链接</a>


        </div>
        <!-- /Main window -->

    </div><!--/.fluid-container-->
</div><!-- wrap ends-->
<script src="https://cdn.bootcss.com/jqueryui/1.12.1/jquery-ui.min.js"></script>
<script type="text/javascript" src="../static/assets/js/raphael-min.js"></script>
<script type="text/javascript" src="../static/assets/js/sparkline.js"></script>
<script type="text/javascript" src="../static/assets/js/morris.min.js"></script>
<script src="https://cdn.bootcss.com/datatables/1.10.16/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.bootcss.com/masonry/2.1.08/jquery.masonry.min.js"></script>
<script src="https://cdn.bootcss.com/jquery.imagesloaded/4.1.4/imagesloaded.min.js"></script>
<script type="text/javascript" src="../static/assets/js/jquery.facybox.js"></script>
<script type="text/javascript" src="../static/assets/js/jquery.alertify.min.js"></script>
<script src="https://cdn.bootcss.com/jQuery-Knob/1.2.13/jquery.knob.min.js"></script>
<script type='text/javascript' src="../static/assets/js/fullcalendar.min.js"></script>
<script src="https://cdn.bootcss.com/jQuery-slimScroll/1.3.8/jquery.slimscroll.min.js"></script>


</body>
</html>

