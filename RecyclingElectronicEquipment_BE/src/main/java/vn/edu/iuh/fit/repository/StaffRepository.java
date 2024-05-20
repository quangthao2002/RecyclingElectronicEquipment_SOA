package vn.edu.iuh.fit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.models.Staff;

public interface StaffRepository extends JpaRepository<Staff, Long> {
  }