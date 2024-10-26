package by.bsuir.skillhub.dto;

import lombok.Data;

@Data
public class TextLessonDto {
    private Long textLessonId;
    private Long lessonId;
    private String lessonBody;
    private String title;
}
