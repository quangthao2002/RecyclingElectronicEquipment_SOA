package vn.edu.iuh.fit.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);
}
