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
            //var tbjson = $.parseJSON(tbjson);
            var tbjson = tbjson;
            var totalstr = [];
            for (var rowitem in tbjson) {
                var rowitem = tbjson[rowitem];
                totaldingdanlist.push(rowitem["orderId"]);
                var rowTemplate = "<tr><td>" + rowitem["orderTime"] + "</td><td>" + rowitem["productName"] + "</td><td>" + rowitem["orderId"] + "</td><td>" + rowitem["estimated"] + "</td><td>" + rowitem["state"] + "</td><td>" + rowitem["finishTime"] + "</td><td>"+rowitem["weixin"]+"</td><td>"+rowitem["refunds"]+"</td><td>"+rowitem["isSubmit"]+"</td><td>"+rowitem["submitTime"]+"</td><td>"+rowitem["channel"]+"</td></tr>";
                totalstr.push(rowTemplate);
            }
            var finalstr = totalstr.join("");
            $("#restb tbody").html(finalstr);

        }

        function setfankuan(fankuan) {
            var lst = JSON.stringify(totaldingdanlist);
            if(totaldingdanlist.length==0){
                alert("无数据可更新");
                return false;
            }
            if( fankuan==1) fankuanstr="已返款";
            else if(fankuan==0) fankuanstr="未返款";
            $.post("/lbsite/ajaxsellerdingdan/",
                {
                    type: "setfankuan",
                    dingdanlist: lst,
                    fankuanstr:fankuanstr
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
                        alert("设置成功");
                    }
                });
            return false;
        }

        function getfromdb() {
            var lst = $("#dingdanlist").val();

            $.post("${pageContext.request.contextPath}/order/ajaxsellerdingdan/",
                {
                    type: "getdd",

                    dingdanlist: lst

                },
                function (ret) {
                    //alert(ret);
                    //ret = $.parseJSON(ret);
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


                },"json");
            return false;
        }


    </script>



    <title> 添加已返款 </title>
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
            <div id="showresulttb">
                <div id="resultstr">
                    <span><a href="#" onclick="setfankuan(1)">设为已返款</a> </span>
                    <span><a href="#" onclick="setfankuan(0)">设为未返款</a> </span>

                </div>
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

