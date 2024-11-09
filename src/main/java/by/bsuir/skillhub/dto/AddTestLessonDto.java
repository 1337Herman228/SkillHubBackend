package by.bsuir.skillhub.dto;

import by.bsuir.skillhub.entity.Lessons;
import lombok.Data;

import java.util.List;

@Data
public class AddTestLessonDto {
    private Long chapterId;
    private String lessonTitle;
    private Lessons.LessonType lessonType;
    private int diamondReward;
    private List<ResourcesDto> resources;
    private List<AddTestQuestionDto> questions;
}
