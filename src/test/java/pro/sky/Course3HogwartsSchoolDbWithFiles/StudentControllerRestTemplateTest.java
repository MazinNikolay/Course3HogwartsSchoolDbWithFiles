package pro.sky.Course3HogwartsSchoolDbWithFiles;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import pro.sky.Course3HogwartsSchoolDbWithFiles.controller.StudentController;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerRestTemplateTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    void getStudentTest() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/1",
                        String.class)).isNotNull();
    }

    @Test
    void testPostStudent() throws Exception {
        Student student = new Student();
        student.setName("TestName2");
        student.setAge(15);
        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student",
                        student, String.class)).isNotNull();
    }
}
