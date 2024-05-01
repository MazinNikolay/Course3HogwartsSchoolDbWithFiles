package pro.sky.Course3HogwartsSchoolDbWithFiles.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Faculty;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Student;
import pro.sky.Course3HogwartsSchoolDbWithFiles.repository.FacultyRepository;
import pro.sky.Course3HogwartsSchoolDbWithFiles.service.impl.FacultyServiceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTestImpl {
    private FacultyServiceImpl out;

    Collection<Student> students;

    @Mock
    private FacultyRepository repository;

    @BeforeEach
    void initial() {
        out = new FacultyServiceImpl(repository);
        Student student1 = new Student(1L, "Tolik", 14);
        Student student2 = new Student(2L, "Oleg", 14);
        students = new ArrayList<>(List.of(student1, student2));
        Faculty faculty = new Faculty(1L, "Griffindor", "yellow");
        out.createFaculty(faculty);
    }

    @Test
    void createfaculty() {
        Faculty expected = new Faculty(1L, "Griffindor", "yellow");
        when(repository.save(any())).thenReturn(expected);
        Faculty actual = out.createFaculty(expected);
        assertEquals(expected, actual);
    }

    @Test
    void getfaculty() {
        Faculty expected = new Faculty(1L, "Griffindor", "yellow");
        when(repository.findById(any())).thenReturn(Optional.of(expected));
        Faculty actual = out.getFaculty(1L);
        assertEquals(expected, actual);
    }

    @Test
    void updatefaculty() {
        Faculty expected = new Faculty(1L, "Griffindor", "yellow");
        when(repository.findById(any())).thenReturn(Optional.of(expected));
        when(repository.save(any())).thenReturn(expected);
        Faculty actual = out.updateFaculty(expected);
        assertEquals(expected, actual);
    }

    @Test
    void deletefaculty() {
        Faculty expected = new Faculty(1L, "Griffindor", "yellow");
        when(repository.findById(any())).thenReturn(Optional.of(expected));
        out.deleteFaculty(1L);
        verify(repository).deleteById(any());
    }
}