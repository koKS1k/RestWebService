package DAO;

import MODEL.Employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        IDAOFactory factory = DAOFactory.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(factory.getEmployeeDAO().getAllEmployees().toString());

        try {
            Employee employee = new Employee(13, "Logistics", "Ismagilov Nikita Rafailovich", formatter.parse("1978-10-03"), 75000);


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
