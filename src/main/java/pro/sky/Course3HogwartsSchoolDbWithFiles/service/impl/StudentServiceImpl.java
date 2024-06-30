package pro.sky.Course3HogwartsSchoolDbWithFiles.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.Course3HogwartsSchoolDbWithFiles.exceptions.NotFoundEntityException;
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

//Сервис для работы с сущностями студенты
@Service
public class StudentServiceImpl implements StudentService {

    private Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    //Инжектим репозиторий
    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }
    //Создание студента
    @Override
    public Student createStudent(Student student) {
        logger.info("Was invoked method \"Create student\"");
        return repository.save(student);
    }
    //Получение студента по Id
    @Override
    public Student getStudent(Long id) {
        logger.info("Was invoked method \"Get student\"");
        isEntityExist(id);
        return repository.findById(id).get();
    }
    //Обновление данных студента
    @Override
    public Student updateStudent(Student student) {
        logger.info("Call method \"Update student\"");
        isEntityExist(student.getId());
        return repository.save(student);
    }
    //Удаление студента по Id
    @Override
    public void deleteStudent(Long id) {
        logger.info("Was invoked method \"Delete student\"");
        isEntityExist(id);
        repository.deleteById(id);
    }
    //Поиск студентов по диапазону возрастов
    @Override
    public Collection<Student> findByAgeBetween(int val1, int val2) {
        logger.info("Was invoked method \"Find student with age between two values\"");
        return repository.findByAgeBetween(val1, val2);
    }
    //Получение факультета студента
    @Override
    public Faculty getStudentFaculty(String student) {
        logger.info("Was invoked method \"Get student faculty\"");
        return repository.findByNameContainsIgnoreCase(student).getFaculty();
    }
    //Проверка наличия сущности в БД
    private void isEntityExist(Long id) {
        logger.info("Was invoked method for check student exist");
        repository.findById(id).orElseThrow(() -> new NotFoundEntityException());
        logger.error("This student with id {} is not found", id);
    }
    //Подсчет обзего количества студентов
    @Override
    public Integer getCountStudents() {
        logger.info("Was invoked method \"Get count students\"");
        return repository.getCountStudents();
    }
    //Вычисление среднего аифметического значения возрастов студентов
    @Override
    public Double getAvgAgeStudents() {
        logger.info("Was invoked method \"Get AVG age students\"");
        return repository.getAvgAgeStudents();
    }
    //Получение списка последних 5ти студентов
    @Override
    public List<StudentsByRequest> getLastFiveStudents() {
        logger.info("Was invoked method \"Get last five students\"");
        return repository.getLastFiveStudents();
    }
    //Получение студентов на букву "А"
    @Override
    public List<String> findAllBeginA() {
        logger.info("Was invoked method \"Find all begin with A\"");
        List<Student> students = repository.findAll();
        return students.stream()
                .sorted(Comparator.comparing(e -> e.getName()))
                .filter(e -> e.getName().startsWith("A"))
                .map(e -> e.getName().toUpperCase())
                .collect(Collectors.toList());
    }
    //Вычисление среднего аифметического значения возрастов студентов
    @Override
    public Double getAvgAge() {
        logger.info("Was invoked method \"Get AVG age\"");
        List<Student> students = repository.findAll();
        return students.stream()
                .mapToDouble(e -> e.getAge())
                .average().orElse(00);
    }
    //Вычисление времени арифметической операции
    @Override
    public Integer getReduceResult() {
        logger.info("Was invoked method \"Get reduce result\"");
        Long t1 = System.currentTimeMillis();
        Integer res = IntStream.iterate(1, a -> a + 1).limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
        Long t2 = System.currentTimeMillis();
        System.out.println("Время выполнения1: " + (t2 - t1));
        return res;
    }
    //Параллельный вывод списка студентов. В метод передаются части списка
    @Override
    public void printParallel() {
        logger.info("Was invoked method \"Print parallel students\"");
        List<Student> students = repository.findAll();
        students.subList(0, 2).forEach(e -> System.out.println(e.getName()));
        printStudentsThread(students.subList(2, 4));
        printStudentsThread(students.subList(4, 6));
    }
    //Синхронизированный вывод списка студентов
    @Override
    public void printSynchronized() {
        logger.info("Was invoked method \"Print synchronized students\"");
        List<Student> students = repository.findAll();
        students.subList(0, 2).forEach(e -> System.out.println(e.getName()));
        printStudentsThreadSync(students.subList(2, 4));
        printStudentsThreadSync(students.subList(4, 6));
    }
    //Метод для параллельного вывода. Создание потока и вывод в нем списка из аргумента метода
    private void printStudentsThread(List<Student> students) {
        new Thread(() -> {
            students.forEach(e -> System.out.println(e.getName()));
        }).start();
    }
    //Создание потока и вызов синхронизированного метода вывода
    private void printStudentsThreadSync(List<Student> students) {
        new Thread(() -> {
            students.forEach(e -> printStudentsNameSync(e));
        }).start();
    }
    //Синхронизированный метод вывода списка студентов
    private synchronized void printStudentsNameSync(Student student) {
        System.out.println(student.getName());
    }
}