package by.bsuir.skillhub.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class AddQuestionDto {
    private Long lessonId;
    private Long userId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String body; //HTML
}
