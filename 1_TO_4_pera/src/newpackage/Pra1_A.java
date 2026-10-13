package newpackage;
import java.sql.*;
public class Pra1_A {
    public static void main(String[] args) {
        try {

            String url = "jdbc:mysql://localhost:3306/ev_1";
            String user = "root";
            String password = "";

            Connection con = DriverManager.getConnection(url, user, password);

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM emp");

            while (rs.next()) {

                System.out.println(
                        rs.getInt("Id") + " "
                        + rs.getString("name") + " "
                        + rs.getString("city") + " "
                        + rs.getInt("age"));

            }

            rs.close();
            stmt.close();
            con.close();

        } catch (SQLException e) {

            System.out.println(e);

        }

    }
}