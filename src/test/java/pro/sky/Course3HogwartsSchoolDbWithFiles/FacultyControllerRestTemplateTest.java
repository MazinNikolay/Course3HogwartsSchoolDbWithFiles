package pro.sky.Course3HogwartsSchoolDbWithFiles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import pro.sky.Course3HogwartsSchoolDbWithFiles.controller.FacultyController;
import pro.sky.Course3HogwartsSchoolDbWithFiles.controller.StudentController;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Faculty;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Student;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerRestTemplateTest {

    private Faculty faculty = new Faculty();

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void initial() {
        faculty.setId(1L);
        faculty.setName("Griffindor");
        faculty.setColor("Red");
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(facultyController).isNotNull();
    }

    @Test
    void getFacultyTest() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculty/1",
                String.class)).isNotNull();
    }

    @Test
    void testPostFaculty() throws Exception {
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/faculty",
                faculty, String.class)).isNotNull();
    }

    @Test
    void testPutFaculty() throws Exception {
        ResponseEntity<Faculty> responsePost = restTemplate.postForEntity("http://localhost:" + port + "/faculty",
                faculty, Faculty.class);
        Faculty newFaculty = responsePost.getBody();
        newFaculty.setName("Slytherin");
        newFaculty.setColor("Yellow");
        HttpEntity<Faculty> requestPut = new RequestEntity<>(newFaculty, HttpMethod.PUT, null);
        ResponseEntity<Faculty> response = restTemplate.exchange("http://localhost:" + port + "/faculty",
                HttpMethod.PUT, requestPut, Faculty.class);

        faculty.setId(newFaculty.getId());

        assertThat(response.getStatusCode()).isNotNull();
        assertThat(response.getBody()).isEqualTo(newFaculty);
    }

    @Test
    void deleteFacultyTest() throws Exception {
        ResponseEntity<Faculty> responcePost = restTemplate.postForEntity("http://localhost:" + port + "/faculty",
                faculty, Faculty.class);
        Long id = responcePost.getBody().getId();
        URI uri = new URI("http://localhost:" + port + "/faculty/" + id);
        restTemplate.delete(uri);
        ResponseEntity<Faculty> response = restTemplate.getForEntity("http://localhost:" + port + "/faculty" + id,
                Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getByColorOrNameTest() throws Exception {
        String name = "Griffindor";
        String color = "Yellow";
        Faculty newFaculty = new Faculty(2L, "Slytherin", "Yellow");
        facultyController.createFaculty(faculty);
        facultyController.createFaculty(newFaculty);
        Collection<Faculty> faculties = restTemplate.exchange("http://localhost:" + port
                        + "/faculty/get-by-color-or-name?color=" + color + "&name=" + name, HttpMethod.GET,
                null, new ParameterizedTypeReference<Collection<Faculty>>() {
                }).getBody();
        assertThat(faculties).isNotEmpty();
        assertThat(faculties).contains(newFaculty);
        assertThat(faculties).contains(faculty);
    }

    @Test
    void findByFacultyTest() throws Exception {
        Faculty newfaculty = new Faculty(1L, "Slitherin", "yellow");
        facultyController.createFaculty(faculty);
        facultyController.createFaculty(newfaculty);
        Student student = new Student();
        student.setId(1L);
        student.setName("Garry");
        student.setAge(14);
        student.setFaculty(faculty);
        studentController.createStudent(student);
        List<Student> students = Collections.singletonList(student);
        faculty.setStudents(students);
        facultyController.updatefaculty(faculty);
        Collection<Student> response = restTemplate.exchange("http://localhost:" + port + "/faculty/get-students"
                + "/Griffindor", HttpMethod.GET,null, new ParameterizedTypeReference<Collection<Student>>() {
        }).getBody();
        assertThat(response).isNotEmpty();
        assertThat(response).contains(student);
    }
}
