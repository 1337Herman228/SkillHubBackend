package by.bsuir.skillhub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Entity
@Table(name = "lessons")
@Accessors(chain = true)
public class Lessons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessonId;

    @ManyToOne
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapters chapter;

    @Enumerated(EnumType.STRING)
    private LessonType lessonType; // Enum for lesson types

    @Column(length = 150, nullable = false)
    private String lessonTitle;

    private Short diamondReward;
    private Integer lessonOrder;

    //У тестовых уроков не будет длительности
    @Column(nullable = true)
    private Integer duration;

    public enum LessonType {
        VIDEO, TEXT, TEST
    }
}
