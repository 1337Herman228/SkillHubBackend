package by.bsuir.skillhub.dto;

import by.bsuir.skillhub.entity.Courses;
import lombok.Data;

@Data
public class AllCoursesDto {
    private Courses course; //
    private Integer duration; //
    private Integer allLessonsCount; //
    private Float rating; //
    private Integer reviewsCount; //
    private String status; // NO_REQUEST, PENDING, APPROVED, REJECTED
}
