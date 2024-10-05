package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.Chapters;
import by.bsuir.skillhub.entity.Lessons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonsRepository extends JpaRepository<Lessons, Long> {
    List<Lessons> findByChapter(Chapters chapter);
    List<Lessons> findByLessonType(Lessons.LessonType lessonType);
}