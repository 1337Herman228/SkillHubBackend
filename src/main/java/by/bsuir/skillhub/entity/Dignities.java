package by.bsuir.skillhub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name = "dignities")
@Accessors(chain = true)
public class Dignities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dignityId;

    private String dignityName;
    private int price;
}
