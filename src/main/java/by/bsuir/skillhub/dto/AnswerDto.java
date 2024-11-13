package by.bsuir.skillhub.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AnswerDto {
    private Long answerId;
    private Long questionId;
    private UserDto user;
    private String body; //html
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
