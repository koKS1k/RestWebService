package DAO;

import MODEL.Department;
import MODEL.Employee;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

//Основной класс для работы с базой данных
public class EmployeeJDBCDAO implements EmployeeDAO {

    public EmployeeJDBCDAO() {
    }

    //Метод для получения всех сотрудников
    @Override
    public List<Employee> getAllEmployees()
    {
        //Лист,в который будем заносить сотрудников
        ArrayList<Employee> allEmployees = new ArrayList<>();

        //Создаем ссылку connection для доступа к БД.
        Connection connection = null;
        try
        {
            //Получаем соединение с БД из метода getConnection.
            connection = this.getConnection();

            //Получаем PreparedStatement из connection. Формируем SQL запрос в БД.
            PreparedStatement statement = connection.prepareStatement("SELECT e.id, d.Name, e.name, e.date_of_birth, e.salary FROM employees as e " +
                    "INNER JOIN departments as d ON d.id = e.department_id ");

            //Исполняем запрос. Полученный результат записываем в ResultSet.
            ResultSet rs = statement.executeQuery();
            //Считываем построчно полученный результат.
            while (rs.next())
            {
                //Создаем сотрудника
                Employee employee = new Employee(
                        //id
                        rs.getLong(1),
                        //Отдел
                        rs.getString(2),
                        //Имя
                        rs.getString(3),
                        //Дата рождения
                        rs.getDate(4),
                        //Зарплата
                        rs.getLong(5));
                //Добавляем сотрудника в список.
                allEmployees.add(employee);
            }
            rs.close();
            statement.close();
            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return allEmployees;
    }

    //Метод для получения всех отделов
    @Override
    public List<Department> getAllDepartments()
    {
        //Лист,в который будем заносить сотрудников
        ArrayList<Department> allDepartments = new ArrayList<>();

        //Создаем ссылку connection для доступа к БД.
        Connection connection = null;
        try
        {
            //Получаем соединение с БД из метода getConnection.
            connection = this.getConnection();

            //Получаем PreparedStatement из connection. Формируем SQL запрос в БД.
            PreparedStatement statement = connection.prepareStatement("SELECT id, Name FROM departments");

            //Исполняем запрос. Полученный результат записываем в ResultSet.
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                //Создаем отдел
                Department department = new Department(
                        //id
                        rs.getLong(1),
                        //Название отдела
                        rs.getString(2));
                allDepartments.add(department);
            }
            rs.close();
            statement.close();
            connection.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return allDepartments;
    }

    //Метод для добавления новго сотрудника
    @Override
    public void addEmployee(Employee employee)
    {
        //Создаем ссылку connection для доступа к БД.
        Connection connection = null;

        try
        {
            //Получаем connection
            connection = this.getConnection();

            //Получаем id отдела
            int departmentId = this.getDepartmentId(employee.getDepartment(), connection);

            //Создаем ссылку PreparedStatement для дальнейшего формирования запросов.
            PreparedStatement statement;

            //Если такого id нет (такого отдела нет), то создаем его.
            if (departmentId == -1)
            {
                //Формируем SQL запрос на добавление новго сотрудника.
                statement = connection.prepareStatement("INSERT INTO departments(name) VALUES (?)");

                //Название отдела берем из данных сотрудника
                statement.setString(1, employee.getDepartment());

                //Исполняем запрос
                statement.execute();

                //Формируем SQL запрос на получение максимального id (нового id отдела).
                statement = connection.prepareStatement("SELECT MAX(id) FROM departments");

                //Исполняем запрос
                ResultSet rs = statement.executeQuery();
                rs.next();

                //Назначаем новый id отделу
                departmentId = rs.getInt(1);

                rs.close();
            }

            //Формируем SQL запрос на добавление нового сотрудника
            statement = connection.prepareStatement("INSERT INTO employees(department_id, name, date_of_birth, salary) VALUES (?, ?, ?, ?)");

            //id отдела.
            statement.setInt(1, departmentId);

            //Имя сотрудника
            statement.setString(2, employee.getName());

            //Дата рождения
            statement.setDate(3, new java.sql.Date(employee.getDate().getTime()));

            //Зарплата
            statement.setLong(4, employee.getSalary());

            //Исполняем запрос
            statement.execute();

            statement.close();
            connection.close();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }


    @Override
    public int getDepartmentId(String departmentName, Connection connection)
    {
        try
        {
            //Формируем запрос в preparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM departments WHERE name = ?");

            //Устанавливаем имя отдела в запрос
            preparedStatement.setString(1, departmentName);

            //Исполняем запрос. Результат записываем в ResultSet
            ResultSet resultSet = preparedStatement.executeQuery();

            //Если такой отдел есть, то возвращаем его индекс.
            if (resultSet.next())
                return resultSet.getInt(1);

        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        //Если нет, то возвращаем -1
        return -1;
    }

    //Метод для изменения данных сотрудника
    @Override
    public void update(Employee employee)
    {
        //Создаем ссылку connection для доступа к БД.
        Connection connection = this.getConnection();
        try
        {
            //Получаем id отдела
            int departmentId = this.getDepartmentId(employee.getDepartment(), connection);

            //Формируем запрос в preparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE employees SET department_id = ?, name = ?, date_of_birth = ?, salary = ? WHERE id = ?");
            //id отдела
            preparedStatement.setInt(1, departmentId);

            //Имя сотрудника
            preparedStatement.setString(2, employee.getName());

            //Дата рождения
            preparedStatement.setDate(3, new java.sql.Date(employee.getDate().getTime()));

            //Зарплата
            preparedStatement.setLong(4, employee.getSalary());

            //id Сотрудника
            preparedStatement.setLong(5, employee.getId());

            //Исполняем запрос
            preparedStatement.executeUpdate();
            connection.close();

        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }

    }

    //Метод для удаления сотрудника по id
    @Override
    public void remove(int id)
    {
        //Создаем ссылку connection для доступа к БД.
        Connection connection = this.getConnection();
        try
        {
            //Формируем запрос в preparedStatement
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM employees WHERE id= ?");

            //id сотрудника
            preparedStatement.setLong(1, id);

            //Исполняем запрос
            preparedStatement.executeUpdate();

        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

    //Метод для получения Connection для базы данных
    private Connection getConnection() {

        try
        {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root", "root");
        }
        catch (SQLException throwable)
        {
            throwable.printStackTrace();
            return null;
        }
    }
}
