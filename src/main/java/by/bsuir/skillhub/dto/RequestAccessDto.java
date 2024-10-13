package by.bsuir.skillhub.dto;

import lombok.Data;

@Data
public class RequestAccessDto {
    private Long userId;
    private Long courseId;
}
