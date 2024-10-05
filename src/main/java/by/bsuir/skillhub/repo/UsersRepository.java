package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByPerson(Persons person);
    List<Users> findByRole(Roles role);
    Optional<Users> findByLogin(String login);
}
