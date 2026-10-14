package demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // ============================================================
    // INSERT
    // ============================================================

    public void insert(Student student) {

        String sql =
                "INSERT INTO student (id, name, email, age) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            /*
             * IMPORTANT CLASSROOM POINT:
             *
             * JDBC does NOT automatically map a Student object
             * to a database row.
             *
             * We have to manually extract every property.
             *
             * Student Object
             *      |
             *      +--> getId()
             *      +--> getName()
             *      +--> getEmail()
             *      +--> getAge()
             *      |
             *      v
             * Database Row
             */

            ps.setInt(1, student.getId());
            ps.setString(2, student.getName());
            ps.setString(3, student.getEmail());
            ps.setInt(4, student.getAge());

            ps.executeUpdate();

            System.out.println("Student inserted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ============================================================
    // SELECT ALL
    // ============================================================

    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();

        String sql =
                "SELECT id, name, email, age FROM student";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                /*
                 * IMPORTANT:
                 *
                 * ResultSet represents a DATABASE ROW.
                 * Java application wants a STUDENT OBJECT.
                 *
                 * JDBC does not automatically perform this conversion.
                 */

                Student student = mapRowToStudent(rs);

                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    // ============================================================
    // MANUAL ROW -> OBJECT MAPPING
    // ============================================================

    private Student mapRowToStudent(ResultSet rs) throws SQLException {

        /*
         * THIS IS THE MOST IMPORTANT METHOD FOR YOUR
         * HIBERNATE INTRODUCTION.
         *
         * Database Row:
         *
         * id | name | email | age
         *  1 | Rahul| ...   | 20
         *
         * We manually convert it into:
         *
         * Student Object:
         * id = 1
         * name = Rahul
         * email = ...
         * age = 20
         */

        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        int age = rs.getInt("age");

        // Manual Row -> Object mapping
        return new Student(id, name, email, age);
    }

    // ============================================================
    // UPDATE
    // ============================================================

    public void update(Student student) {

        String sql =
                "UPDATE student "
                + "SET name = ?, email = ?, age = ? "
                + "WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            /*
             * Again, we manually map Java object properties
             * to SQL parameters.
             */

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setInt(3, student.getAge());
            ps.setInt(4, student.getId());

            ps.executeUpdate();

            System.out.println("Student updated successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ============================================================
    // DELETE
    // ============================================================

    public void delete(int id) {

        String sql =
                "DELETE FROM student WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("Student deleted successfully.");

        } catch (SQLException e) {
        }
    }
}
