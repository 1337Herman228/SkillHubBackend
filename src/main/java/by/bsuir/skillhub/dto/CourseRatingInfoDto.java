package by.bsuir.skillhub.dto;

import lombok.Data;

@Data
public class CourseRatingInfoDto {
    private Long courseId;
    private float rating;
    private float star5Percentage;
    private float star4Percentage;
    private float star3Percentage;
    private float star2Percentage;
    private float star1Percentage;
}
