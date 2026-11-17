<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${param.title != null ? param.title : "Tuition Fee Payment Management System"}</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>

<!-- Navigation Bar -->
<nav class="navbar navbar-expand-lg navbar-dark navbar-custom sticky-top">
    <div class="container">
        <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/">
            <i class="bi bi-wallet2 text-primary me-2 fs-4"></i>
            <span>Tuition<span class="text-primary">Fee</span></span>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navMenu">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navMenu">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <c:choose>
                    <c:when test="${not empty sessionScope.loggedInUser and sessionScope.loggedInUser.admin}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/dashboard">
                                <i class="bi bi-speedometer2 me-1"></i> Dashboard
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/students">
                                <i class="bi bi-people me-1"></i> Students
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/courses">
                                <i class="bi bi-journal-bookmark me-1"></i> Courses
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/fee-assignments">
                                <i class="bi bi-cash-stack me-1"></i> Fee Assignments
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/payments">
                                <i class="bi bi-credit-card me-1"></i> Payments
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin/reports">
                                <i class="bi bi-graph-up me-1"></i> Reports
                            </a>
                        </li>
                    </c:when>
                    <c:when test="${not empty sessionScope.loggedInUser and sessionScope.loggedInUser.student}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/student/dashboard">
                                <i class="bi bi-speedometer2 me-1"></i> Dashboard
                            </a>
                        </li>
                    </c:when>
                </c:choose>
            </ul>

            <div class="d-flex align-items-center gap-2">
                <c:choose>
                    <c:when test="${not empty sessionScope.loggedInUser}">
                        <span class="navbar-text text-light me-2">
                            <i class="bi bi-person-circle text-primary me-1"></i>
                            <strong>${sessionScope.loggedInUser.name}</strong> 
                            <small class="badge bg-secondary ms-1">${sessionScope.loggedInUser.role}</small>
                        </span>
                        <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-danger btn-sm">
                            <i class="bi bi-box-arrow-right me-1"></i> Logout
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/login" class="btn btn-primary btn-sm px-3">
                            <i class="bi bi-box-arrow-in-right me-1"></i> Sign In
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav>

<!-- Alert Banner Include -->
<div class="container mt-3">
    <jsp:include page="/common/alert.jsp" />
</div>
<main class="container my-4 flex-grow-1">
