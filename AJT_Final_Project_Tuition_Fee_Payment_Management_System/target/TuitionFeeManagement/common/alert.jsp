<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${not empty param.msg or not empty requestScope.successMessage}">
    <div class="alert alert-success alert-dismissible show" role="alert">
        <i class="bi bi-check-circle-fill me-2"></i>
        ${not empty param.msg ? param.msg : requestScope.successMessage}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<c:if test="${not empty param.error or not empty requestScope.errorMessage}">
    <div class="alert alert-danger alert-dismissible show" role="alert">
        <i class="bi bi-exclamation-triangle-fill me-2"></i>
        ${not empty param.error ? param.error : requestScope.errorMessage}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>
