package vn.edu.iuh.fit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.models.RecyclingReceipt;
import vn.edu.iuh.fit.models.RecyclingReceiptStatus;

import java.util.List;

@Repository
public interface RecycleRepository extends JpaRepository<RecyclingReceipt, Long>{

    @Query("SELECT r FROM RecyclingReceipt r WHERE r.recyclingReceiptStatus = ?1")
    List<RecyclingReceipt> getRecyclingReceiptsByRecyclingReceiptStatus(RecyclingReceiptStatus status);
}
