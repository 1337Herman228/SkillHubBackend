package by.bsuir.skillhub.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class GetAccessUsersDto {
    private Long accessId;
    private UserDto user;
    private Timestamp requestDate;
    private Timestamp grantedDate;
}
