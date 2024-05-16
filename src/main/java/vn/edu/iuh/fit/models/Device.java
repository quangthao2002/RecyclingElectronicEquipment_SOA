package vn.edu.iuh.fit.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deviceId;
    private String model;
    private int deviceAge;
    private String deviceStatus;
    private String damageLocation;
    private String damageDescription;
    @OneToOne(mappedBy = "device")
    @JsonBackReference
    private Quote quote;
}
