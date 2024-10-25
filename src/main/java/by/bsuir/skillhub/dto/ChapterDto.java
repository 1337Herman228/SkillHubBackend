package by.bsuir.skillhub.dto;

import lombok.Data;

@Data
public class ChapterDto {
    private Long chapterId;
    private Long courseId;
    private String chapterTitle;
    private int chapterOrder;

    private int lessonsCount;
    private int duration;
}
