package by.bsuir.skillhub.dto;

import by.bsuir.skillhub.entity.CourseAccess;
import by.bsuir.skillhub.entity.Courses;
import lombok.Data;

@Data
public class UserInterestCoursesDto {

    private Courses course; //
    private int duration; //
    private int allLessonsCount; //
    private int completedLessonsCount; //
    private float progressInPercents; //
    private float rating; //
    private int reviewsCount; //
    private CourseAccess.AccessStatus status; // PENDING, APPROVED, REJECTED

}
