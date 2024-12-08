package by.bsuir.skillhub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "courses")
@Accessors(chain = true)
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Users author;

    private String courseImg;

    @Column(nullable = false)
    private String courseName;

    @Column(nullable = true)
    private String certificate;

    @Column(length = 150, nullable = false)
    private String topic;

    @Enumerated(EnumType.STRING)
    private SkillLevel skillLevel; // Enum for skill levels

    @Column(length = 300, nullable = false)
    private String shortDescription;

    @Column(length = 2000, nullable = false)
    private String longDescription; //HTML

    private Timestamp lastUpdate;

    public enum SkillLevel {
        START, NORMAL, PRO, ALL
    }
}
