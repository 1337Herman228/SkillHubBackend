package by.bsuir.skillhub.dto;

import by.bsuir.skillhub.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long userId;
    private Persons person;
    private Roles role;
    private String login;
    private Integer diamonds;
    private AvatarStrokes avatarStroke;
    private List<AvatarStrokes> purchasedAvatarStrokes;
    private Dignities dignity;
    private List<Dignities> purchasedDignities;
    private NicknameColors nicknameColor;
    private List<NicknameColors> purchasedNicknameColors;
}
