package MODEL;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlRootElement;

    /*
        Создаем класс Department для создания отдела.
        Поля совпадают с полями в БД. Используем lombok
        для избежания шаблонного кода.
    */

@Getter
@Setter
@ToString
@NoArgsConstructor
@XmlRootElement
public class Department
{
   private long id;
   private String name;

    public Department(long id, String name)
    {
        this.id = id;
        this.name = name;
    }
}
