package by.bsuir.skillhub.dto;

import lombok.Data;

import java.util.List;

@Data
public class TestLessonDto {
    private Long testId;
    private Long lessonId;
    private String testDescription;
    private String name;
    private List<TestQuestionDto> testQuestions;
    private List<TestAnswerDto> testAnswers;
}
