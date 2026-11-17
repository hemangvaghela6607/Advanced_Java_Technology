package com.example;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class MainApp {

    // Configuration + SessionFactory: created ONCE for the whole program
    private static SessionFactory factory;

    public static void main(String[] args) {
        factory = new Configuration()
                .configure()               // reads hibernate.cfg.xml
                .buildSessionFactory();
        System.out.println("SessionFactory ready\n");

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("------ EMPLOYEE MENU ------");
            System.out.println("1. Add a new employee");
            System.out.println("2. Delete an employee");
            System.out.println("3. Increase salary by 10%");
            System.out.println("4. Show all employees");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            String choice = sc.nextLine().trim();

            if (choice.equals("1")) {
                System.out.print("Name: ");
                String name = sc.nextLine().trim();
                System.out.print("Department: ");
                String dept = sc.nextLine().trim();
                System.out.print("Salary: ");
                double salary = Double.parseDouble(sc.nextLine().trim());
                addEmployee(name, dept, salary);
            } else if (choice.equals("2")) {
                System.out.print("Employee id to delete: ");
                deleteEmployee(Integer.parseInt(sc.nextLine().trim()));
            } else if (choice.equals("3")) {
                System.out.print("Employee id for 10% raise: ");
                increaseSalary(Integer.parseInt(sc.nextLine().trim()));
            } else if (choice.equals("4")) {
                showEmployees();
            } else if (choice.equals("0")) {
                break;
            } else {
                System.out.println("Invalid choice\n");
            }
        }

        factory.close();
        System.out.println("Bye!");
    }

    // a. Add a new employee  ->  INSERT
    static void addEmployee(String name, String dept, double salary) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            Employee emp = new Employee(name, dept, salary);
            session.save(emp);
            tx.commit();
            System.out.println("Employee added with id " + emp.getId() + "\n");
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Error: " + e.getMessage() + "\n");
        } finally {
            session.close();
        }
    }

    // b. Delete an existing employee  ->  SELECT, then DELETE
    static void deleteEmployee(int id) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            Employee emp = session.get(Employee.class, id);
            if (emp == null) {
                System.out.println("No employee with id " + id + "\n");
                tx.rollback();
                return;
            }
            session.delete(emp);
            tx.commit();
            System.out.println("Employee " + id + " deleted\n");
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Error: " + e.getMessage() + "\n");
        } finally {
            session.close();
        }
    }

    // c. Increase salary by 10%  ->  SELECT, then UPDATE
    static void increaseSalary(int id) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            Employee emp = session.get(Employee.class, id);
            if (emp == null) {
                System.out.println("No employee with id " + id + "\n");
                tx.rollback();
                return;
            }
            double oldSalary = emp.getSalary();
            double newSalary = Math.round(oldSalary * 1.10 * 100) / 100.0;
            emp.setSalary(newSalary);
            session.update(emp);
            tx.commit();
            System.out.printf("Salary updated: %,.2f -> %,.2f%n%n", oldSalary, newSalary);
        } catch (Exception e) {
            tx.rollback();
            System.out.println("Error: " + e.getMessage() + "\n");
        } finally {
            session.close();
        }
    }

    // Show all rows (HQL uses the class name Employee, not the table name)
    static void showEmployees() {
        Session session = factory.openSession();
        try {
            List<Employee> list = session.createQuery("from Employee", Employee.class).list();
            System.out.println(String.format("%-4s %-15s %-12s %12s", "ID", "NAME", "DEPARTMENT", "SALARY"));
            if (list.isEmpty()) System.out.println("(no employees)");
            for (Employee e : list) System.out.println(e);
            System.out.println();
        } finally {
            session.close();
        }
    }
}
