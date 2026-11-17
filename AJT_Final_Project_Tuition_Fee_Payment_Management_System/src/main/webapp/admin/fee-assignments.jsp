<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/common/header.jsp">
    <jsp:param name="title" value="Fee Assignments - Admin" />
</jsp:include>

<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h2 class="fw-bold mb-1">Fee Assignment Management</h2>
        <p class="text-muted small mb-0">Allocate fees, academic terms, and payment deadlines to enrolled students</p>
    </div>
    <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#assignFeeModal">
        <i class="bi bi-plus-circle-fill me-1"></i> Assign New Fee
    </button>
</div>

<!-- Fee Assignments Table -->
<div class="card table-card bg-white">
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Student Name</th>
                    <th>Roll Number</th>
                    <th>Academic Period</th>
                    <th>Total Fee</th>
                    <th>Paid</th>
                    <th>Balance</th>
                    <th>Due Date</th>
                    <th>Status</th>
                    <th class="text-center">Action</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty assignments}">
                        <tr>
                            <td colspan="10" class="text-center text-muted py-4">No fee assignments recorded.</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="fa" items="${assignments}">
                            <tr>
                                <td>#${fa.assignmentId}</td>
                                <td class="fw-semibold text-dark">${fa.student.user.name}</td>
                                <td><span class="badge bg-light text-dark border font-monospace">${fa.student.rollNumber}</span></td>
                                <td>${fa.academicPeriod}</td>
                                <td class="fw-bold">
                                    ₹<fmt:formatNumber value="${fa.totalFee}" minFractionDigits="2" />
                                </td>
                                <td class="text-success fw-semibold">
                                    ₹<fmt:formatNumber value="${fa.paidAmount}" minFractionDigits="2" />
                                </td>
                                <td class="text-danger fw-semibold">
                                    ₹<fmt:formatNumber value="${fa.balanceAmount}" minFractionDigits="2" />
                                </td>
                                <td>${fa.dueDate}</td>
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
                                    <a href="${pageContext.request.contextPath}/admin/fee-assignments?action=delete&id=${fa.assignmentId}" 
                                       class="btn btn-outline-danger btn-sm" 
                                       onclick="return confirm('Delete this fee assignment? Any existing payment records will be cascade deleted.');"
                                       title="Delete Fee Assignment">
                                        <i class="bi bi-trash"></i>
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

<!-- Modal: Assign New Fee -->
<div class="modal fade" id="assignFeeModal" tabindex="-1" aria-labelledby="assignFeeModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="${pageContext.request.contextPath}/admin/fee-assignments" method="post">
                <div class="modal-header">
                    <h5 class="modal-title fw-bold" id="assignFeeModalLabel">
                        <i class="bi bi-cash-coin text-success me-2"></i> Assign Fee to Student
                    </h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="studentId" class="form-label fw-semibold">Select Student <span class="text-danger">*</span></label>
                        <select class="form-select" id="studentId" name="studentId" required onchange="updateDefaultFee(this)">
                            <option value="">-- Choose Student --</option>
                            <c:forEach var="s" items="${students}">
                                <option value="${s.studentId}" data-fee="${s.course != null ? s.course.totalFee : '0'}">
                                    ${s.user.name} (${s.rollNumber}) - ${s.course != null ? s.course.courseName : 'No Course'}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="totalFee" class="form-label fw-semibold">Fee Amount (₹) <span class="text-danger">*</span></label>
                        <input type="number" step="0.01" min="1" class="form-control" id="totalFee" name="totalFee" placeholder="e.g. 45000" required>
                    </div>

                    <div class="mb-3">
                        <label for="dueDate" class="form-label fw-semibold">Due Date <span class="text-danger">*</span></label>
                        <input type="date" class="form-control" id="dueDate" name="dueDate" required>
                    </div>

                    <div class="mb-3">
                        <label for="academicPeriod" class="form-label fw-semibold">Academic Period <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="academicPeriod" name="academicPeriod" placeholder="e.g. Semester 1 (2026)" required>
                    </div>

                    <div class="mb-3">
                        <label for="remarks" class="form-label fw-semibold">Remarks</label>
                        <input type="text" class="form-control" id="remarks" name="remarks" placeholder="Optional notes (e.g. Regular Batch)">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-light" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-success px-4">
                        <i class="bi bi-check-circle me-1"></i> Confirm &amp; Assign
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
function updateDefaultFee(selectElem) {
    const selectedOption = selectElem.options[selectElem.selectedIndex];
    const defaultFee = selectedOption.getAttribute('data-fee');
    if (defaultFee && defaultFee > 0) {
        document.getElementById('totalFee').value = defaultFee;
    }
}
</script>

<jsp:include page="/common/footer.jsp" />
