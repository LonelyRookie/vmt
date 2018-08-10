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

        String errorMessage = "";
        if (request.getAttribute("errMsg") != null) {
            errorMessage = (String)request.getAttribute("errMsg");
        } else if (request.getParameter("errMsg") != null) {
            errorMessage = request.getParameter("errMsg");
        } else {
            errorMessage = "Unknown issue occurs.";
        }
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
</head>

<body class="skin-blue fixed">
<div class="wrapper">
    <jsp:include page="menu.jsp"/>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <div class="content-header">
        </div>

        <div style="text-align: center; margin-top: 30px">
            <b style="color: #ACACAC; font-style: italic; font-size: 30px;"><%=errorMessage%></b>
        </div>
        <br/>
        <br/>
        <a href="#">Click Here to login again</a>
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
<!-- Bootstrap 4.1.1 -->
<script src="js/bootstrap.min.js"></script>
<%--Disable Right click--%>
<script src="js/norightclick.js"></script>

<script type="application/javascript">
    // Framekiller JavaScript.
    if(top != self) top.location.replace(location);

    // Disable history back button
    history.pushState(null, null, location.href);
    window.onpopstate = function (event) {
        history.go(1);
    };
</script>
</body>
</html>