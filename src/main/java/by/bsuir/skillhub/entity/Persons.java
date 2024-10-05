package by.bsuir.skillhub.entity;

import lombok.Data;
import jakarta.persistence.*;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "persons")
@Accessors(chain = true)
public class Persons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String email;

    private String avatarImg;
}