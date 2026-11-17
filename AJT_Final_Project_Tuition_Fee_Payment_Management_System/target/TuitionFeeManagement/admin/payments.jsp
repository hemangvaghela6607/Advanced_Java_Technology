<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/common/header.jsp">
    <jsp:param name="title" value="Payment Records - Admin" />
</jsp:include>

<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h2 class="fw-bold mb-1">Fee Payment Transactions</h2>
        <p class="text-muted small mb-0">Search, filter, and audit all student tuition payments and transaction records</p>
    </div>
</div>

<!-- Filter Toolbar -->
<div class="card border-0 shadow-sm p-3 mb-4 bg-white rounded-3">
    <form action="${pageContext.request.contextPath}/admin/payments" method="get" class="row g-2 align-items-end">
        <div class="col-md-3">
            <label class="form-label small fw-semibold text-secondary mb-1">Filter by Course</label>
            <select name="courseId" class="form-select form-select-sm">
                <option value="">-- All Courses --</option>
                <c:forEach var="c" items="${courses}">
                    <option value="${c.courseId}" ${selectedCourseId != null && selectedCourseId == c.courseId ? 'selected' : ''}>
                        ${c.courseName}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="col-md-3">
            <label class="form-label small fw-semibold text-secondary mb-1">Payment Status</label>
            <select name="status" class="form-select form-select-sm">
                <option value="ALL" ${selectedStatus eq 'ALL' ? 'selected' : ''}>-- All Statuses --</option>
                <option value="PAID" ${selectedStatus eq 'PAID' ? 'selected' : ''}>PAID</option>
                <option value="PARTIAL" ${selectedStatus eq 'PARTIAL' ? 'selected' : ''}>PARTIAL</option>
            </select>
        </div>

        <div class="col-md-4">
            <label class="form-label small fw-semibold text-secondary mb-1">Search Keyword</label>
            <input type="text" name="keyword" class="form-control form-control-sm" 
                   value="${keyword != null ? keyword : ''}" 
                   placeholder="Roll #, Student Name, Receipt #">
        </div>

        <div class="col-md-2 d-flex gap-2">
            <button type="submit" class="btn btn-primary btn-sm w-100 fw-semibold">
                <i class="bi bi-search me-1"></i> Filter
            </button>
            <a href="${pageContext.request.contextPath}/admin/payments" class="btn btn-outline-secondary btn-sm">
                Reset
            </a>
        </div>
    </form>
</div>

<!-- Payments Table -->
<div class="card table-card bg-white">
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead>
                <tr>
                    <th>Receipt #</th>
                    <th>Student Name</th>
                    <th>Roll Number</th>
                    <th>Course</th>
                    <th>Amount Paid</th>
                    <th>Date &amp; Time</th>
                    <th>Payment Method</th>
                    <th>Status</th>
                    <th>Transaction Ref</th>
                    <th class="text-center">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty payments}">
                        <tr>
                            <td colspan="10" class="text-center text-muted py-4">No matching payment transactions found.</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="p" items="${payments}">
                            <tr>
                                <td>
                                    <span class="badge bg-light text-dark border font-monospace">
                                        ${p.receiptNumber}
                                    </span>
                                </td>
                                <td class="fw-semibold text-dark">${p.feeAssignment.student.user.name}</td>
                                <td>${p.feeAssignment.student.rollNumber}</td>
                                <td>${p.feeAssignment.student.course.courseName}</td>
                                <td class="text-success fw-bold">
                                    ₹<fmt:formatNumber value="${p.amountPaid}" minFractionDigits="2" />
                                </td>
                                <td>${p.paymentDate}</td>
                                <td><span class="badge bg-secondary-subtle text-secondary">${p.paymentMethod}</span></td>
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
                                <td><small class="font-monospace text-muted">${p.transactionReference}</small></td>
                                <td class="text-center">
                                    <div class="btn-group btn-group-sm">
                                        <a href="${pageContext.request.contextPath}/receipt?receiptNo=${p.receiptNumber}" 
                                           class="btn btn-outline-secondary" title="View / Print Receipt">
                                            <i class="bi bi-printer"></i>
                                        </a>
                                        <c:if test="${p.paymentStatus eq 'PARTIAL'}">
                                            <form action="${pageContext.request.contextPath}/admin/payments" method="post" class="d-inline">
                                                <input type="hidden" name="action" value="updateStatus">
                                                <input type="hidden" name="paymentId" value="${p.paymentId}">
                                                <input type="hidden" name="newStatus" value="PAID">
                                                <button type="submit" class="btn btn-success" 
                                                        onclick="return confirm('Update payment status of receipt ${p.receiptNumber} to PAID?');" 
                                                        title="Mark as PAID">
                                                    <i class="bi bi-check2-circle"></i> Mark PAID
                                                </button>
                                            </form>
                                        </c:if>
                                    </div>
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
