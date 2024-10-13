package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseAccessRepository extends JpaRepository<CourseAccess, Long> {
    List<CourseAccess> findByCourse(Courses course);
    List<CourseAccess> findByUser(Users user);
    Optional<CourseAccess> findByUserAndCourse(Users user, Courses course);
    List<CourseAccess> findByStatus(CourseAccess.AccessStatus status);
}