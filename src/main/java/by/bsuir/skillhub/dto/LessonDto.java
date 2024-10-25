package by.bsuir.skillhub.dto;

import by.bsuir.skillhub.entity.Lessons;
import lombok.Data;

@Data
public class LessonDto {
    private Long lessonId;
    private Long chapterId;
    private Lessons.LessonType lessonType;
    private String lessonTitle;
    private Short diamondReward;
    private int lessonOrder;
    private Integer duration;
}
