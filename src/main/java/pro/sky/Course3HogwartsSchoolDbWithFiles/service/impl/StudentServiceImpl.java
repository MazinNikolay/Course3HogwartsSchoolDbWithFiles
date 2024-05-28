package pro.sky.Course3HogwartsSchoolDbWithFiles.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.Course3HogwartsSchoolDbWithFiles.exceptioms.NotFoundEntityException;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Faculty;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Student;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.StudentsByRequest;
import pro.sky.Course3HogwartsSchoolDbWithFiles.repository.StudentRepository;
import pro.sky.Course3HogwartsSchoolDbWithFiles.service.StudentService;

import java.util.Collection;
import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {

    Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student createStudent(Student student) {
        logger.info("Call method \"Create student\"");
        return repository.save(student);
    }

    @Override
    public Student getStudent(Long id) {
        logger.info("Call method \"Get student\"");
        isEntityExist(id);
        return repository.findById(id).get();
    }

    @Override
    public Student updateStudent(Student student) {
        logger.info("Call method \"Update student\"");
        isEntityExist(student.getId());
        return repository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        logger.info("Call method \"Delete student\"");
        isEntityExist(id);
        repository.deleteById(id);
    }

    @Override
    public Collection<Student> findByAgeBetween(int val1, int val2) {
        logger.info("Call method \"Find student with age between two values\"");
        return repository.findByAgeBetween(val1, val2);
    }

    @Override
    public Faculty getStudentFaculty(String student) {
        logger.info("Call method \"Get student faculty\"");
        return repository.findByNameContainsIgnoreCase(student).getFaculty();
    }

    private void isEntityExist(Long id) {
        logger.info("Was invoked method for check student exist");
        repository.findById(id).orElseThrow(() -> new NotFoundEntityException());
        logger.error("This student is not found. id=" + id);
    }

    public Integer getCountStudents() {
        logger.info("Call method \"Get count students\"");
        return repository.getCountStudents();
    }

    public Double getAvgAgeStudents() {
        logger.info("Call method \"Get AVG age students\"");
        return repository.getAvgAgeStudents();
    }

    public List<StudentsByRequest> getLastFiveStudents() {
        logger.info("Call method \"Get last five students\"");
        return repository.getLastFiveStudents();
    }
}