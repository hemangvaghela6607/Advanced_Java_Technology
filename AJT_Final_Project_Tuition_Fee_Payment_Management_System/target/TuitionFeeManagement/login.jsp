<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/common/header.jsp">
    <jsp:param name="title" value="Login - Tuition Fee Payment Management System" />
</jsp:include>

<div class="row justify-content-center py-4">
    <div class="col-md-6 col-lg-5">
        <div class="card form-card p-4">
            <div class="text-center mb-4">
                <div class="metric-icon bg-primary text-white mx-auto mb-3">
                    <i class="bi bi-shield-lock-fill"></i>
                </div>
                <h3 class="fw-bold">Sign In</h3>
                <p class="text-muted small mb-0">Access your tuition fee management portal</p>
            </div>

            <form action="${pageContext.request.contextPath}/login" method="post" id="loginForm" autocomplete="off">
                <div class="mb-3">
                    <label for="email" class="form-label fw-semibold">Email Address</label>
                    <div class="input-group">
                        <span class="input-group-text bg-light"><i class="bi bi-envelope"></i></span>
                        <input type="email" class="form-control" id="email" name="email" 
                               value="${enteredEmail != null ? enteredEmail : ''}" 
                               placeholder="name@example.com" autocomplete="off" required autofocus>
                    </div>
                </div>

                <div class="mb-4">
                    <label for="password" class="form-label fw-semibold">Password</label>
                    <div class="input-group">
                        <span class="input-group-text bg-light"><i class="bi bi-key"></i></span>
                        <input type="password" class="form-control" id="password" name="password" 
                               placeholder="Enter your password" autocomplete="new-password" required>
                    </div>
                </div>

                <div class="d-grid mb-3">
                    <button type="submit" class="btn btn-primary py-2 fw-semibold">
                        <i class="bi bi-box-arrow-in-right me-1"></i> Sign In
                    </button>
                </div>

                <div class="border-top pt-3 mt-3 text-center small text-muted">
                    <i class="bi bi-info-circle me-1"></i>
                    Student accounts and login credentials are provided directly by the Institute Administrator.
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/common/footer.jsp" />
