/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpackage;
import java.sql.*;
import java.util.Scanner;

public class Pra1_B {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {

            String url = "jdbc:mysql://localhost:3306/ev_1";
            String user = "root";
            String password = "";

            Connection con = DriverManager.getConnection(url, user, password); 
                System.out.println("Database Connected Successfully.");
                
                String sql = "INSERT INTO emp(id,name,city,age) VALUES(?,?,?,?)";
                
                PreparedStatement ps = con.prepareStatement(sql);
                
                System.out.print("Enter ID : ");
                int id = sc.nextInt();
                sc.nextLine();
                
                System.out.print("Enter Name : ");
                String name = sc.nextLine();
                
                System.out.print("Enter Ci&ty : ");
                String city = sc.nextLine();
                
                System.out.print("Enter Age : ");
                int age = sc.nextInt();
                
                ps.setInt(1,id);
                ps.setString(2,name);
                ps.setString(3,city);
                ps.setInt(4,age);
                
                int rows=ps.executeUpdate();
                
                if(rows>0)
                    System.out.println("Record Inserted Successfully");
                    System.out.println("Plese check your Database");
            }

        catch(Exception e){
            e.printStackTrace();
        }finally {
            sc.close();
        } 


    }
}
