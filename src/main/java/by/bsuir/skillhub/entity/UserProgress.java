package by.bsuir.skillhub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Entity
@Table(name = "user_progress")
@Accessors(chain = true)
public class UserProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long progressId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lessons lesson;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Courses course;

}
