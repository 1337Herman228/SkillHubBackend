package by.bsuir.skillhub.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ReviewDto {
    private int rating;
    private Long courseId;
    private UserDto user;
    private Timestamp createdAt;
    private Long reviewId;
    private String text;
}
