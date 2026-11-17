package demo;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        StudentDAO dao = new StudentDAO();

        // ========================================================
        // 1. INSERT
        // ========================================================

        System.out.println("===== INSERT =====");

        Student newStudent =
                new Student(
                        4,
                        "Neha",
                        "neha@gmail.com",
                        22
                );

        dao.insert(newStudent);


        // ========================================================
        // 2. SELECT
        // ========================================================

        System.out.println("\n===== SELECT =====");

        List<Student> students = dao.getAllStudents();

        for (Student student : students) {

            /*
             * JDBC originally gives us rows.
             *
             * StudentDAO manually converts those rows
             * into Java Student objects.
             */

            System.out.println(student);
        }


        // ========================================================
        // 3. UPDATE
        // ========================================================

        System.out.println("\n===== UPDATE =====");

        Student updatedStudent =
                new Student(
                        4,
                        "Neha Patel",
                        "neha.patel@gmail.com",
                        23
                );

        dao.update(updatedStudent);


        // ========================================================
        // 4. DELETE
        // ========================================================

        System.out.println("\n===== DELETE =====");

        dao.delete(4);


        // ========================================================
        // CLASSROOM DISCUSSION
        // ========================================================

//        System.out.println();
//        System.out.println("==============================================");
//        System.out.println("JDBC LIMITATIONS");
//        System.out.println("==============================================");
//        System.out.println("1. SQL must be written manually.");
//        System.out.println("2. ResultSet rows must be processed manually.");
//        System.out.println("3. Row -> Object mapping is manual.");
//        System.out.println("4. Object -> SQL parameter mapping is manual.");
//        System.out.println("5. CRUD contains repetitive JDBC code.");
//        System.out.println("6. Java objects and database tables are separate models.");
//        System.out.println("7. Large applications produce significant boilerplate.");
//        System.out.println();
//        System.out.println("NEXT QUESTION:");
//        System.out.println("Can a framework automatically map");
//        System.out.println("Java Objects <-> Database Rows?");
//        System.out.println();
//        System.out.println("Answer: ORM frameworks such as Hibernate.");
//        System.out.println("==============================================");
    }
}
