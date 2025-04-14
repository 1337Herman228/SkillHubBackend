package by.bsuir.skillhub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "avatar_strokes")
@Accessors(chain = true)
public class AvatarStrokes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long avatarStrokeId;

    private String label;
    private String value;
    private String url;
    private int price;
}
