<%-- 
    Document   : jstldemo
    Created on : 24 Sept 2026, 9:20:36 pm
    Author     : ADMIN
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <title>JSTL Tags Demo</title>
</head>
<body>

    <h2>1. JSTL Core Tags (c:out, c:set, c:remove, c:import)</h2>
    <hr>

    <%-- c:set: Setting a variable in the page scope --%>
    <c:set var="myGreeting" value="Hello, Marwadi University"/>
    
    <%-- c:out: Displaying the variable's value --%>
    <p>Display c:out variable: <strong><c:out value="${myGreeting}"/></strong></p>

    <%-- c:import: Importing a small snippet from another file --%>
    <!--<h4>Content imported from another file (`footer.jsp`):</h4>-->
    <c:import url="footer.jsp"/>

    <%-- c:remove: Removing the variable created earlier --%>
    <c:remove var="myGreeting"/>
    
    <%-- Demonstrating the variable is now removed (it will display nothing or an empty string) --%>
    <!--<p>After using c:remove, the variable myGreeting is now: **<c:out value="${myGreeting}"/>**</p>-->
    
    <br>

    <h2>2. JSTL Function Tags (fn:toLowerCase, fn:toUpperCase, fn:substring, fn:replace)</h2>
    <hr>

    <c:set var="originalString" value="Marwadi University"/>
    <h3>Original String: "${originalString}"</h3>
    
    <p>Using fn:toLowerCase: <c:out value="${fn:toLowerCase(originalString)}"/></p>
    <p>Using fn:toUpperCase: <c:out value="${fn:toUpperCase(originalString)}"/></p>
    
    <p>Using fn:substring to get a part of the string (from index 4 to 9): <c:out value="${fn:substring(originalString, 4, 9)}"/></p>
    
    <p>Using fn:replace to replace "Brown" with "Red": <c:out value="${fn:replace(originalString, 'Brown', 'Red')}"/></p>

</body>
</html>
