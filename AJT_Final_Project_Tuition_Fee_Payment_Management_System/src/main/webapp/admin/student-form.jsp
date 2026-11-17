<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/common/header.jsp">
    <jsp:param name="title" value="${student != null ? 'Edit Student' : 'Add Student'} - Admin" />
</jsp:include>

<div class="row justify-content-center">
    <div class="col-md-8 col-lg-7">
        <div class="card form-card p-4">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="fw-bold mb-0">
                    <i class="bi ${student != null ? 'bi-pencil-square text-primary' : 'bi-person-plus-fill text-success'} me-2"></i>
                    ${student != null ? 'Edit Student Details' : 'Add New Student'}
                </h4>
                <a href="${pageContext.request.contextPath}/admin/students" class="btn btn-outline-secondary btn-sm">
                    <i class="bi bi-arrow-left"></i> Back to List
                </a>
            </div>
            <p class="text-muted small mb-4">Provide academic profile and contact details</p>

            <form action="${pageContext.request.contextPath}/admin/students" method="post">
                <c:if test="${student != null}">
                    <input type="hidden" name="studentId" value="${student.studentId}">
                </c:if>

                <div class="row g-3">
                    <div class="col-md-6">
                        <label for="name" class="form-label fw-semibold">Student Name <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" id="name" name="name" 
                               value="${student != null ? student.user.name : ''}" required>
                    </div>

                    <div class="col-md-6">
                        <label for="email" class="form-label fw-semibold">Email Address <span class="text-danger">*</span></label>
                        <input type="email" class="form-control" id="email" name="email" 
                               value="${student != null ? student.user.email : ''}" required>
                    </div>

                    <div class="col-md-6">
                        <label for="rollNumber" class="form-label fw-semibold">
                            Roll Number <span class="text-danger">*</span>
                        </label>
                        <input type="text" class="form-control" id="rollNumber" name="rollNumber" 
                               value="${student != null ? student.rollNumber : generatedRollNumber}" 
                               ${student != null ? 'readonly' : ''} required>
                        <small class="text-muted">
                            ${student != null ? 'Assigned by Administrator (Permanent Roll #).' : 'Generated and assigned exclusively by Administrator.'}
                        </small>
                    </div>

                    <div class="col-md-6">
                        <label for="phone" class="form-label fw-semibold">Phone Number</label>
                        <input type="tel" class="form-control" id="phone" name="phone" 
                               value="${student != null ? student.user.phone : ''}">
                    </div>

                    <div class="col-md-6">
                        <label for="courseId" class="form-label fw-semibold">Course Enrollment <span class="text-danger">*</span></label>
                        <select class="form-select" id="courseId" name="courseId" required>
                            <option value="">-- Select Course --</option>
                            <c:forEach var="c" items="${courses}">
                                <option value="${c.courseId}" ${student != null && student.course != null && student.course.courseId == c.courseId ? 'selected' : ''}>
                                    ${c.courseName} - ₹${c.totalFee}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-6">
                        <label for="admissionDate" class="form-label fw-semibold">Admission Date</label>
                        <input type="date" class="form-control" id="admissionDate" name="admissionDate" 
                               value="${student != null ? student.admissionDate : ''}">
                    </div>

                    <c:if test="${student == null}">
                        <div class="col-md-12">
                            <label for="password" class="form-label fw-semibold">
                                Initial Student Password <span class="text-danger">*</span>
                            </label>
                            <input type="text" class="form-control" id="password" name="password" 
                                   value="student123" required>
                            <small class="text-muted">Administrator must provide this password and the student's email/roll number to the student.</small>
                        </div>
                    </c:if>

                    <div class="col-12">
                        <label for="address" class="form-label fw-semibold">Permanent Address</label>
                        <textarea class="form-control" id="address" name="address" rows="2">${student != null ? student.address : ''}</textarea>
                    </div>
                </div>

                <div class="d-flex justify-content-end gap-2 mt-4">
                    <a href="${pageContext.request.contextPath}/admin/students" class="btn btn-light px-3">Cancel</a>
                    <button type="submit" class="btn btn-primary px-4 fw-semibold">
                        <i class="bi bi-save me-1"></i> ${student != null ? 'Update Student' : 'Save Student'}
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="/common/footer.jsp" />
