package by.bsuir.skillhub.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class NoteDto {
    private Timestamp createdAt;
    private Long lessonId;
    private Long userId;
    private Long noteId;
    private String text;
}
