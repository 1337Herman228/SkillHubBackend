package by.bsuir.skillhub.repo;

import by.bsuir.skillhub.entity.BecomeTeacher;
import by.bsuir.skillhub.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BecomeTeacherRepository extends JpaRepository<BecomeTeacher, Long> {
    List<BecomeTeacher> findByStatus(BecomeTeacher.TeacherStatus status);
    List<BecomeTeacher> findByUser(Users user);
}