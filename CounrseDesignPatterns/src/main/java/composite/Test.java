package composite;

/**
 * Created by cloud on 2019/12/20.
 */
public class Test {

    public static void main(String[] args) {
        Employee CEO = new Employee("John", "CEO");

        Employee headSales = new Employee("Robert", "Head Sales");
        Employee headSales2 = new Employee("Michel", "Head Sales");

        Employee salesExecutive1 = new Employee("Richard", "Sales");
        Employee salesExecutive2 = new Employee("Rob", "Sales");
        CEO.addSubEmployees(headSales);
        CEO.addSubEmployees(headSales2);
        headSales.addSubEmployees(salesExecutive1);
        headSales.addSubEmployees(salesExecutive2);
        printAll(CEO);
    }

    public static void printAll(Employee employee) {
        for (int i = 0; i < employee.getLevel(); i++) {
            System.out.print("  ");
        }
        System.out.println(employee.name + "-" + employee.dep);
        employee.getSubEmployees().forEach(employee1 -> {
            printAll(employee1);
        });

    }


}
