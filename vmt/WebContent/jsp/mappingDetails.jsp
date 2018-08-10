<%@ page import="com.monitoring.constants.VMTConstants" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
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

    <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css">

    <!-- Font Awesome -->
    <!--<link rel="stylesheet" href="css/font-awesome.min.css">-->
    <!-- Ionicons -->
    <!--<link rel="stylesheet" href="css/ionicons.min.css">-->
    <!-- Theme style -->
    <link rel="stylesheet" href="css/AdminLTE.min.css">
    <link rel="stylesheet" type="text/css" href="css/style_table.css"/>
    <style>
        .content-wrapper {
            min-height: 500px;
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
        </div>

        <div class="row">
            <form action="mapping/mapping.action" method="post">
                <s:token/>
                <input type="hidden" name="action" value="search" />
                <div class='col-sm-3'></div>
                <div class='col-sm-2'>
                    <label><s:text name="lable.dtable.country"/></label>
                    <select name="country" id="country" class="form-control" onchange="popCountry($(this).val());">
                        <option value='' disabled selected style='display:none;'>
                            <s:text name="lable.dtable.selectcountry"/>
                        </option>

                        <%
                            ArrayList<?> countryList = (ArrayList<?>) request.getAttribute(VMTConstants.COUNTRY_LIST);
                            String selectedCountry = (String)request.getAttribute("country");

                            for (int i = 0; i < countryList.size(); i++) {
                                out.print("<option value='" + countryList.get(i) + "'");
                                if (countryList.get(i).equals(selectedCountry)) {
                                    out.print("selected");
                                }
                                out.println(">" + countryList.get(i) + "</option>");
                            }
                        %>
                    </select>
                </div>
                <div class='col-sm-2'>
                    <div class="form-group">
                        <label><s:text name="lable.dtable.object"/></label>
                        <select name="module" id="module" class="form-control" required>
                            <option value='' disabled selected style='display:none;'>
                                <s:text name="lable.dtable.selectobject"/>
                            </option>
                        </select>
                    </div>
                </div>
                <div class='col-sm-2'>
                    <button type="submit" class="btn btn-success" style="margin-top: 25px"
                            onclick="if($('#module').val()){showLoading();}">
                        <s:text name="lable.dtable.searchresult"/>
                    </button>
                </div>
            </form>
        </div>

        <s:if test="noOfRecords > 0">
            <table class="hovertable" style="margin-bottom: 20px" align="center" valign="center">
                <tr>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.tablename"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.dbcolumnname"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.datatype"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.sfcolumnname"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.sffieldmodtype"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.operation"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.createdate"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.modifieddate"/></th>
                    <th bgcolor="#3C574B" style="color: #ffffff;"><s:text name="lable.dtable.isinclude"/></th>
                </tr>
                <s:iterator value="auditSfdcBeanList">
                    <tr onmouseover="this.style.backgroundColor='#ffff66';"
                        onmouseout="this.style.backgroundColor='#CFD2FA';">
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="tableName"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="dbColumnName"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="dataType"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="sfColumnName"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="sfFieldModType"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="operation"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="createDate"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="modifiedDate"/></b></td>
                        <td nowrap="nowrap" style="color: <s:property value="colorCode"/>"><b><s:property
                                value="isInclude"/></b></td>
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

    $(function () {
        if ("${country}" !== "") {
            showLoading();
            selectCountry("${country}", "${module}", hideLoading);
        }
    });

    function popCountry(country) {
        showLoading();
        selectCountry(country, "", hideLoading);
    }

    function selectCountry(country, selected, callback) {
        document.getElementById("module").innerHTML = "<option value=\"\" disabled=\"\" selected=\"\" " +
            "style=\"display:none;\"><s:text name="lable.dtable.selectobject"/></option>";
        $.ajax({
            url: "SfApiName?country=" + country,
            method: "post",
            dataType: "json",
            timeout: 30000,
            error: function (data) {
                console.error("get sf api name list failed!\n" + JSON.stringify(data));
                callback(this);
            },
            success: function (data) {
                var options = "";
                for (var key in data.names) {
                    if (data.names.hasOwnProperty(key)) {
                        // console.log(data.names[key]);
                        var selectedTxt = "";
                        if (selected === data.names[key]) {
                            selectedTxt = "selected";
                        }
                        options += "<option value='" + data.names[key] + "'" + selectedTxt + ">" +
                            data.names[key] + "</option>";
                    }
                }
                document.getElementById("module").innerHTML += options;
                callback(this);
            }
        });
    }

    // Disable history back button
    history.pushState(null, null, location.href);
    window.onpopstate = function (event) {
        history.go(1);
    };
</script>
</s:i18n>
</body>
</html>