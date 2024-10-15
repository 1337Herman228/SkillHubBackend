package by.bsuir.skillhub.dto;

import lombok.Data;

@Data
public class EditPersonDto {
    private Long userId;
    private String name;
    private String surname;
    private String login;
    private String email;
}
