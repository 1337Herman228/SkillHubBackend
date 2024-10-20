package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.Courses;
import by.bsuir.skillhub.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Long> {
    List<Courses> findByCourseNameContainingIgnoreCase(String courseName);
    List<Courses> findByCourseName(String courseName);
    List<Courses> findByTopic(String topic);
    List<Courses> findByAuthor(Users user);
    List<Courses> findByAuthorAndCourseNameContainingIgnoreCase(Users user, String courseName);
    List<Courses> findBySkillLevel(Courses.SkillLevel skillLevel);
}
