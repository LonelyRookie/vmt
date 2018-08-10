<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <s:i18n name="ApplicationResources">
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName();
        basePath = basePath + ":" + request.getServerPort() + path +"/";
    %>
    <base href="<%=basePath%>"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><s:text name="title"/></title>

    <link rel="icon" href="css/favicon.jpg" type="image/x-icon"/>
    <link rel="shortcut icon" href="css/favicon.jpg" type="image/x-icon"/>
    <!-- Custom Stylesheet -->
    <link rel="stylesheet" href="css/style.css">

    <div>
        <img src="css/Merck2.png" alt="Merck" height="62" width="252" align="right"/>

        <style>
            body {
                background-image: url(css/monitoring3.jpg);
            }

            .geekatom-input-row label {
                line-height: 1.1;
                float: left;
                width: 20%;
                padding: 11px 15px;
            }
        </style>
        </head>
<body>
<s:form namespace="/login" action="login" focus="userName" method="POST">
<s:token/>
<div class="container">
    <div class="top">
        <h1 id="title" class="hidden"><span id="logo"><s:text name="title"/></span></h1>
    </div>
    <div class="login-box animated fadeInUp">
        <div class="box-header">
            <h2><s:text name="lable.login.page.title"/></h2>
        </div>
        <div class="geekatom-input-row">
            <label style="text-align: right;"><s:text name="lable.login.isid"/></label>
            <input type="text" name="userName" id="userName" required="required" autocomplete="off">
        </div>
        <div class="geekatom-input-row">
            <label style="text-align: right;"><s:text name="lable.login.pwd"/></label>
            <input type="password" name="password" id="password" required="required" autocomplete="off">
        </div>
        <br>
        <button type="submit">
            <s:text name="button.login"/>
        </button>
        </s:form>

        </s:i18n>
        <div style="position:fixed;bottom:0;">
            <jsp:include page="copyright.jsp"/>
        </div>

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