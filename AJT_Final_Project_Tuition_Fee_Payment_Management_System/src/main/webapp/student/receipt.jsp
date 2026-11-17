<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/common/header.jsp">
    <jsp:param name="title" value="Payment Receipt - ${payment.receiptNumber}" />
</jsp:include>

<div class="row justify-content-center py-3">
    <div class="col-md-9 col-lg-8">
        
        <!-- Print / Action Bar -->
        <div class="d-flex justify-content-between align-items-center mb-3 no-print">
            <a href="javascript:history.back()" class="btn btn-outline-secondary btn-sm">
                <i class="bi bi-arrow-left me-1"></i> Back
            </a>
            <button onclick="window.print()" class="btn btn-primary btn-sm">
                <i class="bi bi-printer-fill me-1"></i> Print / Save Receipt
            </button>
        </div>

        <!-- Formal Receipt Card Area -->
        <div class="receipt-box print-area">
            <!-- Header -->
            <div class="receipt-header text-center">
                <div class="d-flex justify-content-center align-items-center gap-2 mb-2">
                    <i class="bi bi-wallet2 text-primary fs-2"></i>
                    <h3 class="fw-bold mb-0 text-dark">TUITION FEE MANAGEMENT SYSTEM</h3>
                </div>
                <p class="text-muted small mb-0">Official E-Payment Acknowledgement &amp; Fee Receipt</p>
            </div>

            <!-- Receipt Meta Bar -->
            <div class="row g-2 mb-4 bg-light p-3 rounded border">
                <div class="col-sm-6">
                    <span class="text-muted small d-block">Receipt Number</span>
                    <strong class="font-monospace text-primary fs-6">${payment.receiptNumber}</strong>
                </div>
                <div class="col-sm-6 text-sm-end">
                    <span class="text-muted small d-block">Transaction Reference</span>
                    <span class="font-monospace text-dark">${payment.transactionReference}</span>
                </div>
                <div class="col-sm-6 mt-2">
                    <span class="text-muted small d-block">Payment Date &amp; Time</span>
                    <strong class="text-dark">${payment.paymentDate}</strong>
                </div>
                <div class="col-sm-6 text-sm-end mt-2">
                    <span class="text-muted small d-block">Payment Method</span>
                    <span class="badge bg-secondary">${payment.paymentMethod}</span>
                </div>
            </div>

            <!-- Student & Academic Details -->
            <div class="row mb-4">
                <div class="col-sm-6">
                    <h6 class="fw-bold text-secondary text-uppercase small mb-2">Student Information</h6>
                    <p class="mb-1"><strong>Name:</strong> ${payment.feeAssignment.student.user.name}</p>
                    <p class="mb-1"><strong>Roll Number:</strong> ${payment.feeAssignment.student.rollNumber}</p>
                    <p class="mb-1"><strong>Email:</strong> ${payment.feeAssignment.student.user.email}</p>
                    <p class="mb-0"><strong>Phone:</strong> ${not empty payment.feeAssignment.student.user.phone ? payment.feeAssignment.student.user.phone : 'N/A'}</p>
                </div>
                <div class="col-sm-6 text-sm-end">
                    <h6 class="fw-bold text-secondary text-uppercase small mb-2">Course &amp; Academic Term</h6>
                    <p class="mb-1"><strong>Course:</strong> ${payment.feeAssignment.student.course.courseName}</p>
                    <p class="mb-1"><strong>Duration:</strong> ${payment.feeAssignment.student.course.duration}</p>
                    <p class="mb-1"><strong>Academic Period:</strong> ${payment.feeAssignment.academicPeriod}</p>
                    <p class="mb-0"><strong>Due Date:</strong> ${payment.feeAssignment.dueDate}</p>
                </div>
            </div>

            <!-- Financial Table -->
            <div class="table-responsive mb-4">
                <table class="table table-bordered align-middle">
                    <thead class="table-light">
                        <tr>
                            <th>Description</th>
                            <th class="text-end" style="width: 200px;">Amount (INR)</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                                <strong>Tuition Fee - ${payment.feeAssignment.academicPeriod}</strong><br>
                                <small class="text-muted">${payment.feeAssignment.student.course.courseName}</small>
                            </td>
                            <td class="text-end">
                                ₹<fmt:formatNumber value="${payment.feeAssignment.totalFee}" minFractionDigits="2" />
                            </td>
                        </tr>
                        <tr class="table-light">
                            <td class="text-end fw-bold text-success">
                                Amount Paid in this Transaction:
                            </td>
                            <td class="text-end fw-bold text-success fs-5">
                                ₹<fmt:formatNumber value="${payment.amountPaid}" minFractionDigits="2" />
                            </td>
                        </tr>
                        <tr>
                            <td class="text-end fw-semibold">Payment Status:</td>
                            <td class="text-end">
                                <c:choose>
                                    <c:when test="${payment.paymentStatus eq 'PAID'}">
                                        <span class="badge badge-paid fs-6">SUCCESS / PAID</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge-partial fs-6">${payment.paymentStatus}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <!-- Receipt Footer / Signature -->
            <div class="receipt-footer row align-items-center">
                <div class="col-sm-8">
                    <p class="small text-muted mb-0">
                        <i class="bi bi-shield-check text-success me-1"></i>
                        This is an electronically generated receipt verified by the Tuition Fee Payment System. 
                        No physical signature is required.
                    </p>
                </div>
                <div class="col-sm-4 text-sm-end mt-3 mt-sm-0">
                    <div class="p-2 border rounded bg-light text-center">
                        <strong class="d-block text-success small">AUTHORIZED</strong>
                        <span class="badge bg-success">RECORD VERIFIED</span>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

<jsp:include page="/common/footer.jsp" />
