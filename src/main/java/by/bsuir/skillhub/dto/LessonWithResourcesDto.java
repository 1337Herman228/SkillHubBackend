package by.bsuir.skillhub.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class LessonWithResourcesDto extends LessonDto {
    private List<ResourceDto> resources;
}
