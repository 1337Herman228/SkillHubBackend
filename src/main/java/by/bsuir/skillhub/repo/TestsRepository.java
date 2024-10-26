package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.Lessons;
import by.bsuir.skillhub.entity.Tests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestsRepository extends JpaRepository<Tests, Long> {
    Optional<Tests> findByLesson(Lessons lesson);
}
