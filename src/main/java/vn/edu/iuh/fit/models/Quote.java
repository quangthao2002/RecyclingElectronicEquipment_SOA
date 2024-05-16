package vn.edu.iuh.fit.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quoteId;
    @Column(nullable = false)
    private double firstQuotePrice;
    private Double finalQuotePrice ;
    private String productCode;
    @Enumerated(EnumType.STRING)
    private QuoteStatus quoteStatus ;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @JsonManagedReference
    @OneToOne
    @JoinColumn(name = "device_id")
    private Device device;

    @OneToOne(mappedBy = "quote", cascade = CascadeType.ALL)
    @JsonManagedReference
    private RecyclingReceipt recyclingReceipt;
}
