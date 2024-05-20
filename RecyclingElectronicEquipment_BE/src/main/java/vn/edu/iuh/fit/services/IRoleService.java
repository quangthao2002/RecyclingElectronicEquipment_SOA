package vn.edu.iuh.fit.services;


import vn.edu.iuh.fit.models.Role;
import vn.edu.iuh.fit.models.User;

import java.util.List;

public interface IRoleService {

    List<Role> getRoles();

    Role createRole(Role role);

    void deleteRole(Long id);

    Role findByName(String name);

    User removeUserFromRole(Long userId, Long roleId);

    User assignRoleToUser(Long userId, Long roleId);

    Role removeAllUsersFromRole(Long roleId);

}
