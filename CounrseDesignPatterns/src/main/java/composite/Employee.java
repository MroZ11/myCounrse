package composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cloud on 2019/12/20.
 */
public class Employee {

    //这个空格主要用展示用
    int level=0;
    String name;
    String dep;

    private List<Employee> subEmployees = new ArrayList<>();

    public Employee(String name, String dep) {
        this.name = name;
        this.dep = dep;

    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Employee> getSubEmployees() {
        return subEmployees;
    }

    public void addSubEmployees(Employee employee){
        employee.setLevel(this.level+1);
        this.subEmployees.add(employee);
    }
}
