<%-- 
    Document   : error
    Created on : 22 Sept 2026, 7:49:51?pm
    Author     : ADMIN
--%>


<%@ page isErrorPage="true" %>
<html>
<head>
    <title>Error Page</title>
</head>
<body>
    <h2 style="color:red;">Oops! Something went wrong.</h2>
    <p><b>Error Details:</b> <%= exception %></p>
    <br>
    <a href="index.html">Go Back</a>
</body>
</html>