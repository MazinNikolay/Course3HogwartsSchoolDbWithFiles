package pro.sky.Course3HogwartsSchoolDbWithFiles.service.impl;

import org.springframework.stereotype.Service;
import pro.sky.Course3HogwartsSchoolDbWithFiles.exceptioms.NotFoundEntityException;
import pro.sky.Course3HogwartsSchoolDbWithFiles.model.Faculty;
import pro.sky.Course3HogwartsSchoolDbWithFiles.repository.FacultyRepository;
import pro.sky.Course3HogwartsSchoolDbWithFiles.service.FacultyService;

import java.util.Collection;

@Service
public class FacultyServiceImpl implements FacultyService {
    private final FacultyRepository repository;

    public FacultyServiceImpl(FacultyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Faculty createFaculty(Faculty faculty) {
        return repository.save(faculty);
    }

    @Override
    public Faculty getFaculty(Long id) {
        isEntityExist(id);
        return repository.findById(id).get();
    }

    @Override
    public Faculty updateFaculty(Faculty faculty) {
        isEntityExist(faculty.getId());
        return repository.save(faculty);
    }

    @Override
    public void deleteFaculty(Long id) {
        isEntityExist(id);
        repository.deleteById(id);
    }

    @Override
    public Collection<Faculty> findByColorOrName(String color, String name) {
        return repository.findByColorContainsIgnoreCaseOrNameContainsIgnoreCase(color, name);
    }

    @Override
    public Collection<Student> findByFaculty(String name) {
        return repository.findByNameContainsIgnoreCase(name).getStudents();
    }

    private void isEntityExist(Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundEntityException());
    }
}

