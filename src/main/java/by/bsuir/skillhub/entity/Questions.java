package by.bsuir.skillhub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;


@Data
@Entity
@Table(name = "questions")
@Accessors(chain = true)
public class Questions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lessons lesson;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String body; //HTML

    private Timestamp createdAt;
    private Timestamp updatedAt;
}
