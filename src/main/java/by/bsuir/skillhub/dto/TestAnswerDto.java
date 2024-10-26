package by.bsuir.skillhub.dto;

import by.bsuir.skillhub.entity.TestQuestions;
import lombok.Data;

@Data
public class TestAnswerDto {
    private Long testAnswerId;
    private Long testQuestionId;
    private String answer; //HTML
}
