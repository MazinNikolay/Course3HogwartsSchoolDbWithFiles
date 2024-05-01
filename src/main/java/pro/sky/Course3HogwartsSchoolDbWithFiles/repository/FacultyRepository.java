package pro.sky.Course3HogwartsSchoolDbWithFiles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(String color, String name);

    Faculty findByNameContainsIgnoreCase(String name);
}

