package pro.sky.Course3HogwartsSchoolDbWithFiles.service;

import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Faculty;

import java.util.Collection;

public interface StudentService {
    Student createStudent(Student student);

    Student getStudent(Long id);

    Student updateStudent(Student student);

    void deleteStudent(Long id);

    Collection<Student> findByAgeBetween(int val1, int val2);

    Faculty getStudentFaculty(String student);
}
