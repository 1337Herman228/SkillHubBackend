package by.bsuir.skillhub.dto;

import by.bsuir.skillhub.entity.Courses;
import lombok.Data;

@Data
public class ContinueCourseDto {
    private Courses course;
    private float progressInPercents;
    private int completedLessonsCount;
    private int allLessonsCount;
}
