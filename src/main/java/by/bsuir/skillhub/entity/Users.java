package by.bsuir.skillhub.entity;

import lombok.Data;
import jakarta.persistence.*;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Entity
@Table(name = "users")
@Accessors(chain = true)
public class Users  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Persons person;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Roles role;

    @Column(length = 200, nullable = false)
    private String login;

    private String password;
    private Integer diamonds;

    // Выбранная рамка аватара
    @ManyToOne
    @JoinColumn(name = "avatar_stroke_id")
    private AvatarStrokes avatarStroke;

    // Выбранный титул
    @ManyToOne
    @JoinColumn(name = "dignity_id")
    private Dignities dignity;

    // Пользователь может покупать рамки для аватара
    // Хранит купленные рамки
    @ManyToMany
    @JoinTable(
            name = "user_purchased_avatar_strokes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "avatar_stroke_id")
    )
    private List<AvatarStrokes> purchasedStrokes;

    // Пользователь может покупать титулы
    // Хранит купленные титулы
    @ManyToMany
    @JoinTable(
            name = "user_purchased_dignities",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "dignity_id")
    )
    private List<Dignities> purchasedDignities;

    // Выбранный цвет никнейма
    @ManyToOne
    @JoinColumn(name = "nickname_color_id")
    private NicknameColors nicknameColor;

    // Пользователь может покупать цвета для ника
    // Хранит купленные цвета для ников
    @ManyToMany
    @JoinTable(
            name = "user_purchased_nickname_colors",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "nickname_color_id")
    )
    private List<NicknameColors> purchasedNicknameColors;
}