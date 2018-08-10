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

    <link rel="icon" href="css/favicon.jpg" type="image/x-icon"/>
    <link rel="shortcut icon" href="css/favicon.jpg" type="image/x-icon"/>

    <!-- Bootstrap 4.1.1 -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <!-- Font Awesome -->
    <!--<link rel="stylesheet" href="css/font-awesome.min.css">-->
    <!-- Ionicons -->
    <!--<link rel="stylesheet" href="css/ionicons.min.css">-->
    <!-- Theme style -->
    <link rel="stylesheet" href="css/AdminLTE.min.css">
    <link rel="stylesheet" type="text/css" href="css/style_table.css"/>
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
    </style>
</head>

<body class="skin-blue fixed">
<div class="wrapper">
    <jsp:include page="menu.jsp"/>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <div class="content-header" style="margin: 20px 0 15px 0;">
            <marquee behavior="scroll" direction="ltr">
                <b style="color: red; font-size: larger">${message}</b>
            </marquee>
        </div>
        <a href="javascript: doBack();" style="width:100px;margin: 0 auto;display:block;"><< Back</a>
        <s:if test="noOfRecords > 0">
            <table class="hovertable" style="margin-bottom: 20px" align="center" valign="center">
                <tr>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.loadname"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.object"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.wfname"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.starttime"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.endtime"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.status"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.sessionloglink"/></th>
                        <%--
                        <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.country"/></th>
                        <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.loadyype"/></th>
                        <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.wfordernum"/></th>--%>

                </tr>
                <s:iterator value="recSummaryList">
                    <tr onmouseover="this.style.backgroundColor='#ffff66';"
                        onmouseout="this.style.backgroundColor='#CFD2FA';">
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="loadName"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="module"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="wfName"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="startDate"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="endDate"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="status"/></b></td>
                        <td nowrap="nowrap"><a href="javascript:void(0)"
                                               style="display: <s:property value="display"/>;"
                                               onclick="viewSessionLog('<s:property value="loadName"/>' ,
                                                       '<s:property value="wfName"/>')">View</a></td>
                            <%--
                            <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                    value="country"/></b></td>
                            <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                    value="loadType"/></b></td>

                            <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                    value="wfOrderNum"/></b></td>--%>
                    </tr>
                </s:iterator>
            </table>
        </s:if>
        <s:elseif test="noOfRecords == 0">
            <b>Nothing found to Display...</b>
        </s:elseif>
    </div>
    <!-- /.content -->
</div>
<!-- ./wrapper -->
</s:i18n>

<form id="status" name="status" method="post" action="" hidden>
    <input type="hidden" name="action" id="action">
    <input type="hidden" name="loadName" id="loadName">
    <input type="hidden" name="wfName" id="wfName">
    <s:token/>
</form>

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

    function viewSessionLog(loadName, wfName) {
        showLoading();
        document.status.action = "status/status.action";
        document.status.elements[0].value = "sessionLog";
        document.status.loadName.value = loadName;
        document.status.wfName.value = wfName;
        document.status.submit();
    }

    function doBack() {
        showLoading();
        document.status.action = "status/status.action";
        document.status.elements[0].value = "ALL";
        document.status.loadName.value = "";
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