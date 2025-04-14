package by.bsuir.skillhub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "nickname_colors")
@Accessors(chain = true)
public class NicknameColors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nicknameColorId;
    private String name;
    private String color;
    private int price;
}
