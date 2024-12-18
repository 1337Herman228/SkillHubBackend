package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VideoLessonsRepository extends JpaRepository<VideoLessons, Long> {
    Optional<VideoLessons> findByLesson(Lessons lesson);
}
