package by.bsuir.skillhub.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AddReviewDto {
    private int rating;
    private Long courseId;
    private Long userId;
    private Timestamp createdAt;
    private Long reviewId;
    private String text;
}
