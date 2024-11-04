package by.bsuir.skillhub.dto;

import by.bsuir.skillhub.entity.Lessons;
import lombok.Data;

@Data
public class CourseLessonDto {
    private Long lessonId;
    private Lessons.LessonType lessonType;
    private String lessonTitle;
    private VideoLessonDto videoLesson;
    private TextLessonDto textLesson;
    private TestLessonDto testLesson;
}
