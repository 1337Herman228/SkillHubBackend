package by.bsuir.skillhub.dto;

import lombok.Data;

import java.sql.Timestamp;


@Data
public class AddAnswerDto {
    private Long questionId;
    private Long userId;
    private String body; //html
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
