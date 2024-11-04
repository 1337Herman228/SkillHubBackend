package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChaptersRepository extends JpaRepository<Chapters, Long> {
    List<Chapters> findByCourse(Courses course);
    List<Chapters> findByChapterTitle(String title);
}