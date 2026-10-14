NETBEANS JDBC DEMO - WHY HIBERNATE IS NEEDED
==============================================

This is a proper Apache NetBeans Java Application project.

HOW TO OPEN:
1. Extract this ZIP.
2. Open NetBeans.
3. File -> Open Project.
4. Select the extracted folder:
   Hibernate_Need_JDBC_Demo
5. NetBeans should recognize it as a Java project.

DATABASE:
1. Start MySQL/XAMPP.
2. Open phpMyAdmin or MySQL client.
3. Run database.sql.
4. The project uses:
   Database: college_db
   Table: student
   Username: root
   Password: empty

If your MySQL password is different, edit:
src/demo/DBConnection.java

MYSQL JDBC DRIVER:
This project expects MySQL Connector/J.

Recommended:
- Add the MySQL Connector/J JAR to the project:
  Right-click project -> Properties -> Libraries
  -> Compile -> Add JAR/Folder

Then run:
src/demo/Main.java

IMPORTANT CLASSROOM FILE:
StudentDAO.java

Focus on:
mapRowToStudent(ResultSet rs)

This method demonstrates:
DATABASE ROW -> JAVA OBJECT

Also discuss:
Student Object -> SQL parameters

This manual mapping is one of the key reasons to introduce
Object Relational Mapping (ORM) and Hibernate.
