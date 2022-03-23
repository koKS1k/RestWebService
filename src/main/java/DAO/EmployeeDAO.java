package DAO;

import MODEL.Department;
import MODEL.Employee;

import java.sql.Connection;
import java.util.List;

//Интерфейс для работы с DAO.
public interface EmployeeDAO {

    List<Employee> getAllEmployees();

    List<Department> getAllDepartments();



    void addEmployee(Employee employee);

    int getDepartmentId(String departmentName, Connection connection);

    void update(Employee employee);

    void remove(int id);


}
