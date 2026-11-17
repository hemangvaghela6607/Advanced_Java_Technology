DROP DATABASE IF EXISTS `tuition_fee_management`;
CREATE DATABASE `tuition_fee_management` 
    DEFAULT CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

USE `tuition_fee_management`;

-- Table 1: users
-- Stores authentication & contact credentials for Admin and Students

CREATE TABLE `users` (
    `user_id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(100) NOT NULL,
    `email` VARCHAR(120) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `role` ENUM('ADMIN', 'STUDENT') NOT NULL DEFAULT 'STUDENT',
    `phone` VARCHAR(20),
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table 2: courses
-- Stores course details, duration, and default fee structure
CREATE TABLE `courses` (
    `course_id` INT AUTO_INCREMENT PRIMARY KEY,
    `course_name` VARCHAR(120) NOT NULL,
    `duration` VARCHAR(50) NOT NULL,
    `total_fee` DECIMAL(10, 2) NOT NULL,
    `description` TEXT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table 3: students
-- Stores student profile linked to a user account and enrolled course

CREATE TABLE `students` (
    `student_id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL UNIQUE,
    `roll_number` VARCHAR(50) NOT NULL UNIQUE,
    `course_id` INT,
    `address` TEXT,
    `admission_date` DATE NOT NULL,
    CONSTRAINT `fk_students_users`
        FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk_students_courses`
        FOREIGN KEY (`course_id`) REFERENCES `courses` (`course_id`)
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table 4: fee_assignments
-- Fee assigned to an enrolled student for a specific academic period

CREATE TABLE `fee_assignments` (
    `assignment_id` INT AUTO_INCREMENT PRIMARY KEY,
    `student_id` INT NOT NULL,
    `total_fee` DECIMAL(10, 2) NOT NULL,
    `due_date` DATE NOT NULL,
    `academic_period` VARCHAR(60) NOT NULL,
    `remarks` VARCHAR(255),
    CONSTRAINT `fk_fee_student`
        FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table 5: payments
-- Records fee payments made against a fee assignment

CREATE TABLE `payments` (
    `payment_id` INT AUTO_INCREMENT PRIMARY KEY,
    `assignment_id` INT NOT NULL,
    `amount_paid` DECIMAL(10, 2) NOT NULL,
    `payment_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `payment_method` VARCHAR(50) NOT NULL,
    `transaction_reference` VARCHAR(80) NOT NULL UNIQUE,
    `payment_status` ENUM('PAID', 'PENDING', 'PARTIAL') NOT NULL DEFAULT 'PAID',
    `receipt_number` VARCHAR(80) NOT NULL UNIQUE,
    CONSTRAINT `fk_payment_assignment`
        FOREIGN KEY (`assignment_id`) REFERENCES `fee_assignments` (`assignment_id`)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Sample Seed Data

-- 1. Insert Admin and Student User accounts (Password: admin123 / student123)
INSERT INTO `users` (`user_id`, `name`, `email`, `password`, `role`, `phone`, `created_at`) VALUES
(1, 'System Administrator', 'admin@tuition.com', 'admin123', 'ADMIN', '9876543210', NOW()),
(2, 'Rahul Sharma', 'rahul@student.com', 'student123', 'STUDENT', '9876543211', NOW()),
(3, 'Priya Patel', 'priya@student.com', 'student123', 'STUDENT', '9876543212', NOW()),
(4, 'Aman Verma', 'aman@student.com', 'student123', 'STUDENT', '9876543213', NOW()),
(5, 'Sneha Roy', 'sneha@student.com', 'student123', 'STUDENT', '9876543214', NOW());

-- 2. Insert Courses
INSERT INTO `courses` (`course_id`, `course_name`, `duration`, `total_fee`, `description`) VALUES
(1, 'Java Full Stack Development', '6 Months', 45000.00, 'Comprehensive training in Core Java, Spring, Hibernate, JSP, Servlets, and Angular/React.'),
(2, 'Python Data Science & AI', '6 Months', 42000.00, 'Data analysis with Python, NumPy, Pandas, Machine Learning, and Deep Learning basics.'),
(3, 'Web Development Bootcamp', '4 Months', 30000.00, 'HTML5, CSS3, JavaScript, Node.js, Express, and MongoDB.'),
(4, 'Cloud Computing & DevOps', '5 Months', 48000.00, 'AWS, Docker, Kubernetes, CI/CD pipelines, and Linux administration.');

-- 3. Insert Student Profiles
INSERT INTO `students` (`student_id`, `user_id`, `roll_number`, `course_id`, `address`, `admission_date`) VALUES
(1, 2, 'STU-2026-001', 1, '42 Park Street, Kolkata, West Bengal', '2026-01-10'),
(2, 3, 'STU-2026-002', 2, '15 MG Road, Bangalore, Karnataka', '2026-01-15'),
(3, 4, 'STU-2026-003', 1, '88 Civil Lines, Jaipur, Rajasthan', '2026-02-01'),
(4, 5, 'STU-2026-004', 3, '21 Linking Road, Mumbai, Maharashtra', '2026-02-10');

-- 4. Insert Fee Assignments
INSERT INTO `fee_assignments` (`assignment_id`, `student_id`, `total_fee`, `due_date`, `academic_period`, `remarks`) VALUES
(1, 1, 45000.00, '2026-04-30', 'Semester 1 (2026)', 'Java Full Stack regular batch'),
(2, 2, 42000.00, '2026-04-15', 'Semester 1 (2026)', 'Data Science weekend batch'),
(3, 3, 45000.00, '2026-05-15', 'Semester 1 (2026)', 'Java Full Stack regular batch'),
(4, 4, 30000.00, '2026-03-31', 'Term 1 (2026)', 'Web Dev Bootcamp fast track');

-- 5. Insert Sample Payments (Rahul has fully paid, Priya has partially paid, Aman & Sneha have pending)
INSERT INTO `payments` (`payment_id`, `assignment_id`, `amount_paid`, `payment_date`, `payment_method`, `transaction_reference`, `payment_status`, `receipt_number`) VALUES
(1, 1, 45000.00, '2026-01-20 11:30:00', 'UPI (Google Pay)', 'TXN-20260120-1011', 'PAID', 'REC-2026-0001'),
(2, 2, 20000.00, '2026-01-25 14:15:00', 'Net Banking (HDFC)', 'TXN-20260125-2042', 'PARTIAL', 'REC-2026-0002');
