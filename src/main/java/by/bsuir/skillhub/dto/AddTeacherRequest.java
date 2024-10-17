package by.bsuir.skillhub.dto;

import by.bsuir.skillhub.entity.Users;
import jakarta.persistence.Column;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class AddTeacherRequest {
    private Long userId;
    private String courseSphere;
    private String courseName;
    private String courseDescription;
}
