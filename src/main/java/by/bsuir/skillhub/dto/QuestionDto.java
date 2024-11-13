package by.bsuir.skillhub.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class QuestionDto {
    private Long questionId;
    private UserDto user;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String body; //HTML
    private List<AnswerDto> answers;
}
