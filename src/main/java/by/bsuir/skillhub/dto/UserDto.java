package by.bsuir.skillhub.dto;

import by.bsuir.skillhub.entity.Persons;
import by.bsuir.skillhub.entity.Roles;
import lombok.Data;

@Data
public class UserDto {
    private Long userId;
    private Persons person;
    private Roles role;
    private String login;
    private Integer diamonds;
}
