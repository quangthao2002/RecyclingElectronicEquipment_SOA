package vn.edu.iuh.fit.services;


import vn.edu.iuh.fit.models.User;

import java.util.List;

public interface IUserService  {

    void registerUser(User user);

    List<User> getUsers();


    User getUserByEmail(String email);
    User getUserById(Long id);

    void deleteUserByEmail(String email);
    void saveUser(User user);

    User getAuthenticatedUser();
}
