/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpackage;
import java.sql.*;

import java.util.Scanner;  
public class Pra2_A {

    static Scanner sc = new Scanner(System.in);

    public static void main(String args[]) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ev_1","root","");
            System.out.println("Enter Employee ID:");
            int id = sc.nextInt();
            CallableStatement cs = con.prepareCall("CALL getcity(?, ?)");
            cs.setInt(1, id);
            cs.registerOutParameter(2, Types.VARCHAR);
            cs.execute();
            String city = cs.getString(2);
            if (city != null) {
                System.out.println("Employee City: " + city);
            } else {
                System.out.println("Employee not found");
            }
            cs.close();
            con.close();
            sc.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
