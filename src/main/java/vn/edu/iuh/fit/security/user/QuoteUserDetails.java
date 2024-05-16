package vn.edu.iuh.fit.security.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.edu.iuh.fit.models.User;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuoteUserDetails implements UserDetails {
    private Long id;
    private  String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String gender;
    private Collection<GrantedAuthority> authorities;

    public static QuoteUserDetails buildUserDetails(User user){
        List<GrantedAuthority> authorities = user.getRoles()// lay ra danh sach cac role cua user
                .stream() // chuyen sang dang stream
                .map(role -> new SimpleGrantedAuthority(role.getName())) // chuyen sang dang SimpleGrantedAuthority de tao ra danh sach cac quyen
                .collect(Collectors.toList());// chuyen sang dang list
    // tra ve doi tuong HotelUserDetails chua thong tin cua user va danh sach cac quyen
        return new QuoteUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getAddress(),
                user.getPhoneNumber(),
                user.getGender(),
                authorities);

    }
    public User getUser() {
        // Tạo một đối tượng User mới từ các thuộc tính của QuoteUserDetails
        User user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName); // Thêm các trường mới
        user.setLastName(lastName);
        user.setAddress(address);
        user.setPhoneNumber(phoneNumber);
        user.setGender(gender);

        return user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
