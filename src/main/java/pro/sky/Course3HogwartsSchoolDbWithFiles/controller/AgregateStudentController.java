package pro.sky.Course3HogwartsSchoolDbWithFiles.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.Course3HogwartsSchoolDbWithFiles.service.impl.StudentServiceImpl;

import java.util.List;

@RestController
@RequestMapping("student/agregate")
@Tag(name = "API для работы с агрегатными функциями по студентам")
public class AgregateStudentController {
    private final StudentServiceImpl service;

    public AgregateStudentController(StudentServiceImpl service) {
        this.service = service;
    }

    @GetMapping()
    @Operation(summary = "Получение колличества всех студентов")
    public Integer getCountStudents() {
        return service.getCountStudents();
    }

    @GetMapping("/avg-age")
    @Operation(summary = "Получение среднего возраста студентов")
    public Double getAvgAgeStudents() {
        return service.getAvgAgeStudents();
    }

    @GetMapping("/last-five-students")
    @Operation(summary = "Получение последних пяти студентов")
    public List<StudentsByRequest> getLastFiveStudents() {
        return service.getLastFiveStudents();
    }
}
