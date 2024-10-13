package by.bsuir.skillhub.dto;

import lombok.Data;

@Data
public class FindCourseByNameForUserDto {
    private String courseName;
    private Long userId;
}
