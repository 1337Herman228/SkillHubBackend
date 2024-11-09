package by.bsuir.skillhub.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddTestQuestionDto {
    private String questionText;
    public String correctAnswerId;
    public List<AddTestAnswerDto> answers;
}
