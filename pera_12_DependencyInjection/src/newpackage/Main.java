/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpackage;

public class Main {
    public static void main(String[] args) {

        // Constructor Injection
        Student s1 = new Student("Hemang");
        System.out.println("Constructor Injection:");
        s1.display();

        // Setter Injection
        Student s2 = new Student("Hello");
        System.out.println("\nSetter Injection:");
        s2.setName("Hello");
        s2.display();
    }
}
