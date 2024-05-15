package pro.sky.Course3HogwartsSchoolDbWithFiles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Student;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.StudentsByRequest;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(int val1, int val2);

    Student findByNameContainsIgnoreCase(String name);

    @Query(value = "SELECT COUNT(*) as count_all FROM student", nativeQuery = true)
    Integer getCountStudents();

    @Query(value = "SELECT AVG(age) as avg_age FROM student", nativeQuery = true)
    Double getAvgAgeStudents();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<StudentsByRequest> getLastFiveStudents();
}
