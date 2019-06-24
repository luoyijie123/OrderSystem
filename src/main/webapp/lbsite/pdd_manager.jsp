<%--
  Created by IntelliJ IDEA.
  User: 75293
  Date: 2019/1/25
  Time: 1:09
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
                $("#txtEndDate").val(todaystr);
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

                $("#txtEndDate").calendar({

                    controlId: "divDate2",                                 // 弹出的日期控件ID，默认: $(this).attr("id") + "Calendar"

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
                            <span>授权中心和账号管理</span></a>
                    </div>
                </div>
                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a class="accordion-toggle b_9FDDF6" href="uploadjddb"><i class="icon-bullhorn"></i>
                            <span>上传数据</span></a>
                    </div>
                </div>

                <%--<div class="accordion-group">--%>
                    <%--<div class="accordion-heading">--%>
                        <%--<a class="accordion-toggle b_9FDDF6" href="selectorder"><i class="icon-bullhorn"></i>--%>
                            <%--<span>订单查询</span></a>--%>
                    <%--</div>--%>
                <%--</div>--%>

                <div class="accordion-group">
                    <div class="accordion-heading">
                        <a class="accordion-toggle b_9FDDF6" href="datachaxun"><i class="icon-bullhorn"></i>
                            <span>数据查询</span></a>
                    </div>
                </div>

                <%--<div class="accordion-group">--%>
                    <%--<div class="accordion-heading">--%>
                        <%--<a class="accordion-toggle b_9FDDF6" href="checkuser"><i class="icon-bullhorn"></i>--%>
                            <%--<span>查找用户</span></a>--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="accordion-group">--%>
                    <%--<div class="accordion-heading">--%>
                        <%--<a class="accordion-toggle b_9FDDF6" href="selectproduct"><i class="icon-bullhorn"></i>--%>
                            <%--<span>按商品查询</span></a>--%>
                    <%--</div>--%>
                <%--</div>--%>

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

            </div>
        </div>
        <!-- /Side menu -->

        <!-- Main window -->
        <div class="main_container" id="dashboard_page">
            <ul class="nav nav-tabs">
                <li class><a href="index">授权中心</a></li>
                <li class="active"><a href="pdd_manager">拼多多</a></li>
                <li><a href="jd_manager">京东</a></li>
                <li><a href="tb_manager">淘宝客</a></li>
            </ul>

            <table class="table">
                <caption>当前授权的的多多进宝联盟账号</caption>
                <thead>
                <tr>
                    <th>手机</th>
                    <th>多多客ID</th>
                    <th>姓名</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${pddauthoModel.pddautho.phone}</td>
                    <td>${pddauthoModel.pddautho.ddkid}</td>
                    <td>${pddauthoModel.pddautho.name}</td>
                </tr>
                </tbody>
            </table>
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


