package by.bsuir.skillhub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "user_notes")
@Accessors(chain = true)
public class UserNotes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noteId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lessons lesson;

    @Column(length = 1000, nullable = true)
    private String noteText; // HTML

    private Timestamp createdAt;
}
