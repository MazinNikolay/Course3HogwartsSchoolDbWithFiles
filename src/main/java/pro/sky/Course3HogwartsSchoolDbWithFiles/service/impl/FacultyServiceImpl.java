package pro.sky.Course3HogwartsSchoolDbWithFiles.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.Course3HogwartsSchoolDbWithFiles.exceptioms.NotFoundEntityException;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Faculty;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Student;
import pro.sky.Course3HogwartsSchoolDbWithFiles.repository.FacultyRepository;
import pro.sky.Course3HogwartsSchoolDbWithFiles.service.FacultyService;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    private Logger logger = LoggerFactory.getLogger(FacultyServiceImpl.class);

    @Override
    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create Faculty");
        return repository.save(faculty);
    }

    @Override
    public Faculty getFaculty(Long id) {
        logger.info("Was invoked method for get Faculty");
        isEntityExist(id);
        return repository.findById(id).get();
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Was invoked method for update Faculty");
        isEntityExist(faculty.getId());
        return repository.save(faculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        logger.info("Was invoked method for delete Faculty");
        isEntityExist(id);
        repository.deleteById(id);
    }

    @Override
    public Collection<Faculty> findByColorOrName(String color, String name) {
        logger.info("Was invoked method for find Faculty by color or name");
        return repository.findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(color, name);
    }

    @Override
    public Collection<Student> findByFaculty(String name) {
        logger.info("Was invoked method for find Faculty");
        return repository.findByNameContainsIgnoreCase(name).getStudents();
    }

    private void isEntityExist(Long id) {
        logger.info("Was invoked method for check Faculty exist");
        repository.findById(id).orElseThrow(() -> new NotFoundEntityException());
        logger.error("This Faculty with id {} is not exist", id);
    }

    public String getLongestFacultyName() {
        return repository.findAll().stream()
                .map(e -> e.getName())
                .max(Comparator.comparing(e -> e.length()))
                .orElseThrow(() -> new RuntimeException());
    }
}