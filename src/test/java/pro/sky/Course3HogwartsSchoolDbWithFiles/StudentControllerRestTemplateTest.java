package pro.sky.Course3HogwartsSchoolDbWithFiles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import pro.sky.Course3HogwartsSchoolDbWithFiles.controller.StudentController;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Student;

import java.net.URI;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerRestTemplateTest {

    private Student student = new Student();

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void initial() {
        student.setId(15L);
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
}
