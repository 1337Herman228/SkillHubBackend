package by.bsuir.skillhub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "course_access")
@Accessors(chain = true)
public class CourseAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accessId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Courses course;

    private Timestamp requestDate;
    private Timestamp grantedDate;

    @Enumerated(EnumType.STRING)
    private AccessStatus status; // Enum for access status

    public enum AccessStatus {
        PENDING, APPROVED, REJECTED
    }
}
