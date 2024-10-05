package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.Lessons;
import by.bsuir.skillhub.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourcesRepository extends JpaRepository<Resources, Long> {
    List<Resources> findByLesson(Lessons lesson);
}
