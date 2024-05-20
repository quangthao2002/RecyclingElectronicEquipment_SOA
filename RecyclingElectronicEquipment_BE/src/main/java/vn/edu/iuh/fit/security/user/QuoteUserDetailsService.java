package vn.edu.iuh.fit.security.user;


import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.models.User;
import vn.edu.iuh.fit.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class QuoteUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return QuoteUserDetails.buildUserDetails(user); // tra ve doi tuong HotelUserDetails chua thong tin cua user va danh sach cac quyen
    }
}
