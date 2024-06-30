package pro.sky.Course3HogwartsSchoolDbWithFiles.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Objects;
//Сущность для записи в БД студента
@Entity
public class Student {
    //обозначаем идентификатор
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Связь много к одному с идентификатором факультета
    @ManyToOne
    @JoinColumn(name = "faculty_id")
    //Поле пропускается при сериализации
    @JsonBackReference
    private Faculty faculty;

    private String name;
    private int age;

    public Student() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age && Objects.equals(id, student.id) && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
