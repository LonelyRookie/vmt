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
                <b style="color: red; font-size: larger">${message}</b>
            </marquee>
        </div>
        <a href="javascript: doBack();" style="width:100px;margin: 0 auto;display:block;"><< Back</a>
        <s:if test="noOfRecords > 0">
            <table class="hovertable" style="margin-bottom: 20px" align="center" valign="center">
                <tr>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.wfname"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.sessionid"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.sessionname"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.successfulrows"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.failedrows"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.errorcode"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.errormsg"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.starttime"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.endtime"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.status"/></th>
                    <%--
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.loadname"/></th>--%>
                </tr>
                <s:iterator value="sessionLogTableList">
                    <tr onmouseover="this.style.backgroundColor='#ffff66';"
                        onmouseout="this.style.backgroundColor='#CFD2FA';">
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="wfName"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="sessionId"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="sessionName"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="successfulRows"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="failedRows"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="errorCode"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="errorMessage"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="startTime"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="endTime"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="status"/></b></td>
                        <%--
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="loadName"/></b></td>--%>
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

<form id="status" name="status" method="post" action="status/status.action" hidden>
    <input type="hidden" name="action" id="action">
    <input type="hidden" name="loadName" id="loadName">
    <input type="hidden" name="wfName" id="wfName">
    <input type="hidden" name="startDate">
    <input type="hidden" name="endDate">
    <input type="hidden" name="country">
    <input type="hidden" name="module">
    <input type="hidden" name="selectedLoadName">
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

    function logoutScreen() {
        window.location.href = "logout/logout.action";
    }

    function doBack() {
        showLoading();
        document.status.action = "status/status.action";
        if ("${isSearch}" === "true") {
            document.status.elements[0].value = "search";
            document.status.selectedLoadName.value = "${selectedLoadName}";
            document.status.startDate.value = "${startDate}";
            document.status.endDate.value = "${endDate}";
            document.status.country.value = "${country}";
            document.status.module.value = "${module}";
        } else {
            document.status.elements[0].value = "recSum";
            document.status.loadName.value = "${loadName}";
        }
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