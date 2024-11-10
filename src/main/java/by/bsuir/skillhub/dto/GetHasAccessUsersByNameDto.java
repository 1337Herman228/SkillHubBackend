package by.bsuir.skillhub.dto;

import lombok.Data;

@Data
public class GetHasAccessUsersByNameDto {
    private Long courseId;
    private String username;
}
