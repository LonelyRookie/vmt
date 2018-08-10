<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
<jsp:include page="error.jsp">
    <jsp:param name="errMsg" value="Your token is invalid." />
</jsp:include>

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