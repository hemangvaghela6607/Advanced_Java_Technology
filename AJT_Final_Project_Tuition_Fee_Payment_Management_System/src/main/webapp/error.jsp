<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/common/header.jsp">
    <jsp:param name="title" value="Error - Tuition Fee Payment Management System" />
</jsp:include>

<div class="row justify-content-center py-5">
    <div class="col-md-7 col-lg-6 text-center">
        <div class="card form-card p-5">
            <div class="text-danger mb-3">
                <i class="bi bi-exclamation-octagon display-1"></i>
            </div>
            <h3 class="fw-bold text-dark mb-2">Oops! Something went wrong</h3>
            <p class="text-muted mb-4">
                <c:choose>
                    <c:when test="${not empty errorMessage}">
                        ${errorMessage}
                    </c:when>
                    <c:when test="${not empty param.error}">
                        ${param.error}
                    </c:when>
                    <c:otherwise>
                        We encountered an unexpected problem processing your request. Please return to the portal or contact your system administrator.
                    </c:otherwise>
                </c:choose>
            </p>

            <div class="d-flex justify-content-center gap-2">
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary px-4">
                    <i class="bi bi-house-door-fill me-1"></i> Return Home
                </a>
                <c:if test="${not empty sessionScope.loggedInUser and sessionScope.loggedInUser.admin}">
                    <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-outline-secondary px-3">
                        <i class="bi bi-speedometer2 me-1"></i> Admin Dashboard
                    </a>
                </c:if>
                <c:if test="${not empty sessionScope.loggedInUser and sessionScope.loggedInUser.student}">
                    <a href="${pageContext.request.contextPath}/student/dashboard" class="btn btn-outline-secondary px-3">
                        <i class="bi bi-speedometer2 me-1"></i> Student Dashboard
                    </a>
                </c:if>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/common/footer.jsp" />
