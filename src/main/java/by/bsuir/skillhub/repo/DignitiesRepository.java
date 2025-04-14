package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.Dignities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DignitiesRepository extends JpaRepository<Dignities, Long> {
}