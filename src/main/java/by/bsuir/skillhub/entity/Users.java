package by.bsuir.skillhub.entity;

import lombok.Data;
import jakarta.persistence.*;
import lombok.experimental.Accessors;

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
}