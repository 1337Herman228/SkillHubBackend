package by.bsuir.skillhub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "registration_keys")
@Accessors(chain = true)
public class RegistrationKeys {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long regKeyId;

    private String email;
    private String regKey;
}
