package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.RegistrationKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationKeysRepository extends JpaRepository<RegistrationKeys, Long> {
    Optional<RegistrationKeys> findByEmail(String email);
    List<RegistrationKeys> findByEmailContainingIgnoreCase(String email);
}