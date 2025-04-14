package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.AvatarStrokes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AvatarStrokesRepository extends JpaRepository<AvatarStrokes, Long> {
}