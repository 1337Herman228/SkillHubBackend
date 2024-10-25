package by.bsuir.skillhub.dto;

import lombok.Data;

import java.util.List;

@Data
public class CourseInfoDto {
    private CourseDto course; //
    private InfoDto info; //
    private List<ChapterDto> chapters;
    private List<LessonWithResourcesDto> lessons;
}
