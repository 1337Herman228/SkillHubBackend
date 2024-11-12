package by.bsuir.skillhub.dto;

import lombok.Data;

@Data
public class MarkLessonAsPassedDto {
    private Long userId;
    private Long courseId;
    private Long lessonId;
}
