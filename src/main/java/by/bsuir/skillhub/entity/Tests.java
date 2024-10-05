package by.bsuir.skillhub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Entity
@Table(name = "tests")
@Accessors(chain = true)
public class Tests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testId;

    @OneToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lessons lesson;

    @Column(length = 500, nullable = true)
    private String testDescription;
}
