package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserNotesRepository extends JpaRepository<UserNotes, Long> {
    List<UserNotes> findByLesson(Lessons lesson);
    List<UserNotes> findByUser(Users user);
    Optional<UserNotes> findByLessonAndUser(Lessons lesson, Users user);
}
