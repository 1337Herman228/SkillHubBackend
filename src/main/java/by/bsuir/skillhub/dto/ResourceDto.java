package by.bsuir.skillhub.dto;

import lombok.Data;

@Data
public class ResourceDto {
    private Long resourceId;
    private Long lessonId;
    private String resourceTitle;
    private String resourceLink;
}
