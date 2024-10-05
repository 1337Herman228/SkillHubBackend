package by.bsuir.skillhub.entity;

import lombok.Data;
import jakarta.persistence.*;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "roles")
@Accessors(chain = true)
public class Roles  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(length = 100, nullable = false)
    private String position; // admin / user / teacher
}