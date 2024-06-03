package pro.sky.Course3HogwartsSchoolDbWithFiles.service;

import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Faculty;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Student;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.StudentsByRequest;

import java.util.Collection;
import java.util.List;

public interface StudentService {
    Student createStudent(Student student);

    Student getStudent(Long id);

    Student updateStudent(Student student);

    void deleteStudent(Long id);

    Collection<Student> findByAgeBetween(int val1, int val2);

    Faculty getStudentFaculty(String student);

    Integer getCountStudents();

    Double getAvgAgeStudents();

    List<StudentsByRequest> getLastFiveStudents();

    List<String> findAllBeginA();

    Double getAvgAge();

    Integer getReduceResult();

    void printParallel();

    void printSynchronized();
}
