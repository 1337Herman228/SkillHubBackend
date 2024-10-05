package by.bsuir.skillhub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "become_teacher")
@Accessors(chain = true)
public class BecomeTeacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long becomeTeacherId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    private Timestamp requestDate;
    private Timestamp grantedDate;

    @Enumerated(EnumType.STRING)
    private TeacherStatus status; // Enum for teacher status

    public enum TeacherStatus {
        PENDING, APPROVED, REJECTED
    }
}
