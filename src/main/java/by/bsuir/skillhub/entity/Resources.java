package by.bsuir.skillhub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Entity
@Table(name = "resources")
@Accessors(chain = true)
public class Resources {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resourceId;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lessons lesson;

    @Column(length = 100, nullable = false)
    private String resourceTitle;
    @Column(length = 355, nullable = false)
    private String resourceLink;
}
