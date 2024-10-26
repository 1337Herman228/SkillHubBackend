package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.TestAnswers;
import by.bsuir.skillhub.entity.TestQuestions;
import by.bsuir.skillhub.entity.Tests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestAnswersRepository extends JpaRepository<TestAnswers, Long> {
    List<TestAnswers> findByTestQuestion(TestQuestions testQuestions);
}
