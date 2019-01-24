<%--
  Created by IntelliJ IDEA.
  User: 75293
  Date: 2018/11/2
  Time: 3:31
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
        var totaldingdanlist = [];

        function cleartb() {
            //var headstr="<tr><td>下单时间</td><td>商品名称</td><td>订单号</td><td>预估金额</td><td>订单状态</td><td>完成时间</td></tr>";
            $("#restb tbody").html("");
            totaldingdanlist = []
        }

        function addmultiRow(tbjson) {
            var tbjson = $.parseJSON(tbjson);
            var totalstr = [];
            for (var rowitem in tbjson) {
                var rowitem = tbjson[rowitem];
                totaldingdanlist.push(rowitem["dingdanhao"]);
                var rowTemplate = "<tr><td>" + rowitem["xdtime"] + "</td><td>" + rowitem["productname"] + "</td><td>" + rowitem["dingdanhao"] + "</td><td>" + rowitem["estprice"] + "</td><td>" + rowitem["dingdanstatus"] + "</td><td>" + rowitem["finishtime"] + "</td><td>"+rowitem["nickname"]+"</td><td>"+rowitem["fankuanstatus"]+"</td><td>"+rowitem["tijiao"]+"</td><td>"+rowitem["addtime"].slice(0,19)+"</td></tr>";

                totalstr.push(rowTemplate);
            }
            var finalstr = totalstr.join("");
            $("#restb tbody").html(finalstr);
        }
        function getuserdingdanbyuser() {
            var searchparam=$("input[name='contacttype']:checked").val();
            var searchval=$("#searchvalue").val();

            $.post("/lbsite/ajaxsellerdingdan/",
                {
                    type: "getdd_byuser",
                    searchparam: searchparam,
                    searchvalue:searchval
                },
                function (ret) {

                    var status = ret["status"];
                    if (status == "noauth") {
                        alert("未授权");
                    }
                    else if (status == "empty") {
                        alert("未查到相关订单");
                    }
                    else if (status == "ok") {
                        retdata = ret["data"];
                        cleartb();
                        addmultiRow(retdata);
                    }
                });
            return false;
        }
    </script>


    <style>
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


    <title> 用户填写事项设置 </title>
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
                        <li><a href="/lbsite/logout/"><i class="icon-off"></i>退出</a></li>
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
                        <a class="accordion-toggle b_9FDDF6" href="/lbsite/tongjiexcel"><i class="icon-bullhorn"></i>
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

            <br><span style="color:red"></span><br>
            <form method="post">
                <input type='hidden' name='csrfmiddlewaretoken' value='W8oPHg5xCIPMmQofImQBSg8Tutp6kp3VLzaT3M2JaTwRmjsB6JmfL2PCSiyqa7JS' />
                1、选择要查找用户的联系方式
                <div class="weui-cells weui-cells_radio">
                    <label class="weui-cell weui-check__label" for="x11">
                        <div class="weui-cell__bd">
                            <p>微信昵称</p>
                        </div>
                        <div class="weui-cell__ft">
                            <input type="radio" class="weui-check" name="contacttype" value="nickname" id="x11">
                            <span class="weui-icon-checked"></span>
                        </div>
                    </label>
                    <label class="weui-cell weui-check__label" for="x12">

                        <div class="weui-cell__bd">
                            <p>微信号</p>
                        </div>
                        <div class="weui-cell__ft">
                            <input type="radio" name="contacttype" value="weixin" class="weui-check" id="x12" checked="checked">
                            <span class="weui-icon-checked"></span>
                        </div>
                    </label>
                    <label class="weui-cell weui-check__label" for="x13">

                        <div class="weui-cell__bd">
                            <p>姓名</p>
                        </div>
                        <div class="weui-cell__ft">
                            <input type="radio" name="contacttype" value="xingming" class="weui-check" id="x13"
                                   checked="checked">
                            <span class="weui-icon-checked"></span>
                        </div>
                    </label>
                    <label class="weui-cell weui-check__label" for="x14">

                        <div class="weui-cell__bd">
                            <p>支付宝账号</p>
                        </div>
                        <div class="weui-cell__ft">
                            <input type="radio" name="contacttype" value="alipay" class="weui-check" id="x14" checked="checked">
                            <span class="weui-icon-checked"></span>
                        </div>
                    </label>
                    <label class="weui-cell weui-check__label" for="x15">

                        <div class="weui-cell__bd">
                            <p>手机号码</p>
                        </div>
                        <div class="weui-cell__ft">
                            <input type="radio" value="phone" name="contacttype" class="weui-check" id="x15" checked="checked">
                            <span class="weui-icon-checked"></span>
                        </div>
                    </label>

                    <label class="weui-cell weui-check__label" for="x16">

                        <div class="weui-cell__bd">
                            <p>平台账号</p>
                        </div>
                        <div class="weui-cell__ft">
                            <input type="radio" value="platform" name="contacttype" class="weui-check" id="x16"
                                   checked="checked">
                            <span class="weui-icon-checked"></span>
                        </div>
                    </label>

                    <label class="weui-cell weui-check__label" for="x17">

                        <div class="weui-cell__bd">
                            <p>邮箱</p>
                        </div>
                        <div class="weui-cell__ft">
                            <input type="radio" value="email" name="contacttype" class="weui-check" id="x17" checked="checked">
                            <span class="weui-icon-checked"></span>
                        </div>
                    </label>
                </div><br>
                2、查询值<input type="text" id="searchvalue" name="searchvalue" style="height:25px;width:200px;"/>
                <br> <a onclick="getuserdingdanbyuser()" style="margin-top:10px;"
                        class="weui-btn weui-btn_primary">查询</a>
            </form>
            <Br>"提交时间"默认时间为系统增加此功能的时间"2018-05-30 18:29:08"，之后提交的订单此项数据才会更新。
            <div id="showresulttb">
                <div id="productname" style="clear:both;float: left;color:blue;"></div>
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
                        </tr>
                        </thead>
                        <tbody></tbody>
                    </table>
                </div>
            </div>



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

