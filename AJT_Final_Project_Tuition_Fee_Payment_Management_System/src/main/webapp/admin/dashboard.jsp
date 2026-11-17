<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/common/header.jsp">
    <jsp:param name="title" value="Admin Dashboard - Tuition Fee Management" />
</jsp:include>

<!-- Page Title & Quick Actions -->
<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h2 class="fw-bold mb-1">Administrative Dashboard</h2>
        <p class="text-muted small mb-0">Overview of institutional revenue, student enrollments, and payment activities</p>
    </div>
    <div class="d-flex gap-2">
        <a href="${pageContext.request.contextPath}/admin/students?action=new" class="btn btn-primary btn-sm">
            <i class="bi bi-person-plus-fill me-1"></i> Add Student
        </a>
        <a href="${pageContext.request.contextPath}/admin/fee-assignments" class="btn btn-success btn-sm">
            <i class="bi bi-cash-stack me-1"></i> Assign Fee
        </a>
    </div>
</div>

<!-- 4 Key Analytics Cards -->
<div class="row g-3 mb-4">
    <div class="col-sm-6 col-xl-3">
        <div class="card metric-card bg-white p-3">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <span class="text-muted small fw-semibold">Total Students</span>
                    <h3 class="fw-bold my-1 text-dark">${totalStudents}</h3>
                    <small class="text-success"><i class="bi bi-person-check-fill me-1"></i> Registered</small>
                </div>
                <div class="metric-icon bg-primary-subtle text-primary">
                    <i class="bi bi-people-fill"></i>
                </div>
            </div>
        </div>
    </div>

    <div class="col-sm-6 col-xl-3">
        <div class="card metric-card bg-white p-3">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <span class="text-muted small fw-semibold">Total Courses</span>
                    <h3 class="fw-bold my-1 text-dark">${totalCourses}</h3>
                    <small class="text-primary"><i class="bi bi-book-fill me-1"></i> Active Programs</small>
                </div>
                <div class="metric-icon bg-info-subtle text-info">
                    <i class="bi bi-journal-bookmark-fill"></i>
                </div>
            </div>
        </div>
    </div>

    <div class="col-sm-6 col-xl-3">
        <div class="card metric-card bg-white p-3">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <span class="text-muted small fw-semibold">Total Fees Paid</span>
                    <h3 class="fw-bold my-1 text-success">
                        ₹<fmt:formatNumber value="${totalCollected}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                    </h3>
                    <small class="text-success"><i class="bi bi-arrow-up-right me-1"></i> Collected</small>
                </div>
                <div class="metric-icon bg-success-subtle text-success">
                    <i class="bi bi-wallet-fill"></i>
                </div>
            </div>
        </div>
    </div>

    <div class="col-sm-6 col-xl-3">
        <div class="card metric-card bg-white p-3">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <span class="text-muted small fw-semibold">Pending Balance</span>
                    <h3 class="fw-bold my-1 text-danger">
                        ₹<fmt:formatNumber value="${totalPending}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                    </h3>
                    <small class="text-danger"><i class="bi bi-clock-history me-1"></i> Outstanding</small>
                </div>
                <div class="metric-icon bg-danger-subtle text-danger">
                    <i class="bi bi-exclamation-circle-fill"></i>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Recent Transactions Table -->
<div class="card table-card bg-white mb-4">
    <div class="card-header bg-white py-3 d-flex justify-content-between align-items-center">
        <h5 class="fw-bold mb-0 text-dark">
            <i class="bi bi-clock-history text-primary me-2"></i> Recent Payment Transactions
        </h5>
        <a href="${pageContext.request.contextPath}/admin/payments" class="btn btn-outline-primary btn-sm">
            View All Payments <i class="bi bi-arrow-right"></i>
        </a>
    </div>
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead>
                <tr>
                    <th>Receipt #</th>
                    <th>Student Name</th>
                    <th>Roll Number</th>
                    <th>Course</th>
                    <th>Amount Paid</th>
                    <th>Date</th>
                    <th>Status</th>
                    <th class="text-center">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty recentPayments}">
                        <tr>
                            <td colspan="8" class="text-center text-muted py-4">No payment records found.</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="p" items="${recentPayments}">
                            <tr>
                                <td><span class="badge bg-light text-dark border font-monospace">${p.receiptNumber}</span></td>
                                <td class="fw-semibold">${p.feeAssignment.student.user.name}</td>
                                <td>${p.feeAssignment.student.rollNumber}</td>
                                <td>${p.feeAssignment.student.course.courseName}</td>
                                <td class="text-success fw-bold">
                                    ₹<fmt:formatNumber value="${p.amountPaid}" minFractionDigits="2" />
                                </td>
                                <td>${p.paymentDate}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${p.paymentStatus eq 'PAID'}">
                                            <span class="badge badge-paid">PAID</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-partial">${p.paymentStatus}</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-center">
                                    <a href="${pageContext.request.contextPath}/receipt?receiptNo=${p.receiptNumber}" 
                                       class="btn btn-outline-secondary btn-sm" title="Print/View Receipt">
                                        <i class="bi bi-receipt"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="/common/footer.jsp" />
