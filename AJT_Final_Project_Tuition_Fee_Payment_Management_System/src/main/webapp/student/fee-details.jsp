<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/common/header.jsp">
    <jsp:param name="title" value="My Fee Details - Student" />
</jsp:include>

<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h2 class="fw-bold mb-1">Fee Structures &amp; Balances</h2>
        <p class="text-muted small mb-0">Detailed breakdown of assigned fees, payments made, and due dates</p>
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
                    <th>Academic Period</th>
                    <th>Due Date</th>
                    <th>Remarks</th>
                    <th>Total Fee</th>
                    <th>Paid Amount</th>
                    <th>Balance Due</th>
                    <th>Status</th>
                    <th class="text-center">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty assignments}">
                        <tr>
                            <td colspan="8" class="text-center text-muted py-4">No fee structures assigned yet.</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="fa" items="${assignments}">
                            <tr>
                                <td class="fw-semibold text-dark">${fa.academicPeriod}</td>
                                <td>${fa.dueDate}</td>
                                <td class="text-muted small">${not empty fa.remarks ? fa.remarks : 'N/A'}</td>
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
                                            <span class="badge bg-light text-success border">Cleared</span>
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

<jsp:include page="/common/footer.jsp" />
