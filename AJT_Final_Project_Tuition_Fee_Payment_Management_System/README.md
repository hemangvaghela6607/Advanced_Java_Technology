# Tuition Fee Payment Management System
### Advanced Java Technology (AJT) Project

A complete, multi-tiered institutional fee management and billing web portal with a companion Java Swing desktop utility. Built with standard Java Enterprise components (**JSP**, **Servlets**, **JSTL/EL**), **JDBC**, **Hibernate ORM**, **Spring Framework (Core DI)**, **Java Swing**, and **MySQL (XAMPP/phpMyAdmin)** deployed on **Apache Tomcat** via **Apache NetBeans IDE**.

---

## 1. Project Overview & Architecture

### Key Modules & Roles
1. **Admin Role**:
   - **Authentication**: Secure login/logout.
   - **Dashboard**: Real-time KPI summary (Total Students, Total Courses, Total Fees Paid, Pending Balance, Recent Payments).
   - **Student Management**: Register, view, edit, and delete student records.
   - **Course Catalog**: Manage tuition programs, duration, and fee structures.
   - **Fee Assignments**: Assign fees, set due dates, and academic periods to enrolled students.
   - **Payment Records**: Search and filter all payment transactions by course, payment status, or keyword.
   - **Financial Reports**: Course-wise collection vs. pending reconciliation with visual progress bars.

2. **Student Role**:
   - **Self-Registration & Login**: Student portal access.
   - **Profile & Status**: Overview of enrolled course, assigned fee, paid amount, and outstanding balance.
   - **Fee Details**: Clear itemization of fees due and academic terms.
   - **Simulated Payment Flow**: One-click "Pay Now" checkout with client and server validations, generating unique transaction references and official receipt numbers.
   - **Payment History & Receipts**: View and print electronic payment receipts (`window.print()`).

3. **Swing Desktop Utility (`FeeReceiptViewer`)**:
   - Standalone administrative desktop application built with Java Swing.
   - Connects directly to MySQL via JDBC.
   - Fast lookup of payment records by **Receipt Number**, **Student Roll Number**, or **Student Name**.
   - Printable formatted receipt dialog.

---

## 2. Technology Implementation Highlights

| Technology | Where & How it is Implemented in this Project |
| :--- | :--- |
| **Java Servlets** | Controller layer (`LoginServlet`, `StudentServlet`, `CourseServlet`, `FeeAssignmentServlet`, `PaymentServlet`, `ReceiptServlet`, `AdminReportServlet`). Handles HTTP GET/POST, session tracking, and routing. |
| **JSP, JSTL & EL** | Presentation layer (`src/main/webapp/`). Clean, scriptlet-free templates using `<c:forEach>`, `<c:if>`, `<c:choose>`, `<fmt:formatNumber>`, and Expression Language (`${sessionScope.loggedInUser}`). |
| **JDBC** | Raw persistence in `UserDAOImpl` and `CourseDAOImpl` using `PreparedStatement`, `ResultSet`, and `DBConnection.java` to demonstrate manual connection management and SQL injection prevention. |
| **Hibernate 5 ORM** | Object-Relational Mapping for `Student`, `FeeAssignment`, and `Payment` entities. Configured in `hibernate.cfg.xml`. Demonstrates `SessionFactory`, `Session`, `Transaction`, and HQL. |
| **Spring Core DI** | Inversion of Control (IoC) configured via `applicationContext.xml`. Manages and injects DAO beans into service classes (`StudentService`, `FeeService`, `PaymentService`, `CourseService`, `UserService`). |
| **Java Swing** | Desktop utility `com.tuitionfee.swing.FeeReceiptViewer` with `JTable`, search filters, and receipt viewer. |
| **Security / Filter** | `AuthFilter` intercepts `/admin/*` and `/student/*` URLs to enforce strict role-based access control. |
| **MySQL Database** | Relational schema in XAMPP phpMyAdmin (`tuition_fee_management`) with foreign keys, ON DELETE CASCADE, and seed data. |

---

## 3. Project Directory Structure

```
d:\AJT Prj/
├── pom.xml                                   # Maven configuration file (compatible with NetBeans)
├── database/
│   └── tuition_fee_management.sql            # Database schema & demo records
├── src/
│   └── main/
│       ├── java/
│       │   └── com/tuitionfee/
│       │       ├── model/                    # JPA Entities (User, Course, Student, FeeAssignment, Payment)
│       │       ├── dao/                      # DAOs (JDBC: UserDAO, CourseDAO; Hibernate: StudentDAO, FeeDAO, PaymentDAO)
│       │       ├── service/                  # Business Services managed by Spring Core DI
│       │       ├── servlet/                  # HTTP Controllers (Servlets)
│       │       ├── filter/                   # Role-based security filter (AuthFilter)
│       │       ├── util/                     # DBConnection (JDBC), HibernateUtil, SpringContextHelper
│       │       ├── exception/                # AppException, InvalidPaymentException
│       │       └── swing/                    # Standalone Desktop Utility (FeeReceiptViewer)
│       ├── resources/
│       │   ├── db.properties                 # MySQL connection properties
│       │   ├── hibernate.cfg.xml             # Hibernate ORM configuration
│       │   └── applicationContext.xml        # Spring Core XML Dependency Injection
│       └── webapp/
│           ├── WEB-INF/
│           │   └── web.xml                   # Web application descriptor
│           ├── css/
│           │   └── style.css                 # Custom styling & print layout
│           ├── js/
│           │   └── validation.js             # Client-side form validations
│           ├── common/
│           │   ├── header.jsp                # Common navbar
│           │   ├── footer.jsp                # Common footer
│           │   └── alert.jsp                 # Toast alerts
│           ├── admin/                        # Admin JSP pages (dashboard, students, courses, fee-assignments, payments, reports)
│           ├── student/                      # Student JSP pages (dashboard, fee-details, payment-form, payment-history, receipt)
│           ├── index.jsp                     # Home landing page
│           ├── login.jsp                     # Login page with demo prefill
│           ├── register.jsp                  # Student self-registration
│           └── error.jsp                     # Custom error page
├── README.md                                 # Complete project instructions
└── VIVA_GUIDE.md                             # Comprehensive viva preparation & Q&A
```

---

## 4. Step-by-Step Setup Guide

### Step 1: Start XAMPP & Import MySQL Database
1. Launch **XAMPP Control Panel**.
2. Click **Start** next to **Apache** and **MySQL**.
3. Open your browser and go to: `http://localhost/phpmyadmin/`.
4. Click on the **Import** tab at the top.
5. Click **Choose File**, select `database/tuition_fee_management.sql` from this project folder, and click **Import** (or **Go**).
6. Verify that database `tuition_fee_management` is created with tables:
   - `users`
   - `courses`
   - `students`
   - `fee_assignments`
   - `payments`

> [!NOTE]
> Database connection defaults to `root` with no password (`""`) on port `3306`. If your MySQL has a password, update it in `src/main/resources/db.properties` and `src/main/resources/hibernate.cfg.xml`.

---

### Step 2: Open Project in Apache NetBeans IDE
1. Launch **Apache NetBeans IDE**.
2. Click **File** &rarr; **Open Project...**
3. Browse to `d:\AJT Prj` (the project root folder containing `pom.xml`).
4. NetBeans will detect the project as a **Maven Web Application**. Click **Open Project**.
5. Wait a moment for NetBeans to load dependencies.

---

### Step 3: Configure Apache Tomcat in NetBeans
If Tomcat is not already added to NetBeans:
1. In NetBeans, open the **Services** tab (or menu **Window** &rarr; **Services**).
2. Right-click on **Servers** &rarr; **Add Server...**
3. Select **Apache Tomcat or TomEE** &rarr; click **Next**.
4. Browse to your Apache Tomcat installation directory (e.g. `C:\xampp\tomcat` or standalone Tomcat).
5. Enter your Tomcat manager username/password (default usually `admin`/`admin` or empty).
6. Click **Finish**.

---

### Step 4: Run the Web Application
1. In NetBeans **Projects** window, right-click on **TuitionFeeManagement** project.
2. Select **Run** (or press **F6**).
3. NetBeans will build the WAR file, deploy it to Apache Tomcat, and automatically open your default browser at:
   ```
   http://localhost:8080/TuitionFeeManagement/
   ```

---

### Step 5: Run the Standalone Swing Desktop Utility
The project includes a companion desktop utility for receptionists and administrators:
1. In NetBeans **Projects** window, navigate to:
   ```
   Source Packages -> com.tuitionfee.swing -> FeeReceiptViewer.java
   ```
2. Right-click on `FeeReceiptViewer.java` and click **Run File** (or press **Shift + F6**).
3. The desktop GUI will appear immediately, allowing you to:
   - Search by **Receipt Number** (e.g., `REC-2026-0001`).
   - Search by **Student Roll Number** (e.g., `STU-2026-001`).
   - Search by **Student Name** (e.g., `Rahul`).
   - Click **View Selected Receipt** to view and print the formatted receipt card.

---

## 5. Demo User Credentials

| Role | Email | Password | Description |
| :--- | :--- | :--- | :--- |
| **Admin** | `admin@tuition.com` | `admin123` | Full administrative access to dashboard, students, courses, assignments, and reports |
| **Student** | `rahul@student.com` | `student123` | Student enrolled in Java Full Stack (Fee: ₹45,000, Paid: ₹45,000, Balance: ₹0) |
| **Student** | `priya@student.com` | `student123` | Student enrolled in Python Data Science (Fee: ₹42,000, Paid: ₹20,000, Balance: ₹22,000) |
| **Student** | `aman@student.com` | `student123` | Student enrolled in Java Full Stack (Fee: ₹45,000, Pending: ₹45,000) |

*(You can also use the one-click demo prefill buttons on the login page!)*

---

## 6. Simulated Payment Process Flow
1. Log in as student `priya@student.com` (password: `student123`).
2. On the student dashboard, look at the **Assigned Tuition Fees** table.
3. Click the green **Pay Now** button.
4. The system presents the simulated payment confirmation checkout form showing the outstanding balance (`₹22,000.00`).
5. Choose payment method (UPI / Card / Net Banking) and click **Confirm & Pay Now**.
6. The system:
   - Validates that amount > 0 and does not exceed balance.
   - Automatically generates a unique transaction reference (e.g. `TXN-20260906-4821`).
   - Automatically generates a unique receipt number (e.g. `REC-2026-8714`).
   - Persists the payment record in MySQL using Hibernate ORM.
   - Redirects directly to the printable formal electronic receipt (`receipt.jsp`).
