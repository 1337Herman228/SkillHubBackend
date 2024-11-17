package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.Courses;
import by.bsuir.skillhub.entity.Reviews;
import by.bsuir.skillhub.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
    List<Reviews> findByCourse(Courses courses);
    Optional<Reviews> findByCourseAndUser(Courses course, Users user);
    List<Reviews> findByRating(Integer rating);
    List<Reviews> findByRatingAndCourse(Integer rating, Courses course);
}
