package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.Persons;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonsRepository extends JpaRepository<Persons, Long> {
    List<Persons> findByName(String name);
    List<Persons> findBySurname(String surname);
    List<Persons> findByEmail(String email);
}
