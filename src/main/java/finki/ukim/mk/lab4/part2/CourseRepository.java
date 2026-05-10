package finki.ukim.mk.lab4.part2;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    List<Course> findAll();

    Optional<Course> findById(Long id);

    Course save(Course course);

    void deleteById(Long id);
}