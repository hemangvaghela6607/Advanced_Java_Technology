<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/common/header.jsp">
    <jsp:param name="title" value="Home - Tuition Fee Payment Management System" />
</jsp:include>

<!-- Hero Section -->
<div class="row justify-content-center py-5">
    <div class="col-lg-9 text-center">
        <span class="badge bg-primary-subtle text-primary px-3 py-2 rounded-pill mb-3 fw-semibold">
            <i class="bi bi-mortarboard-fill me-1"></i> Tuition Fee Management
        </span>
        <h1 class="display-4 fw-bold text-dark mb-3">
            Tuition Fee Payment Management System
        </h1>
        <p class="lead text-muted mb-4 mx-auto" style="max-width: 700px;">
            A comprehensive institutional portal for managing student fee records, course structures, academic fee assignments, and simulated fee payments.
        </p>

        <div class="d-flex justify-content-center flex-wrap gap-3 mb-4">
            <c:choose>
                <c:when test="${not empty sessionScope.loggedInUser}">
                    <c:if test="${sessionScope.loggedInUser.admin}">
                        <a href="${pageContext.request.contextPath}/admin/dashboard" class="btn btn-primary btn-lg px-4">
                            <i class="bi bi-speedometer2 me-2"></i> Go to Admin Dashboard
                        </a>
                    </c:if>
                    <c:if test="${sessionScope.loggedInUser.student}">
                        <a href="${pageContext.request.contextPath}/student/dashboard" class="btn btn-success btn-lg px-4">
                            <i class="bi bi-speedometer2 me-2"></i> Go to Student Portal
                        </a>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-primary btn-lg px-5">
                        <i class="bi bi-box-arrow-in-right me-2"></i> Sign In to Portal
                    </a>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="card form-card p-3 mx-auto text-center" style="max-width: 540px;">
            <p class="text-muted small mb-0">
                <i class="bi bi-shield-check text-primary me-1"></i>
                Authorized Access Only. Student accounts, roll numbers, and login credentials are created and managed by the institute administration.
            </p>
        </div>
    </div>
</div>

<jsp:include page="/common/footer.jsp" />
