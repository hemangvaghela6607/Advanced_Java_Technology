<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/common/header.jsp">
    <jsp:param name="title" value="Manage Students - Admin" />
</jsp:include>

<div class="d-flex justify-content-between align-items-center mb-4">
    <div>
        <h2 class="fw-bold mb-1">Student Management</h2>
        <p class="text-muted small mb-0">View, register, edit, and manage all enrolled students</p>
    </div>
    <a href="${pageContext.request.contextPath}/admin/students?action=new" class="btn btn-primary">
        <i class="bi bi-person-plus-fill me-1"></i> Add New Student
    </a>
</div>

<div class="card table-card bg-white">
    <div class="table-responsive">
        <table class="table table-hover align-middle">
            <thead>
                <tr>
                    <th>Roll #</th>
                    <th>Full Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Enrolled Course</th>
                    <th>Admission Date</th>
                    <th class="text-center">Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty students}">
                        <tr>
                            <td colspan="7" class="text-center text-muted py-4">No student records found.</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="s" items="${students}">
                            <tr>
                                <td><span class="badge bg-light text-dark border font-monospace">${s.rollNumber}</span></td>
                                <td class="fw-semibold">${s.user.name}</td>
                                <td>${s.user.email}</td>
                                <td>${s.user.phone}</td>
                                <td>
                                    <span class="badge bg-info-subtle text-dark">
                                        ${s.course != null ? s.course.courseName : 'Not Assigned'}
                                    </span>
                                </td>
                                <td>${s.admissionDate}</td>
                                <td class="text-center">
                                    <div class="btn-group btn-group-sm">
                                        <a href="${pageContext.request.contextPath}/admin/students?action=edit&id=${s.studentId}" 
                                           class="btn btn-outline-primary" title="Edit Student">
                                            <i class="bi bi-pencil-square"></i>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/admin/fee-assignments" 
                                           class="btn btn-outline-success" title="Assign Fee">
                                            <i class="bi bi-cash"></i>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/admin/students?action=delete&id=${s.studentId}" 
                                           class="btn btn-outline-danger" 
                                           onclick="return confirm('Are you sure you want to delete student ${s.user.name}? This will also delete related fee records.');"
                                           title="Delete Student">
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

<jsp:include page="/common/footer.jsp" />
