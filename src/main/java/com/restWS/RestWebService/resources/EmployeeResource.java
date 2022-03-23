package com.restWS.RestWebService.resources;

import DAO.DAOFactory;
import DAO.IDAOFactory;
import MODEL.Employee;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


// EmployeeResource-класс для обработки REST-запросов.
// Прописываем адреса endpoint'а, для доступа к REST-сервису.
@Path("/employees")
public class EmployeeResource {

    //Получаем ссылку на DAOFactory, для работы с базой данных.
    IDAOFactory factory = DAOFactory.getInstance();

    // Адрес: "api/employees"
    // Для получения списка всех сотрудников
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response allEmployees() {
        return Response.ok(factory.getEmployeeDAO().getAllEmployees()).build();
    }

    // Адрес: "api/employees/departments"
    // Для получения списка всех отделов
    @GET
    @Path("/departments")
    @Produces(MediaType.APPLICATION_JSON)
    public Response allDepartments() {
        return Response.ok(factory.getEmployeeDAO().getAllDepartments()).build();
    }

    // Адрес: "api/employees/add"
    // Для добавления нового сотрудника (Данные передаются в теле запроса в формате JSON)
    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addEmployee(Employee employee) {
        factory.getEmployeeDAO().addEmployee(employee);
        return Response.ok("Add successful!!!").build();
    }

    // Адрес: "api/employees/delete"
    // Для удаления сотрудника по id (передается в строке запроса).
    @DELETE
    @Path("/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEmployees(@QueryParam("id") int id) {
        factory.getEmployeeDAO().remove(id);
        return Response.ok("Delete successful!!!").build();
    }

    // Адрес: "api/employees/update"
    // Для редактирования данных сотрудника (Данные передаются в теле запроса в формате JSON))
    @PUT
    @Path("/update")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEmployees(Employee employee) {
        factory.getEmployeeDAO().update(employee);
        return Response.ok("Update successful!!!").build();
    }


}