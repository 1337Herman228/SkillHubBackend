package by.bsuir.skillhub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Entity
@Table(name = "chapters")
@Accessors(chain = true)
public class Chapters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chapterId;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Courses course;

    @Column(length = 100, nullable = false)
    private String chapterTitle;

    @Column(nullable = false)
    private Integer chapterOrder;
}
