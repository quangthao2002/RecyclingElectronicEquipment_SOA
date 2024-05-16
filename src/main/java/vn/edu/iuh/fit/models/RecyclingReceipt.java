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
@NoArgsConstructor @AllArgsConstructor
public class RecyclingReceipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RecyclingReceiptStatus  recyclingReceiptStatus;
    private String paymentMethod;
    private String paymentStatus;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_id")
    @JsonBackReference
    private Quote quote;

}
