package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.Lessons;
import by.bsuir.skillhub.entity.Questions;
import by.bsuir.skillhub.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions, Long> {
    List<Questions> findByLesson(Lessons lesson);
    List<Questions> findByUser(Users user);
    List<Questions> findByTitle(String title);
}
