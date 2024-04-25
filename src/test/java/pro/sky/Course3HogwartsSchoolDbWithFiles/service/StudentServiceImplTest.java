package pro.sky.Course3HogwartsSchoolDbWithFiles.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Faculty;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Student;
import pro.sky.Course3HogwartsSchoolDbWithFiles.repository.StudentRepository;
import pro.sky.Course3HogwartsSchoolDbWithFiles.service.impl.StudentServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    private StudentServiceImpl out;
    private Faculty faculty;

    @Mock
    private StudentRepository repository;

    @BeforeEach
    void initial() {
        faculty = new Faculty(1L, "Griffindor", "Yellow");
        out = new StudentServiceImpl(repository);
        Student student = new Student(1L, "Tolik", 14);
        out.createStudent(student);
    }

    @Test
    void createStudent() {
        Student expected = new Student(1L, "Oleg", 14);
        when(repository.save(any())).thenReturn(expected);
        Student actual = out.createStudent(expected);
        assertEquals(expected, actual);
    }

    @Test
    void getStudent() {
        Student expected = new Student(1L, "Tolik", 14);
        when(repository.findById(any())).thenReturn(Optional.of(expected));
        Student actual = out.getStudent(1L);
        assertEquals(expected, actual);
    }

    @Test
    void updateStudent() {
        Student expected = new Student(1L, "Oleg", 14);
        when(repository.findById(any())).thenReturn(Optional.of(expected));
        when(repository.save(any())).thenReturn(expected);
        Student actual = out.updateStudent(expected);
        assertEquals(expected, actual);
    }

    @Test
    void deleteStudent() {
        Student expected = new Student(1L, "Tolik", 14);
        when(repository.findById(any())).thenReturn(Optional.of(expected));
        out.deleteStudent(1L);
        verify(repository).deleteById(any());
    }
}