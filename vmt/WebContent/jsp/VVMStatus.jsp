<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<html>
<head>
    <s:i18n name="ApplicationResources">
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName();
        basePath = basePath + ":" + request.getServerPort() + path + "/";
    %>
    <base href="<%=basePath%>"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title><s:text name="title"/></title>

    <link rel="icon" href="css/favicon.jpg" type="image/x-icon" />
    <link rel="shortcut icon" href="css/favicon.jpg" type="image/x-icon" />

    <!-- Bootstrap 4.1.1 -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <!-- Font Awesome -->
    <!--<link rel="stylesheet" href="css/font-awesome.min.css">-->
    <!-- Ionicons -->
    <!--<link rel="stylesheet" href="css/ionicons.min.css">-->
    <!-- Theme style -->
    <link rel="stylesheet" href="css/AdminLTE.min.css">
    <link rel="stylesheet" type="text/css" href="css/style_table.css" />
    <style>
        h3.elegantshadow1 {
            color: #FFFFFF;
            background-color: #000080;
            letter-spacing: .15em;
            font-size: 15px;
            margin: 0px;
        }

        h1.elegantshadow {
            color: #FFFFFF;
            background-color: #000080;
            letter-spacing: .15em;
            margin: 0px;
            text-align: center;
        }

        .center {
        }
    </style>
</head>

<body class="skin-blue fixed">
<div class="wrapper">
    <jsp:include page="menu.jsp" />

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <div class="content-header" style="margin: 20px 0 15px 0;">
            <marquee behavior="scroll" direction="ltr">
                <b style="color: red; font-size: larger">Note: The results are fetched runtime from country specific
                    log tables</b>
            </marquee>
        </div>

        <s:if test="noOfRecords > 0">
            <table id="datatable" class="hovertable center" style="margin-bottom: 20px" align="center" valign="center">
                <tr>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.country"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.startdate"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.enddate"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.timeelapsed"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.durationofsync"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.avgtime"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.status"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.recsumlink"/></th>
                </tr>
                <s:iterator value="statusList">
                    <tr onmouseover="this.style.backgroundColor='#ffff66';"
                        onmouseout="this.style.backgroundColor='#CFD2FA';">
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="country"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="startDate"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="endDate"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="timeElapsed"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="durationOfSync"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="avgTime"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="status"/></b></td>
                        <td nowrap="nowrap"><a href="javascript:void(0)"
                                               style="display: <s:property value="display"/>;"
                                               onclick="recSum('<s:property value="loadName"/>')">View</a></td>
                    </tr>
                </s:iterator>
            </table>
        </s:if>
        <s:else>
            <b>Nothing found to Display...</b>
        </s:else>

        <form id="status" name="status" method="post" action="status/status.action" hidden>
            <input type="hidden" name="action" id="action">
            <input type="hidden" name="loadName" id="loadName">
            <input type="hidden" name="wfName" id="wfName">
            <s:token/>
        </form>
    </div>
    <!-- /.content -->
</div>
<!-- ./wrapper -->
</s:i18n>
<div style="position:fixed;bottom:0;">
    <jsp:include page="copyright.jsp"/>
</div>

<!-- jQuery 3.3.1 -->
<script src="js/jquery-3.3.1.min.js"></script>
<%--jquery.mloading js--%>
<script src="js/jquery.mloading.js"></script>
<!-- Bootstrap 4.1.1 -->
<script src="js/bootstrap.min.js"></script>
<%--Disable Right click--%>
<script src="js/norightclick.js"></script>

<script>
    // Framekiller JavaScript.
    if(top != self) top.location.replace(location);

    function recSum(loadname) {
        showLoading();
        document.status.action = "status/status.action";
        document.status.elements[0].value = "recSum";
        document.status.loadName.value = loadname;
        document.status.wfName.value = "";
        document.status.submit();
    }

    // Disable history back button
    history.pushState(null, null, location.href);
    window.onpopstate = function (event) {
        history.go(1);
    };
</script>
</body>
</html>