package by.bsuir.skillhub.dto;


import lombok.Data;

@Data
public class ChangeUserPasswordDto {
    private Long userId;
    private String oldPassword;
    private String newPassword;
}
