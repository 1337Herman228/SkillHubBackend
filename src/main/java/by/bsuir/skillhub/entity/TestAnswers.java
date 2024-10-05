package by.bsuir.skillhub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "test_answers")
@Accessors(chain = true)
public class TestAnswers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long testAnswerId;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private TestQuestions testQuestion;

    @Column(length = 200, nullable = false)
    private String answer;
}
