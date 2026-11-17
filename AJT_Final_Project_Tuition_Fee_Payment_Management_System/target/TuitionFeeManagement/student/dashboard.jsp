<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/common/header.jsp">
    <jsp:param name="title" value="Student Portal - Tuition Fee Management" />
</jsp:include>

<!-- Student Welcome & Profile Banner -->
<div class="card border-0 shadow-sm p-4 mb-4 bg-white rounded-3">
    <div class="row align-items-center">
        <div class="col-md-8">
            <div class="d-flex align-items-center mb-2">
                <div class="metric-icon bg-primary text-white me-3">
                    <i class="bi bi-mortarboard-fill"></i>
                </div>
                <div>
                    <h3 class="fw-bold mb-0">${student.user.name}</h3>
                    <span class="badge bg-secondary font-monospace">${student.rollNumber}</span>
                    <span class="badge bg-info-subtle text-dark ms-1">${student.course.courseName}</span>
                </div>
            </div>
            <p class="text-muted small mb-0 mt-2">
                <i class="bi bi-envelope me-1"></i> ${student.user.email} &bull; 
                <i class="bi bi-telephone me-1"></i> ${not empty student.user.phone ? student.user.phone : 'Not provided'} &bull; 
                <i class="bi bi-calendar3 me-1"></i> Admitted: ${student.admissionDate}
            </p>
        </div>
        <div class="col-md-4 text-md-end mt-3 mt-md-0">
            <c:choose>
                <c:when test="${totalBalance <= 0}">
                    <span class="badge badge-paid px-3 py-2 fs-6">
                        <i class="bi bi-check-circle-fill me-1"></i> Fees Fully Cleared
                    </span>
                </c:when>
                <c:otherwise>
                    <span class="badge badge-pending px-3 py-2 fs-6">
                        <i class="bi bi-exclamation-triangle-fill me-1"></i> Payment Due
                    </span>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<!-- Financial Summary Cards -->
<div class="row g-3 mb-4">
    <div class="col-md-4">
        <div class="card metric-card bg-white p-3">
            <span class="text-muted small fw-semibold">Total Assigned Fee</span>
            <h3 class="fw-bold my-1 text-dark">
                ₹<fmt:formatNumber value="${totalFee}" minFractionDigits="2" />
            </h3>
            <small class="text-muted">Total for your enrolled program</small>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card metric-card bg-white p-3">
            <span class="text-muted small fw-semibold">Total Amount Paid</span>
            <h3 class="fw-bold my-1 text-success">
                ₹<fmt:formatNumber value="${totalPaid}" minFractionDigits="2" />
            </h3>
            <small class="text-success"><i class="bi bi-shield-check me-1"></i> Confirmed</small>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card metric-card bg-white p-3">
            <span class="text-muted small fw-semibold">Outstanding Balance</span>
            <h3 class="fw-bold my-1 text-danger">
                ₹<fmt:formatNumber value="${totalBalance}" minFractionDigits="2" />
            </h3>
            <small class="text-danger">Remaining to be paid</small>
        </div>
    </div>
</div>

<!-- Fee Items / Due Periods Table -->
<div class="card table-card bg-white mb-4">
    <div class="card-header bg-white py-3">
        <h5 class="fw-bold mb-0 text-dark">
            <i class="bi bi-cash-stack text-primary me-2"></i> Assigned Tuition Fees
        </h5>
    </div>
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead>
                <tr>
                    <th>Academic Term</th>
                    <th>Due Date</th>
                    <th>Total Fee</th>
                    <th>Paid</th>
                    <th>Balance</th>
                    <th>Status</th>
                    <th class="text-center">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty assignments}">
                        <tr>
                            <td colspan="7" class="text-center text-muted py-4">No fee structures assigned yet.</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="fa" items="${assignments}">
                            <tr>
                                <td class="fw-semibold text-dark">${fa.academicPeriod}</td>
                                <td>${fa.dueDate}</td>
                                <td>₹<fmt:formatNumber value="${fa.totalFee}" minFractionDigits="2" /></td>
                                <td class="text-success fw-semibold">₹<fmt:formatNumber value="${fa.paidAmount}" minFractionDigits="2" /></td>
                                <td class="text-danger fw-bold">₹<fmt:formatNumber value="${fa.balanceAmount}" minFractionDigits="2" /></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${fa.paymentStatus eq 'PAID'}">
                                            <span class="badge badge-paid">PAID</span>
                                        </c:when>
                                        <c:when test="${fa.paymentStatus eq 'PARTIAL'}">
                                            <span class="badge badge-partial">PARTIAL</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-pending">PENDING</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-center">
                                    <c:choose>
                                        <c:when test="${fa.balanceAmount > 0}">
                                            <a href="${pageContext.request.contextPath}/student/pay?assignmentId=${fa.assignmentId}" 
                                               class="btn btn-success btn-sm px-3 fw-semibold">
                                                <i class="bi bi-credit-card me-1"></i> Pay Now
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="btn btn-outline-secondary btn-sm" disabled>
                                                <i class="bi bi-check2-all me-1"></i> Cleared
                                            </button>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<!-- Student Payment History -->
<div class="card table-card bg-white mb-4">
    <div class="card-header bg-white py-3">
        <h5 class="fw-bold mb-0 text-dark">
            <i class="bi bi-clock-history text-primary me-2"></i> Payment History &amp; Receipts
        </h5>
    </div>
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead>
                <tr>
                    <th>Receipt #</th>
                    <th>Payment Date</th>
                    <th>Academic Period</th>
                    <th>Amount Paid</th>
                    <th>Method</th>
                    <th>Status</th>
                    <th>Transaction Ref</th>
                    <th class="text-center">Receipt</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty payments}">
                        <tr>
                            <td colspan="8" class="text-center text-muted py-4">No payment transactions recorded yet.</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="p" items="${payments}">
                            <tr>
                                <td><span class="badge bg-light text-dark border font-monospace">${p.receiptNumber}</span></td>
                                <td>${p.paymentDate}</td>
                                <td>${p.feeAssignment.academicPeriod}</td>
                                <td class="text-success fw-bold">₹<fmt:formatNumber value="${p.amountPaid}" minFractionDigits="2" /></td>
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
                                    <a href="${pageContext.request.contextPath}/receipt?receiptNo=${p.receiptNumber}" 
                                       class="btn btn-outline-primary btn-sm">
                                        <i class="bi bi-printer me-1"></i> Print
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
