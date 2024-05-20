package vn.edu.iuh.fit.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.models.Role;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role,Long> {
    Optional<Role> findRoleByName(String roleUser);

    boolean existsByName(String roleName);
}
