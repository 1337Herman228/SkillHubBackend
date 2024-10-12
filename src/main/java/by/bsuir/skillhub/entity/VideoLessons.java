package by.bsuir.skillhub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Entity
@Table(name = "video_lessons")
@Accessors(chain = true)
public class VideoLessons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long videoLessonId;

    @OneToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lessons lesson;

    @Column(length = 300, nullable = false)
    private String videoUrl;

//    @Column(nullable = false)
//    private Integer videoDuration;
}
