package pro.sky.Course3HogwartsSchoolDbWithFiles.controller;

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
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Faculty;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Student;
import pro.sky.Course3HogwartsSchoolDbWithFiles.repository.AvatarRepository;
import pro.sky.Course3HogwartsSchoolDbWithFiles.repository.FacultyRepository;
import pro.sky.Course3HogwartsSchoolDbWithFiles.service.impl.FacultyServiceImpl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class FacultyControllerMockTest {

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
    private Faculty faculty;
    private Collection<Faculty> faculties;

    @BeforeEach
    void initial() {
        faculty = new Faculty();
        Long id = 1L;
        String name = "Griffindor";
        String color = "Red";
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);
        faculties = Collections.singletonList(faculty);
    }

    @Test
    public void postFacultyTest() throws Exception {
        Long id = 1L;
        String name = "Griffindor";
        String color = "Red";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("color", color);

        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);
        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void getFacultyTest() throws Exception {
        Long id = 1L;
        String name = "Griffindor";
        String color = "Red";

        when(facultyRepository.findById(any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform((MockMvcRequestBuilders
                        .get("/faculty/1", Student.class)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void deleteFacultyTest() throws Exception {
        Long id = 1L;
        String name = "Griffindor";
        String color = "Red";

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void putFacultyTest() throws Exception {
        Long id = 1L;
        String name = "Griffindor";
        String color = "Red";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("color", color);

        when(facultyRepository.findById(anyLong())).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(jsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void getFacultyByColorOrNameTest() throws Exception {
        when(facultyRepository.findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(
                anyString(), anyString())).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/get-by-color-or-name?color=red&name=griffindor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(faculties)));
    }

    @Test
    void findByqFacultyTest() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Igor");
        student.setAge(15);
        student.setFaculty(faculty);
        List<Student> students = Collections.singletonList(student);
        faculty.setStudents(students);
        when(facultyRepository.findByNameContainsIgnoreCase(anyString())).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/get-students/Griffindor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(students)));
    }
}





