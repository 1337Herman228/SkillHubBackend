package by.bsuir.skillhub.dto;

import lombok.Data;

@Data
public class TestQuestionDto {
    private Long questionId;
    private Long testId;
    private String questionText; //HTML
    private Long correctAnswerId;
    private String answerDescription;//HTML
}
