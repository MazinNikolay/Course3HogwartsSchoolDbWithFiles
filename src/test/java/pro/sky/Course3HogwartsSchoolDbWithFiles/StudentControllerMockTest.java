package pro.sky.Course3HogwartsSchoolDbWithFiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.Course3HogwartsSchoolDbWithFiles.controller.StudentController;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Faculty;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Student;
import pro.sky.Course3HogwartsSchoolDbWithFiles.repository.AvatarRepository;
import pro.sky.Course3HogwartsSchoolDbWithFiles.repository.FacultyRepository;
import pro.sky.Course3HogwartsSchoolDbWithFiles.repository.StudentRepository;
import pro.sky.Course3HogwartsSchoolDbWithFiles.service.impl.AvatarServiceImpl;
import pro.sky.Course3HogwartsSchoolDbWithFiles.service.impl.FacultyServiceImpl;
import pro.sky.Course3HogwartsSchoolDbWithFiles.service.impl.StudentServiceImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class StudentControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @SpyBean
    private StudentServiceImpl service;

    @SpyBean
    private FacultyServiceImpl facultyService;

    @SpyBean
    private AvatarServiceImpl avatarService;

    @InjectMocks
    private StudentController controller;

    private ObjectMapper mapper = new ObjectMapper();
    private Student student;
    private Collection<Student> students;

    @BeforeEach
    void initial() {
        student = new Student();
        Long id = 1L;
        String name = "Gennady";
        int age = 15;
        student.setId(id);
        student.setName(name);
        student.setAge(age);
        students = Collections.singletonList(student);
    }

    @Test
    public void postStudentTest() throws Exception {
        Long id = 1L;
        String name = "Gennady";
        int age = 15;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("age", age);

        when(studentRepository.save(any(Student.class))).thenReturn(student);
        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void getStudentTest() throws Exception {
        Long id = 1L;
        String name = "Gennady";
        int age = 15;

        when(studentRepository.findById(any(Long.class))).thenReturn(Optional.of(student));

        mockMvc.perform((MockMvcRequestBuilders
                        .get("/student/1", Student.class)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void deleteStudentTest() throws Exception {
        Long id = 1L;
        String name = "Gennady";
        int age = 15;

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void putStudentTest() throws Exception {
        Long id = 1L;
        String name = "Gennady";
        int age = 15;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("age", age);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void getStudentsByAgeBetween() throws Exception {
        when(studentRepository.findByAgeBetween(any(Integer.class), any(Integer.class))).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/get-by-age-between?val1=13&val2=15")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(students)));
    }

    @Test
    void getFacultyStudentTest() throws Exception {
        Faculty faculty = new Faculty(1L, "Hogwarts", "Red");
        student.setFaculty(faculty);
        when(studentRepository.findByNameContainsIgnoreCase(any(String.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/faculty/Garry")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()));
    }
}
