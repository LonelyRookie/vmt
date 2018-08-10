<%@ page import="com.monitoring.constants.VMTConstants" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
    <title>Menu template Page</title>

    <%--jquery.mloading css--%>
    <link rel="stylesheet" href="css/jquery.mloading.css">

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

        .main-header .logo {
            height: 70px;
        }
    </style>
</head>
<body>
<s:i18n name="ApplicationResources">
    <header class="main-header">
        <!-- Logo -->
        <!-- Logo -->
        <div class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>VVM</b></span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>
                <img height="100%" width="100%" style="z-index: 100" src="css/Merck2.png"/>
            </b></span>
        </div>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top">

            <h3 class="elegantshadow1" align="right" width="10%" background-color="#000080 ">
                    <%--<ul class="nav navbar-nav navbar-right" style="padding-right: 20px;">--%>
                <%
                    if (session.getAttribute(VMTConstants.USER_ID) != null) {
                %>
                <s:text name="lable.success.page.welcome"/>
                <b>${userId}</b>
                <%
                    }
                %>
            </h3>
                <%--<ul/>--%>

            <h1 class="elegantshadow">VEEVA MONITORING</h1>

            <h3 class="elegantshadow1" align="right" width="10%" background-color="#000080 ">
                <div id="currentTime" align="right" position="absolute right">
                </div>
            </h3>
        </nav>
    </header>
    <!-- Left side column. contains the logo and sidebar -->
    <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <div class="sidebar">
            <!-- sidebar menu: : style can be found in sidebar.less -->
            <%
                if (session.getAttribute(VMTConstants.USER_ID) != null) {
            %>
            <ul class="nav sidebar-menu" style="margin-top: 20px;">
                <li class="active">
                    <a href="javascript: showStatus();"><span class="glyphicon glyphicon-stats"></span>VVM Status</a>
                </li>
                <%
                    boolean isAdmin = false;
                    if (session.getAttribute(VMTConstants.IS_ADMIN) != null) {
                        isAdmin = (Boolean) session.getAttribute(VMTConstants.IS_ADMIN);
                    }
                    if (isAdmin) {
                %>
                <li>
                    <a href="javascript: showUser();"><span class="glyphicon glyphicon-user"></span>Add/Update User</a>
                </li>
                <%
                    }
                %>
                <li>
                    <a href="javascript: showSearch();"><span class="glyphicon glyphicon-search"></span>History
                        Log</a>
                </li>
                <li>
                    <a href="javascript: showMapping();"><span class="glyphicon glyphicon-list"></span>Mapping
                        Details</a>
                </li>
                <li>
                    <a onclick="return confirm('Are you sure to logout?')" href="logout/logout.action">
                        <span class="glyphicon glyphicon-log-out"></span>
                        Logout
                    </a>
                </li>
            </ul>
            <%
                }
            %>
        </div>
        <!-- /.sidebar -->
    </aside>

</s:i18n>

<form hidden id="actionFrm" name="actionFrm" action="" method="post">
    <input type="hidden" name="action"/>
    <s:token/>
</form>

<%--jQuery 3.3.1--%>
<script src="js/jquery-3.3.1.min.js"></script>
<%--jquery.mloading js--%>
<script src="js/jquery.mloading.js"></script>
<%--Disable Right click--%>
<script src="js/norightclick.js"></script>
<%--Date Format Libary--%>
<script src="js/date.format.js"></script>

<script>
    $(function () {
        var now = new Date();
        var formattedDate = dateFormat(now, "dddd, mmmm dS, yyyy h:MM:ss TT");
        document.getElementById("currentTime").innerText = formattedDate;
    });

    function showStatus() {
        document.actionFrm.action = "status/status.action";
        document.actionFrm.elements[0].value = "ALL";
        document.actionFrm.submit();
    }

    function showUser() {
        document.actionFrm.action = "addUser/addUser.action";
        document.actionFrm.elements[0].value = "";
        document.actionFrm.submit();
    }

    function showSearch() {
        document.actionFrm.action = "status/status.action";
        document.actionFrm.elements[0].value = "search";
        document.actionFrm.submit();
    }

    function showMapping() {
        document.actionFrm.action = "mapping/mapping.action";
        document.actionFrm.elements[0].value = "";
        document.actionFrm.submit();
    }

    function showLoading() {
        $("body").mLoading({
            text: "loading..."
        });
    }

    function hideLoading() {
        $("body").mLoading("hide");
    }
</script>
</body>
</html>
