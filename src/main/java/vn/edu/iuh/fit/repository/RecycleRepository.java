package vn.edu.iuh.fit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.models.RecycleRequest;

@Repository
public interface RecycleRepository extends JpaRepository<RecycleRequest, Long>{

}
