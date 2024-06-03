package pro.sky.Course3HogwartsSchoolDbWithFiles.service;

import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Faculty;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Student;

import java.util.Collection;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty getFaculty(Long id);

    Faculty updateFaculty(Faculty faculty);

    void deleteFaculty(Long id);

    Collection<Faculty> findByColorOrName(String color, String name);

    Collection<Student> findByFaculty(String name);

    String getLongestFacultyName();
}
