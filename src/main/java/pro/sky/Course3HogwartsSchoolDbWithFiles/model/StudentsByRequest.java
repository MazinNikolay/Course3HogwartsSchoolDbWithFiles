package pro.sky.Course3HogwartsSchoolDbWithFiles.model;
//Сущность для запроса на вывод опурядоченных последних пяти сущностей студентов, с описанием выводимых полей
public interface StudentsByRequest {
    Integer getId();

    String getName();

    Integer getAge();
}
