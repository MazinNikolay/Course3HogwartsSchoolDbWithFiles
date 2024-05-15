package pro.sky.Course3HogwartsSchoolDbWithFiles.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Faculty;

import java.net.URI;
import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerRestTemplateTest {

    private Student student = new Student();

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
        student.setId(1L);
        student.setName("TestName2");
        student.setAge(15);
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
    }

    @Test
    void getStudentTest() throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/1",
                String.class)).isNotNull();
    }

    @Test
    void testPostStudent() throws Exception {
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student",
                student, String.class)).isNotNull();
    }

    @Test
    void testPutStudent() throws Exception {
        ResponseEntity<Student> responsePost = restTemplate.postForEntity("http://localhost:" + port + "/student",
                student, Student.class);
        Student newStudent = responsePost.getBody();
        newStudent.setName("Gennady");
        newStudent.setAge(14);
        HttpEntity<Student> requestPut = new RequestEntity<>(newStudent, HttpMethod.PUT, null);
        ResponseEntity<Student> response = restTemplate.exchange("http://localhost:" + port + "/student",
                HttpMethod.PUT, requestPut, Student.class);

        student.setId(newStudent.getId());

        assertThat(response.getStatusCode()).isNotNull();
        assertThat(response.getBody()).isEqualTo(newStudent);
    }

    @Test
    void deleteStudentTest() throws Exception {
        ResponseEntity<Student> responcePost = restTemplate.postForEntity("http://localhost:" + port + "/student",
                student, Student.class);
        Long id = responcePost.getBody().getId();
        URI uri = new URI("http://localhost:" + port + "/student/" + id);
        restTemplate.delete(uri);
        ResponseEntity<Student> response = restTemplate.getForEntity("http://localhost:" + port + "/student" + id,
                Student.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void getByAgeBetweenTest() throws Exception {
        int startAge = 13;
        int endAge = 15;
        Faculty faculty = new Faculty(1L, "griffindor", "red");
        studentController.createStudent(student);
        Student newStudent = new Student();
        newStudent.setId(2L);
        newStudent.setName("Garry");
        newStudent.setAge(14);
        studentController.createStudent(newStudent);
        newStudent.setFaculty(faculty);
        Collection<Student> students = restTemplate.exchange("http://localhost:" + port
                        + "/student/get-by-age-between?val1=" + startAge + "&val2=" + endAge, HttpMethod.GET,
                null, new ParameterizedTypeReference<Collection<Student>>() {
                }).getBody();
        assertThat(students).isNotEmpty();
        assertThat(students).contains(newStudent);
    }

    @Test
    void getFacultyTest() throws Exception {
        Faculty faculty = new Faculty(1L, "slitherin", "yellow");
        facultyController.createFaculty(faculty);
        Student newStudent = new Student();
        newStudent.setId(2L);
        newStudent.setName("Garry");
        newStudent.setAge(14);
        newStudent.setFaculty(faculty);
        studentController.createStudent(newStudent);
        ResponseEntity<Faculty> response = restTemplate.getForEntity("http://localhost:" + port + "/student"
                + "/faculty/Garry", Faculty.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(faculty);
    }
}
