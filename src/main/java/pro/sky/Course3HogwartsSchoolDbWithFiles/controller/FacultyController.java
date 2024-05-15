package pro.sky.Course3HogwartsSchoolDbWithFiles.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Faculty;
import pro.sky.Course3HogwartsSchoolDbWithFiles.service.impl.FacultyServiceImpl;

import java.util.Collection;

@RestController
@RequestMapping("faculty")
@Tag(name = "API для работы с факультетами")
public class FacultyController {
    private final FacultyServiceImpl service;

    public FacultyController(FacultyServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Создание факультета")
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return service.createFaculty(faculty);
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение факультета")
    public Faculty getFaculty(@PathVariable Long id) {
        return service.getFaculty(id);
    }

    @PutMapping
    @Operation(summary = "Обновление факультета")
    public Faculty updatefaculty(@RequestBody Faculty faculty) {
        return service.updateFaculty(faculty);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаление факультета")
    public ResponseEntity<String> deletefaculty(@PathVariable Long id) {
        service.deleteFaculty(id);
        return ResponseEntity.ok().body("Успешно удален");
    }

    @GetMapping("/get-by-color-or-name")
    @Operation(summary = "Получение списка факультетов по цвету или по названию")
    public Collection<Faculty> findByColorOrName(@RequestParam String color,
                                                 @RequestParam String name) {
        return service.findByColorOrName(color, name);
    }

    @GetMapping("/get-students/{faculty}")
    @Operation(summary = "Получение студентов факультета")
    public Collection<Student> findByFaculty(@PathVariable String faculty) {
        return service.findByFaculty(faculty);
    }
}
