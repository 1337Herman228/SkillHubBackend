package by.bsuir.skillhub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;


@Data
@Entity
@Table(name = "reviews")
@Accessors(chain = true)
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "course_id",nullable = false)
    private Courses course;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Users user;

    private Integer rating;

    @Column(length = 500, nullable = false)
    private String reviewText; //Not HTML
    private Timestamp createdAt;
}
