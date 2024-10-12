package by.bsuir.skillhub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Entity
@Table(name = "text_lessons")
@Accessors(chain = true)
public class TextLessons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long textLessonId;

    @OneToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lessons lesson;

    @Column(length = 3000, nullable = false)
    private String lessonBody; //HTML

//    @Column(nullable = false)
//    private Integer duration;
}
