package pro.sky.Course3HogwartsSchoolDbWithFiles.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Faculty;

import java.util.Collection;

@RestController
@RequestMapping("student")
@Tag(name = "API для работы со студентами")
public class StudentController {
    private final StudentServiceImpl service;

    public StudentController(StudentServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Создание студента")
    public Student createStudent(@RequestBody Student student) {
        return service.createStudent(student);
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение студента")
    public Student getStudent(@PathVariable Long id) {
        return service.getStudent(id);
    }

    @PutMapping
    @Operation(summary = "Обновление студента")
    public Student updateStudent(@RequestBody Student student) {
        return service.updateStudent(student);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление студента")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        service.deleteStudent(id);
        return ResponseEntity.ok().body("Успешно удален");
    }

    @GetMapping("/get-by-age-between")
    @Operation(summary = "Получение списка студентов по диапазону возраста")
    public Collection<Student> findByAgeBetween(@RequestParam int val1,
                                                @RequestParam int val2) {
        return service.findByAgeBetween(val1, val2);
    }

    @GetMapping("/faculty/{student}")
    @Operation(summary = "Получение факультета студента")
    public Faculty getStudentFaculty(@PathVariable String student) {
        return service.getStudentFaculty(student);
    }
}
