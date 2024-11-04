package by.bsuir.skillhub.dto;

import by.bsuir.skillhub.entity.Courses;
import lombok.Data;

@Data
public class EditCourseDto {

    private Long courseId;
    private String topic;
    private String courseName;
    private String shortDescription;
    private String longDescription;
    private String courseImg;
    private Courses.SkillLevel skillLevel;
}
