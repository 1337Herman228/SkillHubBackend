package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.NicknameColors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NicknameColorsRepository extends JpaRepository<NicknameColors, Long> {
}