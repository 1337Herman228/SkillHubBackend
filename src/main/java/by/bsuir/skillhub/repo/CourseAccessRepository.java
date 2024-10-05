package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseAccessRepository extends JpaRepository<CourseAccess, Long> {
    List<CourseAccess> findByCourse(Courses course);
    List<CourseAccess> findByUser(Users user);
    List<CourseAccess> findByStatus(CourseAccess.AccessStatus status);
}