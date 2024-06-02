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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


@Service
public class StudentServiceImpl implements StudentService {

    private Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Student createStudent(Student student) {
        logger.info("Was invoked method \"Create student\"");
        return repository.save(student);
    }

    @Override
    public Student getStudent(Long id) {
        logger.info("Was invoked method \"Get student\"");
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
        logger.info("Was invoked method \"Delete student\"");
        isEntityExist(id);
        repository.deleteById(id);
    }

    @Override
    public Collection<Student> findByAgeBetween(int val1, int val2) {
        logger.info("Was invoked method \"Find student with age between two values\"");
        return repository.findByAgeBetween(val1, val2);
    }

    @Override
    public Faculty getStudentFaculty(String student) {
        logger.info("Was invoked method \"Get student faculty\"");
        return repository.findByNameContainsIgnoreCase(student).getFaculty();
    }

    private void isEntityExist(Long id) {
        logger.info("Was invoked method for check student exist");
        repository.findById(id).orElseThrow(() -> new NotFoundEntityException());
        logger.error("This student with id {} is not found", id);
    }

    public Integer getCountStudents() {
        logger.info("Was invoked method \"Get count students\"");
        return repository.getCountStudents();
    }

    public Double getAvgAgeStudents() {
        logger.info("Was invoked method \"Get AVG age students\"");
        return repository.getAvgAgeStudents();
    }

    public List<StudentsByRequest> getLastFiveStudents() {
        logger.info("Was invoked method \"Get last five students\"");
        return repository.getLastFiveStudents();
    }

    public List<String> findAllBeginA() {
        List<Student> students = repository.findAll();
        return students.stream()
                .sorted(Comparator.comparing(e -> e.getName()))
                .filter(e -> e.getName().startsWith("A"))
                .map(e -> e.getName().toUpperCase())
                .collect(Collectors.toList());
    }

    public Double getAvgAge() {
        List<Student> students = repository.findAll();
        return students.stream()
                .mapToDouble(e -> e.getAge())
                .average().orElse(00);
    }

    public Integer getReduceResult() {
        Long t1 = System.currentTimeMillis();
        Integer res = IntStream.iterate(1, a -> a + 1).limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        Long t2 = System.currentTimeMillis();
        System.out.println("Время выполнения1: " + (t2 - t1));
        return res;
    }
}