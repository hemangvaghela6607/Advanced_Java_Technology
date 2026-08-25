/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpackage.Extra_Codes.newpackage;

/**
 *
 * @author ADMIN
 */
//public class product {
//    
//}
//
import java.sql.*;
import java.util.Scanner;
public class product {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try{
            String url = "jdbc:mysql://localhost:3306/ev1_94";
            String username = "root";
            String password = "";
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Product Records");
            Statement st = con.createStatement();
            int choice;
     
         do {
            System.out.println("\nEnter Your choice you want:");
            System.out.println("1.Viwe Choice");
            System.out.println("2.Insert product");
            System.out.println("3.Update Product");
            System.out.println("4.Delete Product");
            System.out.println("5.Product ID give");

            System.out.println("6.EXIT");

             System.out.print("\nEnter Your Choice: ");
                choice = sc.nextInt();
           
            switch(choice){
                case 1:
                    //Use a Statement object to display all product records from the `product` table.
                    ResultSet rs = st.executeQuery("select * from product");
                    while (rs.next()) {
                        System.out.println(
                                rs.getInt("pid") + " "
                                + rs.getString("pname") + " "
                                + rs.getString("category") + " "
                                + rs.getDouble("price"));
                    }
                    break;
                    
                case 2:
                    // Use a PreparedStatement object to insert a new product record into the `product` table.
                    System.out.println("\nEnter Product Details");
                    System.out.print("Product ID : ");
                    int pid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Product Name : ");
                    String pname = sc.nextLine();
                    System.out.print("Category : ");
                    String category = sc.nextLine();
                    System.out.print("Price : ");
                    double price = sc.nextDouble();
                    PreparedStatement psInsert = con.prepareStatement("INSERT INTO product VALUES(?,?,?,?)");
                    psInsert.setInt(1, pid);
                    psInsert.setString(2, pname);
                    psInsert.setString(3, category);
                    psInsert.setDouble(4, price);
                    int insert = psInsert.executeUpdate();
                    if(insert > 0)
                        System.out.println("Product Inserted Successfully");
                    else
                        System.out.println("Insertion Failed");
                    break;
                   
                case 3:
                    //Use a PreparedStatement object to update the price and category of a product based on the entered product ID.
                    System.out.print("\nEnter Product ID to Update : ");
                    int upid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("New Category : ");
                    String newCat = sc.nextLine();
                    System.out.print("New Price : ");
                    double newPrice = sc.nextDouble();
                    PreparedStatement psUpdate = con.prepareStatement("UPDATE product SET category=?, price=? WHERE pid=?");
                    psUpdate.setString(1, newCat);
                    psUpdate.setDouble(2, newPrice);
                    psUpdate.setInt(3, upid);
                    int update = psUpdate.executeUpdate();
                    if(update > 0)
                        System.out.println("Product Updated Successfully");
                    else
                        System.out.println("Product Not Found");
                    break;
                    
                case 4:
                    //Use a PreparedStatement object to delete a product record based on the entered product ID.
                    System.out.print("\nEnter Product ID to Delete : ");
                    int delid = sc.nextInt();
                    PreparedStatement psDelete = con.prepareStatement("DELETE FROM product WHERE pid=?");
                    psDelete.setInt(1, delid);
                    int delete = psDelete.executeUpdate();
                    if(delete > 0)
                        System.out.println("Product Deleted Successfully");
                    else
                        System.out.println("Product Not Found");
                    break;
                    
                    
                case 5:
                    //Use a CallableStatement object to create and execute a stored procedure that accepts a product ID and returns the product name and price
                     System.out.print("\nEnter Product ID to Search : ");
                    int searchId = sc.nextInt();
                    CallableStatement cs = con.prepareCall("{call GetProductDetails(?)}");
                    cs.setInt(1, searchId);
                    ResultSet rs2 = cs.executeQuery();
                    if(rs2.next()) {
                        System.out.println("\nProduct Name : " +rs2.getString("pname"));
                        System.out.println("Price : " + rs2.getDouble("price"));
                    }
                    else {
                        System.out.println("Product Not Found");
                    }
                    break;
                    
                case 6:
                    System.out.println("Thank You!");
                    break;
                default:
                    System.out.println("Invalid Choice!");
                   
            }
         }while (choice != 5);
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}
