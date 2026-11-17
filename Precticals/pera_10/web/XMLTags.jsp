<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>  

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>JSTL XML Tags</title>
</head>
<body>

<h2>JSTL XML Tags</h2>

<h3>1. x:parse and x:out</h3>

<%-- It loads the contents of: books.xml and stores the result in: bookInfo --%> 
<c:import var="bookInfo" url="books.xml" />

<%-- x:parse takes the XML content and parses it into an XML document structure that JSTL XML tags can work with --%> 
<x:parse xml="${bookInfo}" var="output" />
 <p>First Book title: <x:out select="$output/books/book[1]/name" /></p>
 <p>First Book price: <x:out select="$output/books/book[1]/price" /></p>
 <p>Second Book title: <x:out select="$output/books/book[2]/name" /></p>
 <p>Second Book price: <x:out select="$output/books/book[2]/price" /></p>
 
 <br><br>
<h3>2. x:forEach</h3>
<ul>
<x:forEach select="$output/books/book" var="book">
    <li>
        Book Name:
        <x:out select="$book/name" />
        | Author:
        <x:out select="$book/author" />
        | Price:
        <x:out select="$book/price" />
    </li>
</x:forEach>
</ul>
</body>
</html>