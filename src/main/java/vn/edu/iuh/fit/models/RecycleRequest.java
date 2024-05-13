package vn.edu.iuh.fit.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RecycleRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productCode;

    private String model;

    private int deviceAge;

    private String status;

    private int quoteValue;

    private String damageLocation;

    private String damageDescription;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
