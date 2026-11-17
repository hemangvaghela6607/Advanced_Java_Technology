<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/common/header.jsp">
    <jsp:param name="title" value="Payment History - Student" />
</jsp:include>

<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h2 class="fw-bold mb-1">My Payment History</h2>
        <p class="text-muted small mb-0">Record of all past tuition fee transactions and electronic receipts</p>
    </div>
    <a href="${pageContext.request.contextPath}/student/dashboard" class="btn btn-outline-secondary btn-sm">
        <i class="bi bi-arrow-left me-1"></i> Back to Dashboard
    </a>
</div>

<div class="card table-card bg-white">
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead>
                <tr>
                    <th>Receipt #</th>
                    <th>Payment Date</th>
                    <th>Academic Period</th>
                    <th>Amount Paid</th>
                    <th>Payment Mode</th>
                    <th>Status</th>
                    <th>Transaction Reference</th>
                    <th class="text-center">Receipt</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty payments}">
                        <tr>
                            <td colspan="8" class="text-center text-muted py-4">No payment records found.</td>
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
                                        <i class="bi bi-printer me-1"></i> Print Receipt
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
