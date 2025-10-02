import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n===== Employee Management System =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Update Employee");
            System.out.println("3. Delete Employee");
            System.out.println("4. Show All Employees");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // consume newline
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Department: ");
                    String dept = sc.nextLine();
                    System.out.print("Enter Salary: ");
                    double salary = sc.nextDouble();
                    dao.addEmployee(new Employee(id, name, dept, salary));
                    break;

                case 2:
                    System.out.print("Enter ID to update: ");
                    int uid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter New Name: ");
                    String uname = sc.nextLine();
                    System.out.print("Enter New Department: ");
                    String udept = sc.nextLine();
                    System.out.print("Enter New Salary: ");
                    double usalary = sc.nextDouble();
                    dao.updateEmployee(new Employee(uid, uname, udept, usalary));
                    break;
                case 3:
                    System.out.print("Enter ID to delete: ");
                    int did = sc.nextInt();
                    dao.deleteEmployee(did);
                    break;
                case 4:
                    List<Employee> employees = dao.getAllEmployees();
                    System.out.println("\n--- Employee List ---");
                    for (Employee e : employees) {
                        System.out.println(e);
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
