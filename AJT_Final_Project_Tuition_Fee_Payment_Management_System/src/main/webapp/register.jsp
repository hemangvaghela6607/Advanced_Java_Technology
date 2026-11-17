<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/common/header.jsp">
    <jsp:param name="title" value="Registration Disabled - Tuition Fee Management" />
</jsp:include>

<div class="row justify-content-center py-5">
    <div class="col-md-7 col-lg-6 text-center">
        <div class="card form-card p-5">
            <div class="text-primary mb-3">
                <i class="bi bi-person-lock display-3"></i>
            </div>
            <h3 class="fw-bold text-dark mb-2">Student Self-Registration Disabled</h3>
            <p class="text-muted mb-4">
                In accordance with institutional guidelines, student accounts and roll numbers are generated and assigned exclusively by the Administrator.
                Please contact the institute administration to receive your student portal credentials.
            </p>
            <div class="d-flex justify-content-center gap-2">
                <a href="${pageContext.request.contextPath}/login" class="btn btn-primary px-4">
                    <i class="bi bi-box-arrow-in-right me-1"></i> Proceed to Login
                </a>
                <a href="${pageContext.request.contextPath}/" class="btn btn-outline-secondary px-3">
                    <i class="bi bi-house-door me-1"></i> Home
                </a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/common/footer.jsp" />
