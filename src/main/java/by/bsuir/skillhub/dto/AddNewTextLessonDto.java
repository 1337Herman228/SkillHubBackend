package by.bsuir.skillhub.dto;

import by.bsuir.skillhub.entity.Lessons;
import lombok.Data;

import java.util.List;

@Data
public class AddNewTextLessonDto {
    private Long chapterId;
    private String lessonTitle;
    private Lessons.LessonType lessonType;
    private int duration;
    private int diamondReward;
    private List<ResourcesDto> resources;
    private String html;
}
