package by.bsuir.skillhub.dto;

import by.bsuir.skillhub.entity.Courses;
import by.bsuir.skillhub.entity.Users;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class CourseDto {
    private Long courseId;
    private Users author;
    private String courseName;
    private String topic;
    private String shortDescription;
    private String longDescription;
    private String courseImg;
    private Courses.SkillLevel skillLevel;
    private Timestamp lastUpdate;
}
