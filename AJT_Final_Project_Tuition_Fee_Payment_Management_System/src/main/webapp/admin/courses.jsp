<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/common/header.jsp">
    <jsp:param name="title" value="Manage Courses - Admin" />
</jsp:include>

<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h2 class="fw-bold mb-1">Course Catalog Management</h2>
        <p class="text-muted small mb-0">Define tuition programs, course durations, and standard fee structures</p>
    </div>
</div>

<div class="row g-4">
    <!-- Add / Edit Course Form -->
    <div class="col-lg-4">
        <div class="card form-card p-4">
            <h5 class="fw-bold mb-3">
                <i class="bi ${editCourse != null ? 'bi-pencil-square text-primary' : 'bi-plus-circle-fill text-success'} me-2"></i>
                ${editCourse != null ? 'Edit Course' : 'Add New Course'}
            </h5>

            <form action="${pageContext.request.contextPath}/admin/courses" method="post">
                <c:if test="${editCourse != null}">
                    <input type="hidden" name="courseId" value="${editCourse.courseId}">
                </c:if>

                <div class="mb-3">
                    <label for="courseName" class="form-label fw-semibold">Course Title <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="courseName" name="courseName" 
                           value="${editCourse != null ? editCourse.courseName : ''}" 
                           placeholder="e.g. Java Full Stack" required>
                </div>

                <div class="mb-3">
                    <label for="duration" class="form-label fw-semibold">Duration <span class="text-danger">*</span></label>
                    <input type="text" class="form-control" id="duration" name="duration" 
                           value="${editCourse != null ? editCourse.duration : ''}" 
                           placeholder="e.g. 6 Months" required>
                </div>

                <div class="mb-3">
                    <label for="totalFee" class="form-label fw-semibold">Total Course Fee (₹) <span class="text-danger">*</span></label>
                    <input type="number" step="0.01" min="0" class="form-control" id="totalFee" name="totalFee" 
                           value="${editCourse != null ? editCourse.totalFee : ''}" 
                           placeholder="e.g. 45000" required>
                </div>

                <div class="mb-3">
                    <label for="description" class="form-label fw-semibold">Description</label>
                    <textarea class="form-control" id="description" name="description" rows="3" 
                              placeholder="Key topics and modules...">${editCourse != null ? editCourse.description : ''}</textarea>
                </div>

                <div class="d-grid gap-2">
                    <button type="submit" class="btn btn-primary fw-semibold">
                        <i class="bi bi-save me-1"></i> ${editCourse != null ? 'Update Course' : 'Create Course'}
                    </button>
                    <c:if test="${editCourse != null}">
                        <a href="${pageContext.request.contextPath}/admin/courses" class="btn btn-outline-secondary btn-sm">
                            Cancel Editing
                        </a>
                    </c:if>
                </div>
            </form>
        </div>
    </div>

    <!-- Courses List Table -->
    <div class="col-lg-8">
        <div class="card table-card bg-white">
            <div class="card-header bg-white py-3">
                <h5 class="fw-bold mb-0 text-dark">
                    <i class="bi bi-journal-bookmark text-primary me-2"></i> Active Courses (${courses.size()})
                </h5>
            </div>
            <div class="table-responsive">
                <table class="table table-hover align-middle">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Course Name</th>
                            <th>Duration</th>
                            <th>Total Fee</th>
                            <th>Description</th>
                            <th class="text-center">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty courses}">
                                <tr>
                                    <td colspan="6" class="text-center text-muted py-4">No courses registered yet.</td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="c" items="${courses}">
                                    <tr>
                                        <td>#${c.courseId}</td>
                                        <td class="fw-bold text-dark">${c.courseName}</td>
                                        <td><span class="badge bg-secondary-subtle text-secondary">${c.duration}</span></td>
                                        <td class="text-success fw-semibold">
                                            ₹<fmt:formatNumber value="${c.totalFee}" minFractionDigits="2" />
                                        </td>
                                        <td class="small text-muted text-truncate" style="max-width: 200px;">
                                            ${c.description}
                                        </td>
                                        <td class="text-center">
                                            <div class="btn-group btn-group-sm">
                                                <a href="${pageContext.request.contextPath}/admin/courses?editId=${c.courseId}" 
                                                   class="btn btn-outline-primary" title="Edit Course">
                                                    <i class="bi bi-pencil-square"></i>
                                                </a>
                                                <a href="${pageContext.request.contextPath}/admin/courses?action=delete&id=${c.courseId}" 
                                                   class="btn btn-outline-danger" 
                                                   onclick="return confirm('Are you sure you want to delete ${c.courseName}?');"
                                                   title="Delete Course">
                                                    <i class="bi bi-trash"></i>
                                                </a>
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
    </div>
</div>

<jsp:include page="/common/footer.jsp" />
