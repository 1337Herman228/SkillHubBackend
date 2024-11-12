package by.bsuir.skillhub.dto;

import lombok.Data;

@Data
public class UserProgressDto {
    private float progressInPercents;
    private int completedLessonsCount;
    private int allLessonsCount;
}
