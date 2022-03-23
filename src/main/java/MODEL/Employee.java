package MODEL;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

 /*
        Создаем класс Employee для создания сотрудника.
        Поля совпадают с полями в БД. Используем lombok
        для избежания шаблонного кода.
  */

@Setter
@Getter
@ToString
@XmlRootElement  //Необходим для отправки/получения объекта в/из JSON через REST.
@EqualsAndHashCode

public class Employee {
    private long id;
    private String department;
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Yekaterinburg")
    private Date date;
    private long salary;


    public Employee(long id, String department, String name, Date date, long salary) {
        this.id = id;
        this.department = department;
        this.name = name;
        this.date = date;
        this.salary = salary;
    }

    public Employee() {
    }
}
