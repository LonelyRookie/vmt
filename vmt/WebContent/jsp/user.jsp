<%@ page import="java.util.ArrayList" %>
<%@ page import="com.monitoring.constants.VMTConstants" %>
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
        <div class="content-header">
        </div>
        <form action="addUser/addUser.action?action=update" method="post" style="margin-top: 20px;">
            <s:token/>
        <center>
            <span style="color: red">${message}</span>
            <fieldset style="width: 40% ; background-color: #CFD2FA" align="center">
                <s:text name="lable.adduser.title"/>
                <table width="70%" align="center" style="display: table; border-collapse: separate;
                border-spacing: 2px; border-color: grey;">

                    <tbody>
                    <tr>
                        <td align="right" colspan="2"><b><font color="green"></font></b></td>
                    </tr>
                    <tr>
                        <td align="right"><b><s:text name="lable.adduser.isid"/></b></td>
                        <td align="left"><input type="text" name="isid" id="isid" maxlength="8"
                                                onchange="selectCountryByUser($(this).val())"
                                                onblur="" style="background:#FFFFFF"></td>
                    </tr>

                    <tr>
                        <td align="right"><b><s:text name="lable.adduser.country"/></b></td>
                        <td align="left">
                            <div id="defaultCountryChange"></div>
                            <div id="defaultCountry">
                                <left>
                                    <%
                                        ArrayList<?> countryList = (ArrayList<?>) session.getAttribute(VMTConstants.COUNTRY_LIST);
                                    %>
                                    <select name="country" id="country" multiple="multiple" size=${noOfRecords}>
                                        <%
                                            for (int i = 0; i < countryList.size(); i++) {
                                                out.print("<option value='" + countryList.get(i) + "'");
                                                out.println(">" + countryList.get(i) + "</option>");
                                            }
                                        %>
                                    </select>
                                    <left>
                                    </left>
                                </left>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td align="right"><b><s:text name="lable.adduser.role"/></b></td>
                        <td align="left">
                            <div id="defaulfRoleChange"></div>
                            <div id="defaulfRole">
                                <left>
                                    <select name="role" class="field-select">
                                        <option value="Administrator">ADMINISTRATOR</option>
                                        <option value="BI USer">BI USER</option>
                                        <option value="Country User">Country USER</option>
                                    </select>
                                    <left>
                                    </left>
                                </left>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><b><s:text name="lable.adduser.status"/></b></td>
                        <td align="left">
                            <div id="defaulfStatusChange"></div>
                            <div id="defaulfStatus">
                                <left>
                                    <select name="status" class="field-select">
                                        <option value="Active">Active</option>
                                        <option value="Inactive">Inactive</option>
                                    </select>
                                    <left>
                                    </left>
                                </left>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" align="center">
                            <div id="newUser">
                                <button type="submit">
                                    <s:text name="button.adduser"/>
                                </button>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </fieldset>
        </center>
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

    function selectCountryByUser(isid) {
        showLoading();
        $.ajax({
            url: "CountryList?ISID=" + isid,
            method: "post",
            dataType: "json",
            timeout: 30000,
            error: function (data) {
                console.error("get country by user failed!\n" + JSON.stringify(data));
                hideLoading();
            },
            success: function (data) {
                var options = "";
                for (var key in data.options) {
                    if (data.options.hasOwnProperty(key)) {
                        var selected = data.options[key].isSelected ? "selected" : "";
                        options += "<option value='" + data.options[key].country + "'" + selected +">" +
                            data.options[key].country + "</option>";
                    }
                }
                document.getElementById("country").innerHTML = options;
                hideLoading();
            }
        });
    }

    // Disable history back button
    history.pushState(null, null, location.href);
    window.onpopstate = function (event) {
        history.go(1);
    };
</script>
</body>
</html>