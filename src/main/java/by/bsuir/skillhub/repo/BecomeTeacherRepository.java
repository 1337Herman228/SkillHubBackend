package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.BecomeTeacher;
import by.bsuir.skillhub.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BecomeTeacherRepository extends JpaRepository<BecomeTeacher, Long> {
    Optional<BecomeTeacher> findByStatus(BecomeTeacher.TeacherStatus status);
    Optional<BecomeTeacher> findByUser(Users user);
}