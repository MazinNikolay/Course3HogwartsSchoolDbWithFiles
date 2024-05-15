package pro.sky.Course3HogwartsSchoolDbWithFiles.service.impl;

import org.springframework.stereotype.Service;
import pro.sky.Course3HogwartsSchoolDbWithFiles.exceptioms.NotFoundEntityException;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Faculty;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Student;
import pro.sky.Course3HogwartsSchoolDbWithFiles.repository.StudentRepository;
import pro.sky.Course3HogwartsSchoolDbWithFiles.service.StudentService;

import java.util.Collection;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student createStudent(Student student) {
        return repository.save(student);
    }

    @Override
    public Student getStudent(Long id) {
        isEntityExist(id);
        return repository.findById(id).get();
    }

    @Override
    public Student updateStudent(Student student) {
        isEntityExist(student.getId());
        return repository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        isEntityExist(id);
        repository.deleteById(id);
    }

    @Override
    public Collection<Student> findByAgeBetween(int val1, int val2) {
        return repository.findByAgeBetween(val1, val2);
    }

    @Override
    public Faculty getStudentFaculty(String student) {
        return repository.findByNameContainsIgnoreCase(student).getFaculty();
    }

    private void isEntityExist(Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundEntityException());
    }

    public Integer getCountStudents() {
        return repository.getCountStudents();
    }

    public Double getAvgAgeStudents() {
        return repository.getAvgAgeStudents();
    }

    public List<StudentsByRequest> getLastFiveStudents() {
        return repository.getLastFiveStudents();
    }
}
