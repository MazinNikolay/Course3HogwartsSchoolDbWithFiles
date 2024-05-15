package pro.sky.Course3HogwartsSchoolDbWithFiles.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findByStudentId(Long id);
}
