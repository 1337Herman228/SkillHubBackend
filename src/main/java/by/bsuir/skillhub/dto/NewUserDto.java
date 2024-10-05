package by.bsuir.skillhub.dto;

import lombok.Data;

@Data
public class NewUserDto {
    private String login;
    private String password;
    private final String position = "user";
    private String name;
    private String surname;
    private String email;
    private String referralKey;
}
