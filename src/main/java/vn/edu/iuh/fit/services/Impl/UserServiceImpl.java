package vn.edu.iuh.fit.services.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.exceptions.UserAlreadyExistsException;
import vn.edu.iuh.fit.models.Role;
import vn.edu.iuh.fit.models.User;
import vn.edu.iuh.fit.repository.RoleRepository;
import vn.edu.iuh.fit.repository.UserRepository;
import vn.edu.iuh.fit.services.IUserService;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private  final  PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Override
    public void registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException(user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findRoleByName("ROLE_USER").get();
        user.setRoles(Collections.singletonList(userRole));// dung de set role cho user colelctions.singletonList tra ve 1 list chua 1 phan tu duy nhat
        userRepository.save(user);
    }
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found")) ;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new UsernameNotFoundException("User not found")) ;
    }


    @Transactional
    @Override
    public void deleteUserByEmail(String email) {
        User theUser = getUserByEmail(email);
        if (theUser != null) {
            userRepository.deleteByEmail(email);
        }
    }
}
