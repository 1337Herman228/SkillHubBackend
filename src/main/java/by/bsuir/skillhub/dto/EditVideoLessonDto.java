package by.bsuir.skillhub.dto;

import by.bsuir.skillhub.entity.Lessons;
import lombok.Data;

import java.util.List;

@Data
public class EditVideoLessonDto {
    private Long lessonId;
    private Long chapterId;
    private String lessonTitle;
    private Lessons.LessonType lessonType;
    private int duration;
    private int diamondReward;
    private List<ResourcesDto> resources;
    private String videoUrl;
}
