package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.Lessons;
import by.bsuir.skillhub.entity.Tests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestsRepository extends JpaRepository<Tests, Long> {
    List<Tests> findByLesson(Lessons lesson);
}
