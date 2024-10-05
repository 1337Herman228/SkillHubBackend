package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByPosition(String position);
}