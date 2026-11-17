<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/common/header.jsp">
    <jsp:param name="title" value="Tuition Fee Checkout (Simulated) - Student" />
</jsp:include>

<div class="row justify-content-center py-4">
    <div class="col-md-8 col-lg-6">
        <div class="card form-card p-4">
            <div class="text-center mb-4">
                <div class="metric-icon bg-success text-white mx-auto mb-3">
                    <i class="bi bi-credit-card-2-front-fill"></i>
                </div>
                <h3 class="fw-bold">Fee Payment Checkout</h3>
                <p class="text-muted small">Simulated Institutional Fee Payment Gateway</p>
                <div class="alert alert-info py-2 small mb-0">
                    <i class="bi bi-info-circle-fill me-1"></i>
                    <strong>Simulated Transaction:</strong> No real bank cards or accounts are billed.
                </div>
            </div>

            <!-- Invoice Summary Card -->
            <div class="bg-light p-3 rounded-3 mb-4 border">
                <div class="d-flex justify-content-between mb-2">
                    <span class="text-muted">Student:</span>
                    <strong class="text-dark">${assignment.student.user.name} (${assignment.student.rollNumber})</strong>
                </div>
                <div class="d-flex justify-content-between mb-2">
                    <span class="text-muted">Course:</span>
                    <span class="text-dark">${assignment.student.course.courseName}</span>
                </div>
                <div class="d-flex justify-content-between mb-2">
                    <span class="text-muted">Academic Term:</span>
                    <span class="text-dark">${assignment.academicPeriod}</span>
                </div>
                <div class="d-flex justify-content-between mb-2">
                    <span class="text-muted">Total Assigned Fee:</span>
                    <span>₹<fmt:formatNumber value="${assignment.totalFee}" minFractionDigits="2" /></span>
                </div>
                <div class="d-flex justify-content-between mb-2">
                    <span class="text-muted">Amount Already Paid:</span>
                    <span class="text-success fw-semibold">₹<fmt:formatNumber value="${assignment.paidAmount}" minFractionDigits="2" /></span>
                </div>
                <hr class="my-2">
                <div class="d-flex justify-content-between">
                    <span class="fw-bold text-danger">Outstanding Balance:</span>
                    <strong class="text-danger fs-5">
                        ₹<fmt:formatNumber value="${assignment.balanceAmount}" minFractionDigits="2" />
                    </strong>
                </div>
            </div>

            <!-- Payment Form -->
            <form action="${pageContext.request.contextPath}/student/pay" method="post" 
                  onsubmit="return validatePaymentForm(${assignment.balanceAmount});">
                
                <input type="hidden" name="assignmentId" value="${assignment.assignmentId}">

                <div class="mb-3">
                    <label for="amount" class="form-label fw-semibold">Payment Amount (₹) <span class="text-danger">*</span></label>
                    <div class="input-group">
                        <span class="input-group-text bg-light fw-bold">₹</span>
                        <input type="number" step="0.01" min="1" max="${assignment.balanceAmount}" 
                               class="form-control form-control-lg" id="amount" name="amount" 
                               value="${assignment.balanceAmount}" required>
                    </div>
                    <small class="text-muted">You can pay the full balance or make a partial payment.</small>
                </div>

                <div class="mb-4">
                    <label for="paymentMethod" class="form-label fw-semibold">Select Payment Mode <span class="text-danger">*</span></label>
                    <select class="form-select" id="paymentMethod" name="paymentMethod" required>
                        <option value="UPI (Google Pay / PhonePe / Paytm)">UPI (Google Pay / PhonePe / Paytm)</option>
                        <option value="Debit Card / ATM Card">Debit Card / ATM Card</option>
                        <option value="Credit Card">Credit Card</option>
                        <option value="Net Banking (NEFT / RTGS / IMPS)">Net Banking (NEFT / RTGS / IMPS)</option>
                    </select>
                </div>

                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-success py-2 fw-semibold fs-5">
                        <i class="bi bi-shield-check me-1"></i> Confirm &amp; Pay Now
                    </button>
                    <a href="${pageContext.request.contextPath}/student/dashboard" class="btn btn-outline-secondary">
                        Cancel
                    </a>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/common/footer.jsp" />
