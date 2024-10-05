package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoLessonsRepository extends JpaRepository<VideoLessons, Long> {
    List<VideoLessons> findByLesson(Lessons lesson);
}
