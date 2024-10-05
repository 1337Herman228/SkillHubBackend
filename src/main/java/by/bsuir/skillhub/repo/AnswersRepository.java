package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.Answers;
import by.bsuir.skillhub.entity.Questions;
import by.bsuir.skillhub.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswersRepository extends JpaRepository<Answers, Long> {
    List<Answers> findByQuestion(Questions question);
    List<Answers> findByUser(Users user);
}
