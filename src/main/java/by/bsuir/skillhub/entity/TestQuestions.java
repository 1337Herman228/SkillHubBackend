package by.bsuir.skillhub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Entity
@Table(name = "test_questions")
@Accessors(chain = true)
public class TestQuestions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Tests test;

    @Column(length = 600, nullable = false)
    private String questionText; //HTML

    @OneToOne
    @JoinColumn(name = "test_answer_id", nullable = true)
    private TestAnswers correctAnswer;

    @Column(length = 600, nullable = true)
    private String answerDescription; //HTML
}
