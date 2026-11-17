<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/common/header.jsp">
    <jsp:param name="title" value="Financial Reports - Admin" />
</jsp:include>

<div class="d-flex justify-content-between align-items-center mb-4 no-print">
    <div>
        <h2 class="fw-bold mb-1">Financial &amp; Revenue Reports</h2>
        <p class="text-muted small mb-0">Aggregate collection analysis and course-wise fee reconciliation</p>
    </div>
    <button onclick="window.print()" class="btn btn-outline-dark">
        <i class="bi bi-printer me-1"></i> Print Report
    </button>
</div>

<!-- Financial KPI Summary Cards -->
<div class="row g-3 mb-4">
    <div class="col-md-4">
        <div class="card metric-card bg-white p-3 border-start border-4 border-primary">
            <span class="text-muted small fw-semibold">Total Revenue Assigned</span>
            <h3 class="fw-bold my-1 text-primary">
                ₹<fmt:formatNumber value="${totalAssigned}" minFractionDigits="2" maxFractionDigits="2" />
            </h3>
            <small class="text-muted">Total invoice across all students</small>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card metric-card bg-white p-3 border-start border-4 border-success">
            <span class="text-muted small fw-semibold">Total Revenue Collected</span>
            <h3 class="fw-bold my-1 text-success">
                ₹<fmt:formatNumber value="${totalCollected}" minFractionDigits="2" maxFractionDigits="2" />
            </h3>
            <small class="text-success">
                <c:choose>
                    <c:when test="${totalAssigned > 0}">
                        <fmt:formatNumber value="${(totalCollected / totalAssigned) * 100}" maxFractionDigits="1" />% Realized
                    </c:when>
                    <c:otherwise>0%</c:otherwise>
                </c:choose>
            </small>
        </div>
    </div>
    <div class="col-md-4">
        <div class="card metric-card bg-white p-3 border-start border-4 border-danger">
            <span class="text-muted small fw-semibold">Total Pending Dues</span>
            <h3 class="fw-bold my-1 text-danger">
                ₹<fmt:formatNumber value="${totalPending}" minFractionDigits="2" maxFractionDigits="2" />
            </h3>
            <small class="text-danger">Awaiting student payment</small>
        </div>
    </div>
</div>

<!-- Course-Wise Collection Table -->
<div class="card table-card bg-white mb-4">
    <div class="card-header bg-white py-3">
        <h5 class="fw-bold mb-0 text-dark">
            <i class="bi bi-pie-chart-fill text-primary me-2"></i> Course-Wise Revenue Breakdown
        </h5>
    </div>
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead>
                <tr>
                    <th>Course Program</th>
                    <th class="text-center">Enrolled Students</th>
                    <th>Total Assigned (₹)</th>
                    <th>Collected (₹)</th>
                    <th>Pending (₹)</th>
                    <th style="min-width: 150px;">Collection Progress</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty reportItems}">
                        <tr>
                            <td colspan="6" class="text-center text-muted py-4">No report data available.</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="item" items="${reportItems}">
                            <c:set var="pct" value="${item.totalAssigned > 0 ? (item.totalPaid / item.totalAssigned) * 100 : 0}" />
                            <tr>
                                <td class="fw-bold text-dark">${item.courseName}</td>
                                <td class="text-center">
                                    <span class="badge bg-secondary-subtle text-secondary">${item.studentCount}</span>
                                </td>
                                <td>₹<fmt:formatNumber value="${item.totalAssigned}" minFractionDigits="2" /></td>
                                <td class="text-success fw-semibold">
                                    ₹<fmt:formatNumber value="${item.totalPaid}" minFractionDigits="2" />
                                </td>
                                <td class="text-danger fw-semibold">
                                    ₹<fmt:formatNumber value="${item.totalPending}" minFractionDigits="2" />
                                </td>
                                <td>
                                    <div class="d-flex align-items-center gap-2">
                                        <div class="progress flex-grow-1" style="height: 8px;">
                                            <div class="progress-bar bg-success" role="progressbar" 
                                                 style="width: ${pct}%;" aria-valuenow="${pct}" aria-valuemin="0" aria-valuemax="100">
                                            </div>
                                        </div>
                                        <small class="text-muted fw-semibold">
                                            <fmt:formatNumber value="${pct}" maxFractionDigits="0" />%
                                        </small>
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
