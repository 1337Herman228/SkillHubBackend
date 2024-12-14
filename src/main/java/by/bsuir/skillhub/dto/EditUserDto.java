package by.bsuir.skillhub.dto;

import lombok.Data;

@Data
public class EditUserDto {
    private Long userId;
    private Long roleId;
    private String login;
    private String name;
    private String surname;
    private String email;
}
